// VolunteerInterface.java
package com.eventmanagement.ui;

import com.eventmanagement.dao.EventDAO;
import com.eventmanagement.dao.RoleAssignmentDAO;
import com.eventmanagement.model.Event;
import com.eventmanagement.model.RoleAssignment;
import java.util.List;
import java.util.Scanner;

public class VolunteerInterface implements UserInterface {  // Make sure this line has "implements UserInterface"
    private Scanner scanner;
    private EventDAO eventDAO;
    private RoleAssignmentDAO roleAssignmentDAO;
    private int volunteerId;
    
    public VolunteerInterface(int volunteerId) {
        this.scanner = new Scanner(System.in);
        this.eventDAO = new EventDAO();
        this.roleAssignmentDAO = new RoleAssignmentDAO();
        this.volunteerId = volunteerId;
    }
    
    @Override
    public void showMenu() {
        System.out.println("\n=== VOLUNTEER DASHBOARD ===");
        System.out.println("1. View My Tasks");
        System.out.println("2. View Event Details");
        System.out.println("3. View Upcoming Events");
        System.out.println("4. Mark Task Complete");
        System.out.println("5. View Volunteer Schedule");
        System.out.println("6. Logout");
        System.out.print("Choose an option: ");
    }
    
    @Override
    public void handleUserInput(int choice) {
        switch (choice) {
            case 1:
                viewMyTasks();
                break;
            case 2:
                System.out.print("Enter Event ID: ");
                int eventId = scanner.nextInt();
                displayEventDetails(eventId);
                break;
            case 3:
                viewUpcomingEvents();
                break;
            case 4:
                markTaskComplete();
                break;
            case 5:
                viewVolunteerSchedule();
                break;
            case 6:
                System.out.println("Logging out...");
                break;
            default:
                System.out.println("Invalid option!");
        }
    }
    
    private void viewMyTasks() {
        List<RoleAssignment> assignments = roleAssignmentDAO.getAssignmentsByOrganizer(volunteerId);
        System.out.println("\n=== MY VOLUNTEER TASKS ===");
        if (assignments.isEmpty()) {
            System.out.println("No volunteer tasks assigned.");
        } else {
            System.out.printf("%-12s %-20s %-15s %-12s %-12s%n", 
                "Event ID", "Role", "Location", "Start Time", "End Time");
            System.out.println("-------------------------------------------------------------------");
            for (RoleAssignment assignment : assignments) {
                System.out.printf("%-12d %-20s %-15s %-12s %-12s%n",
                    assignment.getEventID(),
                    truncate(assignment.getAssignedRole(), 18),
                    truncate(assignment.getLocationAssigned(), 13),
                    assignment.getShiftStartTime(),
                    assignment.getShiftEndTime());
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
            System.out.println("Expected Attendees: " + event.getExpectedAttendees());
            
            System.out.println("\nVolunteer Instructions:");
            System.out.println("- Arrive 30 minutes before your shift");
            System.out.println("- Wear your volunteer badge at all times");
            System.out.println("- Report to the volunteer coordinator");
        } else {
            System.out.println("Event not found!");
        }
    }
    
    private void viewUpcomingEvents() {
        List<Event> events = eventDAO.getUpcomingEvents();
        System.out.println("\n=== UPCOMING EVENTS ===");
        if (events.isEmpty()) {
            System.out.println("No upcoming events.");
        } else {
            for (Event event : events) {
                System.out.printf("ID: %d | %s | %s | %s%n",
                    event.getEventID(),
                    event.getEventName(),
                    event.getEventType(),
                    event.getEventDate());
            }
        }
    }
    
    private void markTaskComplete() {
        System.out.print("Enter Assignment ID to mark complete: ");
        int assignmentId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        System.out.print("Enter completion notes: ");
        String notes = scanner.nextLine();
        
        System.out.println("✅ Task completion feature coming soon...");
        System.out.println("Would mark Assignment " + assignmentId + " as complete");
        System.out.println("Notes: " + notes);
    }
    
    private void viewVolunteerSchedule() {
        List<RoleAssignment> assignments = roleAssignmentDAO.getAssignmentsByOrganizer(volunteerId);
        System.out.println("\n=== MY VOLUNTEER SCHEDULE ===");
        if (assignments.isEmpty()) {
            System.out.println("No volunteer schedule found.");
        } else {
            System.out.println("Your upcoming volunteer shifts:");
            for (RoleAssignment assignment : assignments) {
                System.out.println("📅 Event ID: " + assignment.getEventID() +
                                 " | Role: " + assignment.getAssignedRole() +
                                 " | Date: " + assignment.getShiftStartTime() + " - " + assignment.getShiftEndTime() +
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