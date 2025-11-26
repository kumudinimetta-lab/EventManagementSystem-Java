// AttendeeDAO.java
package com.eventmanagement.dao;

import com.eventmanagement.database.DatabaseConnection;
import com.eventmanagement.model.Attendee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendeeDAO {
    
    public List<Attendee> getAllAttendees() {
        List<Attendee> attendees = new ArrayList<>();
        String sql = "SELECT * FROM Attendee";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Attendee attendee = new Attendee(
                    rs.getInt("AttendeeID"),
                    rs.getString("Name"),
                    rs.getString("Email"),
                    rs.getString("QRCode"),
                    rs.getString("RegistrationStatus"),
                    rs.getBoolean("LocationSharingEnabled")
                );
                attendees.add(attendee);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching attendees: " + e.getMessage());
        }
        return attendees;
    }
    
    public boolean updateAttendeeLocation(int attendeeId, double lat, double lng) {
        String sql = "UPDATE Attendee SET CurrentLocationX = ?, CurrentLocationY = ? WHERE AttendeeID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, lat);
            pstmt.setDouble(2, lng);
            pstmt.setInt(3, attendeeId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Error updating location: " + e.getMessage());
            return false;
        }
    }
}