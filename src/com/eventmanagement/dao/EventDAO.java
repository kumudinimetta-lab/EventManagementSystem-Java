// EventDAO.java - COMPLETE VERSION
package com.eventmanagement.dao;

import com.eventmanagement.database.DatabaseConnection;
import com.eventmanagement.model.Event;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {
    
    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) return events;
            
            String sql = "SELECT * FROM Event ORDER BY EventDate";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Event event = new Event(
                    rs.getInt("EventID"),
                    rs.getString("EventName"),
                    rs.getString("EventType"),
                    rs.getDate("EventDate"),
                    rs.getInt("VenueID"),
                    rs.getInt("ManagerID"),
                    rs.getDouble("TotalBudget"),
                    rs.getInt("ExpectedAttendees")
                );
                events.add(event);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching events: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }
        return events;
    }
    
    // ADD THIS MISSING METHOD
    public Event getEventById(int eventId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) return null;
            
            String sql = "SELECT * FROM Event WHERE EventID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, eventId);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Event(
                    rs.getInt("EventID"),
                    rs.getString("EventName"),
                    rs.getString("EventType"),
                    rs.getDate("EventDate"),
                    rs.getInt("VenueID"),
                    rs.getInt("ManagerID"),
                    rs.getDouble("TotalBudget"),
                    rs.getInt("ExpectedAttendees")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error fetching event: " + e.getMessage());
        } finally {
            closeResources(conn, pstmt, rs);
        }
        return null;
    }
    
    public boolean registerForEvent(int attendeeId, int eventId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) return false;
            
            // Generate a simple RegID (in real app, use proper ID generation)
            int regId = (int) (System.currentTimeMillis() % 100000);
            
            String sql = "INSERT INTO Registration (RegID, EventID, AttendeeID, Paid, RegistrationDate) " +
                        "VALUES (?, ?, ?, FALSE, CURDATE())";
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt(1, regId);
            pstmt.setInt(2, eventId);
            pstmt.setInt(3, attendeeId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Registration error: " + e.getMessage());
            return false;
        } finally {
            closeResources(conn, pstmt, null);
        }
    }
    
    // ADDITIONAL USEFUL METHODS
    
    public List<Event> getEventsByType(String eventType) {
        List<Event> events = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) return events;
            
            String sql = "SELECT * FROM Event WHERE EventType = ? ORDER BY EventDate";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, eventType);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Event event = new Event(
                    rs.getInt("EventID"),
                    rs.getString("EventName"),
                    rs.getString("EventType"),
                    rs.getDate("EventDate"),
                    rs.getInt("VenueID"),
                    rs.getInt("ManagerID"),
                    rs.getDouble("TotalBudget"),
                    rs.getInt("ExpectedAttendees")
                );
                events.add(event);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching events by type: " + e.getMessage());
        } finally {
            closeResources(conn, pstmt, rs);
        }
        return events;
    }
    
    public List<Event> getUpcomingEvents() {
        List<Event> events = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) return events;
            
            String sql = "SELECT * FROM Event WHERE EventDate >= CURDATE() ORDER BY EventDate";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Event event = new Event(
                    rs.getInt("EventID"),
                    rs.getString("EventName"),
                    rs.getString("EventType"),
                    rs.getDate("EventDate"),
                    rs.getInt("VenueID"),
                    rs.getInt("ManagerID"),
                    rs.getDouble("TotalBudget"),
                    rs.getInt("ExpectedAttendees")
                );
                events.add(event);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching upcoming events: " + e.getMessage());
        } finally {
            closeResources(conn, pstmt, rs);
        }
        return events;
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
    
    // Overloaded method for PreparedStatement
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
