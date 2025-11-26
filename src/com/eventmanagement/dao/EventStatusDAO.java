// EventStatusDAO.java
package com.eventmanagement.dao;

import com.eventmanagement.database.DatabaseConnection;
import com.eventmanagement.model.EventStatus;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventStatusDAO {
    
    public List<EventStatus> getAllEventStatus() {
        List<EventStatus> statusList = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) return statusList;
            
            String sql = "SELECT * FROM EventStatus ORDER BY StatusCheckTime DESC";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                EventStatus status = new EventStatus(
                    rs.getInt("StatusID"),
                    rs.getInt("EventID"),
                    rs.getInt("AdminID"),
                    rs.getTimestamp("StatusCheckTime"),
                    rs.getBoolean("ResourcesAchieved"),
                    rs.getString("EmergencyCircumstances"),
                    rs.getString("OverallStatus"),
                    rs.getString("Remarks")
                );
                statusList.add(status);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching event status: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }
        return statusList;
    }
    
    // Helper method to close resources
    private void closeResources(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.out.println("Error closing resources: " + e.getMessage());
        }
    }
}