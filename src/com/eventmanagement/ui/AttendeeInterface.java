// AttendeeInterface.java
package com.eventmanagement.ui;

import com.eventmanagement.service.EventService;
import java.util.Scanner;

public class AttendeeInterface implements UserInterface {
    private Scanner scanner;
    private EventService eventService;
    private int attendeeId;
    
    public AttendeeInterface(int attendeeId) {
        this.scanner = new Scanner(System.in);
        this.eventService = new EventService();
        this.attendeeId = attendeeId;
    }
    
    @Override
    public void showMenu() {
        System.out.println("\n=== ATTENDEE DASHBOARD ===");
        System.out.println("1. View All Events");
        System.out.println("2. View Event Details");
        System.out.println("3. Register for Event");
        System.out.println("4. View My Registrations");
        System.out.println("5. Update Location");
        System.out.println("6. Logout");
        System.out.print("Choose an option: ");
    }
    
    @Override
    public void handleUserInput(int choice) {
        switch (choice) {
            case 1:
                eventService.displayAllEvents();
                break;
            case 2:
                System.out.print("Enter Event ID: ");
                int eventId = scanner.nextInt();
                eventService.displayEventDetails(eventId);
                break;
            case 3:
                System.out.print("Enter Event ID to register: ");
                int regEventId = scanner.nextInt();
                eventService.registerForEvent(attendeeId, regEventId);
                break;
            case 4:
                System.out.println("Feature coming soon...");
                break;
            case 5:
                System.out.println("Location update feature coming soon...");
                break;
            case 6:
                System.out.println("Logging out...");
                break;
            default:
                System.out.println("Invalid option!");
        }
    }
}