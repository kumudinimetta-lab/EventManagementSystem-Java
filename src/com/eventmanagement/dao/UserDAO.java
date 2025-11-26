// UserDAO.java
package com.eventmanagement.dao;

import com.eventmanagement.database.DatabaseConnection;
import com.eventmanagement.model.User;
import java.sql.*;

public class UserDAO {
    
    public User authenticateUser(String username, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                return null;
            }
            
            String sql = "SELECT * FROM Users WHERE Username = ? AND Password = ?";
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new User(
                    rs.getInt("UserID"),
                    rs.getString("Username"),
                    rs.getString("Password"),
                    rs.getString("Email"),
                    rs.getString("Role")
                );
            }
        } catch (SQLException e) {
            System.out.println("Authentication error: " + e.getMessage());
        } finally {
            // Close resources in finally block
            closeResources(conn, pstmt, rs);
        }
        return null;
    }
    
    public boolean registerUser(String username, String password, String email, String role) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                return false;
            }
            
            String sql = "INSERT INTO Users (Username, Password, Email, Role) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, email);
            pstmt.setString(4, role);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Registration error: " + e.getMessage());
            return false;
        } finally {
            closeResources(conn, pstmt, null);
        }
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