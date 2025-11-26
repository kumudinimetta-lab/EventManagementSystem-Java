// OrganizerInterface.java
package com.eventmanagement.ui;

import com.eventmanagement.dao.EventDAO;
import com.eventmanagement.dao.RoleAssignmentDAO;
import com.eventmanagement.model.Event;
import com.eventmanagement.model.RoleAssignment;
import java.util.List;
import java.util.Scanner;

public class OrganizerInterface implements UserInterface {  // Make sure this line has "implements UserInterface"
    private Scanner scanner;
    private EventDAO eventDAO;
    private RoleAssignmentDAO roleAssignmentDAO;
    private int organizerId;
    
    public OrganizerInterface(int organizerId) {
        this.scanner = new Scanner(System.in);
        this.eventDAO = new EventDAO();
        this.roleAssignmentDAO = new RoleAssignmentDAO();
        this.organizerId = organizerId;
    }
    
    @Override
    public void showMenu() {
        System.out.println("\n=== ORGANIZER DASHBOARD ===");
        System.out.println("1. View My Assignments");
        System.out.println("2. View Event Details");
        System.out.println("3. View All Events");
        System.out.println("4. Update Assignment Status");
        System.out.println("5. View Shift Schedule");
        System.out.println("6. Logout");
        System.out.print("Choose an option: ");
    }
    
    @Override
    public void handleUserInput(int choice) {
        switch (choice) {
            case 1:
                viewMyAssignments();
                break;
            case 2:
                System.out.print("Enter Event ID: ");
                int eventId = scanner.nextInt();
                displayEventDetails(eventId);
                break;
            case 3:
                viewAllEvents();
                break;
            case 4:
                updateAssignmentStatus();
                break;
            case 5:
                viewShiftSchedule();
                break;
            case 6:
                System.out.println("Logging out...");
                break;
            default:
                System.out.println("Invalid option!");
        }
    }
    
    private void viewMyAssignments() {
        List<RoleAssignment> assignments = roleAssignmentDAO.getAssignmentsByOrganizer(organizerId);
        System.out.println("\n=== MY ASSIGNMENTS ===");
        if (assignments.isEmpty()) {
            System.out.println("No assignments found.");
        } else {
            System.out.printf("%-12s %-20s %-15s %-12s %-12s %-15s%n", 
                "Event ID", "Role", "Location", "Shift Start", "Shift End", "Status");
            System.out.println("--------------------------------------------------------------------------------");
            for (RoleAssignment assignment : assignments) {
                System.out.printf("%-12d %-20s %-15s %-12s %-12s %-15s%n",
                    assignment.getEventID(),
                    truncate(assignment.getAssignedRole(), 18),
                    truncate(assignment.getLocationAssigned(), 13),
                    assignment.getShiftStartTime(),
                    assignment.getShiftEndTime(),
                    "Active");
            }
        }
    }
    
    private void displayEventDetails(int eventId) {
        Event event = eventDAO.getEventById(eventId);
        if (event != null) {
            System.out.println("\n=== EVENT DETAILS ===");
            System.out.println("ID: " + event.getEventID());
            System.out.println("Name: " + event.getEventName());
            System.out.println("Type: " + event.getEventType());
            System.out.println("Date: " + event.getEventDate());
            System.out.println("Budget: $" + event.getTotalBudget());
            System.out.println("Expected Attendees: " + event.getExpectedAttendees());
        } else {
            System.out.println("Event not found!");
        }
    }
    
    private void viewAllEvents() {
        List<Event> events = eventDAO.getAllEvents();
        System.out.println("\n=== ALL EVENTS ===");
        if (events.isEmpty()) {
            System.out.println("No events found.");
        } else {
            System.out.printf("%-8s %-25s %-15s %-12s%n", 
                "ID", "Name", "Type", "Date");
            System.out.println("----------------------------------------------------");
            for (Event event : events) {
                System.out.printf("%-8d %-25s %-15s %-12s%n",
                    event.getEventID(),
                    truncate(event.getEventName(), 23),
                    truncate(event.getEventType(), 13),
                    event.getEventDate());
            }
        }
    }
    
    private void updateAssignmentStatus() {
        System.out.print("Enter Assignment ID to update: ");
        int assignmentId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        System.out.print("Enter new status (COMPLETED/IN_PROGRESS/CANCELLED): ");
        String status = scanner.nextLine();
        
        System.out.println("Assignment status update feature coming soon...");
        System.out.println("Would update Assignment " + assignmentId + " to: " + status);
    }
    
    private void viewShiftSchedule() {
        List<RoleAssignment> assignments = roleAssignmentDAO.getAssignmentsByOrganizer(organizerId);
        System.out.println("\n=== MY SHIFT SCHEDULE ===");
        if (assignments.isEmpty()) {
            System.out.println("No shifts scheduled.");
        } else {
            for (RoleAssignment assignment : assignments) {
                System.out.println("Event: " + assignment.getEventID() + 
                                 " | Role: " + assignment.getAssignedRole() +
                                 " | Time: " + assignment.getShiftStartTime() + " - " + assignment.getShiftEndTime() +
                                 " | Location: " + assignment.getLocationAssigned());
            }
        }
    }
    
    private String truncate(String str, int length) {
        if (str == null || str.length() <= length) {
            return str;
        }
        return str.substring(0, length - 3) + "...";
    }
}