// AdminInterface.java - ENHANCED VERSION
package com.eventmanagement.ui;

import com.eventmanagement.dao.AdminDAO;
import com.eventmanagement.dao.AttendeeDAO;
import com.eventmanagement.dao.EventDAO;
import com.eventmanagement.dao.EventStatusDAO;
import com.eventmanagement.dao.UserDAO;
import com.eventmanagement.model.Attendee;
import com.eventmanagement.model.Event;
import com.eventmanagement.model.EventStatus;
import com.eventmanagement.model.User;
import java.util.List;
import java.util.Scanner;

public class AdminInterface implements UserInterface {
    private Scanner scanner;
    private EventDAO eventDAO;
    private AttendeeDAO attendeeDAO;
    private UserDAO userDAO;
    private EventStatusDAO eventStatusDAO;
    private AdminDAO adminDAO;
    
    public AdminInterface() {
        this.scanner = new Scanner(System.in);
        this.eventDAO = new EventDAO();
        this.attendeeDAO = new AttendeeDAO();
        this.userDAO = new UserDAO();
        this.eventStatusDAO = new EventStatusDAO();
        this.adminDAO = new AdminDAO();
    }
    
    @Override
    public void showMenu() {
        System.out.println("\n=== ADMIN DASHBOARD ===");
        System.out.println("1. View All Events");
        System.out.println("2. View Event Details");
        System.out.println("3. View All Attendees");
        System.out.println("4. View Event Status");
        System.out.println("5. Manage Users");
        System.out.println("6. System Statistics");
        System.out.println("7. Update Event Status");
        System.out.println("8. Logout");
        System.out.print("Choose an option: ");
    }
    
    @Override
    public void handleUserInput(int choice) {
        switch (choice) {
            case 1:
                viewAllEvents();
                break;
            case 2:
                System.out.print("Enter Event ID: ");
                int eventId = scanner.nextInt();
                displayEventDetails(eventId);
                break;
            case 3:
                displayAllAttendees();
                break;
            case 4:
                viewEventStatus();
                break;
            case 5:
                manageUsers();
                break;
            case 6:
                viewSystemStatistics();
                break;
            case 7:
                updateEventStatus();
                break;
            case 8:
                System.out.println("Logging out...");
                break;
            default:
                System.out.println("Invalid option!");
        }
    }
    
    private void viewAllEvents() {
        List<Event> events = eventDAO.getAllEvents();
        System.out.println("\n=== ALL EVENTS ===");
        if (events.isEmpty()) {
            System.out.println("No events found.");
        } else {
            System.out.printf("%-8s %-25s %-15s %-12s %-12s %-15s%n", 
                "ID", "Name", "Type", "Date", "Budget", "Attendees");
            System.out.println("------------------------------------------------------------------------------");
            for (Event event : events) {
                System.out.printf("%-8d %-25s %-15s %-12s $%-11.2f %-15d%n",
                    event.getEventID(),
                    truncate(event.getEventName(), 23),
                    truncate(event.getEventType(), 13),
                    event.getEventDate(),
                    event.getTotalBudget(),
                    event.getExpectedAttendees());
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
            System.out.println("Venue ID: " + event.getVenueID());
            System.out.println("Manager ID: " + event.getManagerID());
        } else {
            System.out.println("Event not found!");
        }
    }
    
    private void displayAllAttendees() {
        List<Attendee> attendees = attendeeDAO.getAllAttendees();
        System.out.println("\n=== ALL ATTENDEES ===");
        if (attendees.isEmpty()) {
            System.out.println("No attendees found.");
        } else {
            System.out.printf("%-8s %-20s %-25s %-15s %-10s%n", 
                "ID", "Name", "Email", "Status", "Location");
            System.out.println("-----------------------------------------------------------------------");
            for (Attendee attendee : attendees) {
                System.out.printf("%-8d %-20s %-25s %-15s %-10s%n",
                    attendee.getAttendeeID(),
                    truncate(attendee.getName(), 18),
                    truncate(attendee.getEmail(), 23),
                    attendee.getRegistrationStatus(),
                    attendee.isLocationSharingEnabled() ? "Enabled" : "Disabled");
            }
        }
    }
    
    private void viewEventStatus() {
        List<EventStatus> statusList = eventStatusDAO.getAllEventStatus();
        System.out.println("\n=== EVENT STATUS OVERVIEW ===");
        if (statusList.isEmpty()) {
            System.out.println("No event status records found.");
        } else {
            System.out.printf("%-8s %-12s %-15s %-12s %-20s%n", 
                "Event ID", "Resources", "Emergency", "Status", "Remarks");
            System.out.println("----------------------------------------------------------------");
            for (EventStatus status : statusList) {
                System.out.printf("%-8d %-12s %-15s %-12s %-20s%n",
                    status.getEventID(),
                    status.isResourcesAchieved() ? "✅ Ready" : "❌ Pending",
                    truncate(status.getEmergencyCircumstances(), 13),
                    truncate(status.getOverallStatus(), 10),
                    truncate(status.getRemarks(), 18));
            }
        }
    }
    
    private void manageUsers() {
        System.out.println("\n=== USER MANAGEMENT ===");
        System.out.println("1. View All Users");
        System.out.println("2. Create New User");
        System.out.println("3. Reset User Password");
        System.out.println("4. Back to Main Menu");
        System.out.print("Choose an option: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        switch (choice) {
            case 1:
                viewAllUsers();
                break;
            case 2:
                createNewUser();
                break;
            case 3:
                resetUserPassword();
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid option!");
        }
    }
    
    private void viewAllUsers() {
        // This would fetch from Users table
        System.out.println("\nUser list feature coming soon...");
    }
    
    private void createNewUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter role: ");
        String role = scanner.nextLine();
        
        System.out.println("User creation feature coming soon...");
        System.out.println("Would create user: " + username + " with role: " + role);
    }
    
    private void resetUserPassword() {
        System.out.print("Enter username to reset password: ");
        String username = scanner.nextLine();
        System.out.println("Password reset feature coming soon for user: " + username);
    }
    
    private void viewSystemStatistics() {
        System.out.println("\n=== SYSTEM STATISTICS ===");
        
        // Get counts from database
        int totalEvents = eventDAO.getAllEvents().size();
        int totalAttendees = attendeeDAO.getAllAttendees().size();
        int upcomingEvents = eventDAO.getUpcomingEvents().size();
        
        System.out.println("Total Events: " + totalEvents);
        System.out.println("Upcoming Events: " + upcomingEvents);
        System.out.println("Total Attendees: " + totalAttendees);
        System.out.println("System Status: ✅ Operational");
        System.out.println("Database: ✅ Connected");
    }
    
    private void updateEventStatus() {
        System.out.print("Enter Event ID: ");
        int eventId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        System.out.print("Enter overall status (ON_TRACK/AT_RISK/DELAYED): ");
        String status = scanner.nextLine();
        
        System.out.print("Enter remarks: ");
        String remarks = scanner.nextLine();
        
        System.out.println("Event status update feature coming soon...");
        System.out.println("Would update Event " + eventId + " status to: " + status);
        System.out.println("Remarks: " + remarks);
    }
    
    private String truncate(String str, int length) {
        if (str == null || str.length() <= length) {
            return str;
        }
        return str.substring(0, length - 3) + "...";
    }
}
