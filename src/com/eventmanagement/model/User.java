// User.java
package com.eventmanagement.model;

public class User {
    private int userID;
    private String username;
    private String password;
    private String email;
    private String role;
    
    public User(int userID, String username, String password, String email, String role) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }
    
    // Getters and Setters
    public int getUserID() { return userID; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    
    @Override
    public String toString() {
        return String.format("User{ID=%d, Username='%s', Role='%s'}", userID, username, role);
    }
}
