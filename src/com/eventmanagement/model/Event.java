// Event.java
package com.eventmanagement.model;

import java.util.Date;

public class Event {
    private int eventID;
    private String eventName;
    private String eventType;
    private Date eventDate;
    private int venueID;
    private int managerID;
    private double totalBudget;
    private int expectedAttendees;
    
    public Event(int eventID, String eventName, String eventType, Date eventDate, 
                 int venueID, int managerID, double totalBudget, int expectedAttendees) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventType = eventType;
        this.eventDate = eventDate;
        this.venueID = venueID;
        this.managerID = managerID;
        this.totalBudget = totalBudget;
        this.expectedAttendees = expectedAttendees;
    }
    
    // Getters
    public int getEventID() { return eventID; }
    public String getEventName() { return eventName; }
    public String getEventType() { return eventType; }
    public Date getEventDate() { return eventDate; }
    public int getVenueID() { return venueID; }
    public int getManagerID() { return managerID; }
    public double getTotalBudget() { return totalBudget; }
    public int getExpectedAttendees() { return expectedAttendees; }
    
    @Override
    public String toString() {
        return String.format("Event{ID=%d, Name='%s', Type='%s', Date=%s}", 
                           eventID, eventName, eventType, eventDate);
    }
}