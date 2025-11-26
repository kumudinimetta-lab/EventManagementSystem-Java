// AuthenticationService.java
package com.eventmanagement.service;

import com.eventmanagement.dao.UserDAO;
import com.eventmanagement.model.User;

public class AuthenticationService {
    private UserDAO userDAO;
    private User currentUser;
    
    public AuthenticationService() {
        this.userDAO = new UserDAO();
    }
    
    public boolean login(String username, String password) {
        try {
            User user = userDAO.authenticateUser(username, password);
            if (user != null) {
                this.currentUser = user;
                System.out.println("Login successful! Welcome, " + user.getUsername());
                System.out.println("Your role: " + user.getRole());
                return true;
            } else {
                System.out.println("Invalid username or password!");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
            return false;
        }
    }
   
    
    public boolean register(String username, String password, String email, String role) {
        try {
            // Validate role
            if (!isValidRole(role)) {
                System.out.println("Invalid role! Please use: ATTENDEE, ORGANIZER, or VOLUNTEER");
                return false;
            }
            
            boolean success = userDAO.registerUser(username, password, email, role.toUpperCase());
            if (success) {
                System.out.println("Registration successful! You can now login.");
            } else {
                System.out.println("Registration failed! Username or email might be taken.");
            }
            return success;
        } catch (Exception e) {
            System.out.println("Registration error: " + e.getMessage());
            return false;
        }
    }
    
    private boolean isValidRole(String role) {
        String upperRole = role.toUpperCase();
        return upperRole.equals("ATTENDEE") || 
               upperRole.equals("ORGANIZER") || 
               upperRole.equals("VOLUNTEER") ||
               upperRole.equals("ADMIN") ||
               upperRole.equals("MANAGER") ||
               upperRole.equals("SECURITY") ||
               upperRole.equals("VENDOR") ||
               upperRole.equals("TECHNICIAN");
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
    
    public void logout() {
        this.currentUser = null;
        System.out.println("Logged out successfully!");
    }
}