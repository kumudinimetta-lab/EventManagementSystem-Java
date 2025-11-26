// Venue.java - NEW FILE in com.eventmanagement.model package
package com.eventmanagement.model;

public class Venue {
    private int venueID;
    private String venueName;
    private String location;
    private int capacity;
    
    public Venue(int venueID, String venueName, String location, int capacity) {
        this.venueID = venueID;
        this.venueName = venueName;
        this.location = location;
        this.capacity = capacity;
    }
    
    // Getters
    public int getVenueID() { return venueID; }
    public String getVenueName() { return venueName; }
    public String getLocation() { return location; }
    public int getCapacity() { return capacity; }
    
    @Override
    public String toString() {
        return String.format("Venue{ID=%d, Name='%s', Location='%s', Capacity=%d}", 
                           venueID, venueName, location, capacity);
    }
}