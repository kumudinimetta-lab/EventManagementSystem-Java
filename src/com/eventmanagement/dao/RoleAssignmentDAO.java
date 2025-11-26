// RoleAssignmentDAO.java
package com.eventmanagement.dao;

import com.eventmanagement.database.DatabaseConnection;
import com.eventmanagement.model.RoleAssignment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleAssignmentDAO {
    
    public List<RoleAssignment> getAssignmentsByOrganizer(int organizerId) {
        List<RoleAssignment> assignments = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) return assignments;
            
            String sql = "SELECT * FROM RoleAssignment WHERE AssignedToOrganizerID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, organizerId);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                RoleAssignment assignment = new RoleAssignment(
                    rs.getInt("AssignmentID"),
                    rs.getInt("EventID"),
                    rs.getInt("ManagerID"),
                    rs.getInt("AssignedToOrganizerID"),
                    rs.getString("AssignedRole"),
                    rs.getTime("ShiftStartTime"),
                    rs.getTime("ShiftEndTime"),
                    rs.getString("LocationAssigned")
                );
                assignments.add(assignment);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching assignments: " + e.getMessage());
        } finally {
            closeResources(conn, pstmt, rs);
        }
        return assignments;
    }
    
    // Helper method to close resources
    private void closeResources(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.out.println("Error closing resources: " + e.getMessage());
        }
    }
}