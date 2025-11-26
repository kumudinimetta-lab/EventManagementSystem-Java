// ManagerInterface.java
package com.eventmanagement.ui;

import com.eventmanagement.service.EventService;
import java.util.Scanner;

public class ManagerInterface implements UserInterface {
    private Scanner scanner;
    private EventService eventService;
    
    public ManagerInterface() {
        this.scanner = new Scanner(System.in);
        this.eventService = new EventService();
    }
    
    @Override
    public void showMenu() {
        System.out.println("\n=== MANAGER DASHBOARD ===");
        System.out.println("1. View My Events");
        System.out.println("2. View Event Details");
        System.out.println("3. Manage Event Resources");
        System.out.println("4. View Team Assignments");
        System.out.println("5. Generate Reports");
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
                System.out.println("Resource management feature coming soon...");
                break;
            case 4:
                System.out.println("Team assignments feature coming soon...");
                break;
            case 5:
                System.out.println("Reports feature coming soon...");
                break;
            case 6:
                System.out.println("Logging out...");
                break;
            default:
                System.out.println("Invalid option!");
        }
    }
}