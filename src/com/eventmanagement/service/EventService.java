// EventService.java - ENHANCED VERSION
package com.eventmanagement.service;

import com.eventmanagement.dao.EventDAO;
import com.eventmanagement.dao.VenueDAO;
import com.eventmanagement.model.Event;
import com.eventmanagement.model.Venue;
import java.util.List;

public class EventService {
    private EventDAO eventDAO;
    private VenueDAO venueDAO;
    
    public EventService() {
        this.eventDAO = new EventDAO();
        this.venueDAO = new VenueDAO();
    }
    
    public void displayAllEvents() {
        List<Event> events = eventDAO.getAllEvents();
        System.out.println("\n=== ALL EVENTS ===");
        if (events.isEmpty()) {
            System.out.println("No events found.");
        } else {
            System.out.printf("%-8s %-25s %-15s %-12s %-10s%n", 
                "ID", "Name", "Type", "Date", "Attendees");
            System.out.println("------------------------------------------------------------------------");
            for (Event event : events) {
                System.out.printf("%-8d %-25s %-15s %-12s %-10d%n",
                    event.getEventID(),
                    truncate(event.getEventName(), 23),
                    truncate(event.getEventType(), 13),
                    event.getEventDate(),
                    event.getExpectedAttendees());
            }
        }
    }
    
    public void displayEventDetails(int eventId) {
        Event event = eventDAO.getEventById(eventId);
        if (event != null) {
            Venue venue = venueDAO.getVenueById(event.getVenueID());
            
            System.out.println("\n=== EVENT DETAILS ===");
            System.out.println("ID: " + event.getEventID());
            System.out.println("Name: " + event.getEventName());
            System.out.println("Type: " + event.getEventType());
            System.out.println("Date: " + event.getEventDate());
            System.out.println("Budget: $" + event.getTotalBudget());
            System.out.println("Expected Attendees: " + event.getExpectedAttendees());
            
            if (venue != null) {
                System.out.println("Venue: " + venue.getVenueName());
                System.out.println("Location: " + venue.getLocation());
                System.out.println("Capacity: " + venue.getCapacity());
            }
            
            // Display sub-events if any
            displaySubEvents(eventId);
        } else {
            System.out.println("Event not found!");
        }
    }
    
    private void displaySubEvents(int eventId) {
        // This would connect to SubEvent table - for future enhancement
        System.out.println("\nSub-events: Feature coming soon...");
    }
    
    public boolean registerForEvent(int attendeeId, int eventId) {
        boolean success = eventDAO.registerForEvent(attendeeId, eventId);
        if (success) {
            System.out.println("Successfully registered for the event!");
        } else {
            System.out.println("Registration failed! You might already be registered.");
        }
        return success;
    }
    
    public void displayUpcomingEvents() {
        List<Event> events = eventDAO.getUpcomingEvents();
        System.out.println("\n=== UPCOMING EVENTS ===");
        if (events.isEmpty()) {
            System.out.println("No upcoming events found.");
        } else {
            for (Event event : events) {
                System.out.println(event);
            }
        }
    }
    
    // Helper method to truncate long strings
    private String truncate(String str, int length) {
        if (str.length() <= length) {
            return str;
        }
        return str.substring(0, length - 3) + "...";
    }
}