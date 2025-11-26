// EventManagementSystem.java - WITH PROPER IMPORTS
package com.eventmanagement;

import com.eventmanagement.database.DatabaseConnection;
import com.eventmanagement.model.User;
import com.eventmanagement.service.AuthenticationService;
import com.eventmanagement.ui.AdminInterface;
import com.eventmanagement.ui.AttendeeInterface;
import com.eventmanagement.ui.ManagerInterface;
import com.eventmanagement.ui.OrganizerInterface;
import com.eventmanagement.ui.VolunteerInterface;  // ADD THIS IMPORT
import com.eventmanagement.ui.UserInterface;
import java.util.Scanner;

public class EventManagementSystem {
    private AuthenticationService authService;
    private Scanner scanner;
    private boolean isRunning;
    
    public EventManagementSystem() {
        this.authService = new AuthenticationService();
        this.scanner = new Scanner(System.in);
        this.isRunning = true;
    }
    
    public void start() {
        System.out.println("=== WELCOME TO EVENT MANAGEMENT SYSTEM ===");
        
        while (isRunning) {
            if (authService.getCurrentUser() == null) {
                showMainMenu();
            } else {
                showRoleBasedInterface();
            }
        }
        
        scanner.close();
        System.out.println("Application closed successfully!");
    }
    
    private void showMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        switch (choice) {
            case 1:
                handleLogin();
                break;
            case 2:
                handleRegistration();
                break;
            case 3:
                isRunning = false;
                System.out.println("Thank you for using Event Management System!");
                break;
            default:
                System.out.println("Invalid option!");
        }
    }
    
    private void handleLogin() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        authService.login(username, password);
    }
    
    private void handleRegistration() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter role (ATTENDEE/ORGANIZER/VOLUNTEER/ADMIN/MANAGER): ");
        String role = scanner.nextLine();
        
        authService.register(username, password, email, role);
    }
    
    private void showRoleBasedInterface() {
        User currentUser = authService.getCurrentUser();
        UserInterface userInterface = null;
        
        // Create appropriate interface based on user role
        switch (currentUser.getRole().toUpperCase()) {
            case "ADMIN":
                userInterface = new AdminInterface();
                break;
            case "MANAGER":
                userInterface = new ManagerInterface();
                break;
            case "ATTENDEE":
                // For demo, using first attendee ID. In real app, map user to attendee
                userInterface = new AttendeeInterface(201);
                break;
            case "ORGANIZER":
                // For demo, using organizer ID 6. In real app, map user to organizer
                userInterface = new OrganizerInterface(6);
                break;
            case "VOLUNTEER":
                // For demo, using volunteer ID 11. In real app, map user to volunteer
                userInterface = new VolunteerInterface(11);
                break;
            default:
                System.out.println("Role-based interface for " + currentUser.getRole() + " coming soon!");
                authService.logout();
                return;
        }
        
        // Show role-specific menu
        boolean inSession = true;
        while (inSession && authService.getCurrentUser() != null) {
            userInterface.showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            if (choice == getLogoutOption(userInterface)) {
                authService.logout();
                inSession = false;
            } else {
                userInterface.handleUserInput(choice);
            }
        }
    }
    
    // Helper method to determine logout option based on interface
    private int getLogoutOption(UserInterface userInterface) {
        if (userInterface instanceof AdminInterface) return 8;
        if (userInterface instanceof ManagerInterface) return 6;
        if (userInterface instanceof OrganizerInterface) return 6;
        if (userInterface instanceof VolunteerInterface) return 6;
        if (userInterface instanceof AttendeeInterface) return 6;
        return 6; // Default
    }
    
    public static void main(String[] args) {
        EventManagementSystem app = new EventManagementSystem();
        app.start();
    }
}