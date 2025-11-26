// VenueDAO.java - NEW FILE
package com.eventmanagement.dao;

import com.eventmanagement.database.DatabaseConnection;
import com.eventmanagement.model.Venue;
import java.sql.*;

public class VenueDAO {
    
    public Venue getVenueById(int venueId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) return null;
            
            String sql = "SELECT * FROM Venue WHERE VenueID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, venueId);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Venue(
                    rs.getInt("VenueID"),
                    rs.getString("VenueName"),
                    rs.getString("Location"),
                    rs.getInt("Capacity")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error fetching venue: " + e.getMessage());
        } finally {
            closeResources(conn, pstmt, rs);
        }
        return null;
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