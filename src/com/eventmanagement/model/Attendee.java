// Attendee.java
package com.eventmanagement.model;

public class Attendee {
    private int attendeeID;
    private String name;
    private String email;
    private String qrCode;
    private String registrationStatus;
    private boolean locationSharingEnabled;
    
    public Attendee(int attendeeID, String name, String email, String qrCode, 
                    String registrationStatus, boolean locationSharingEnabled) {
        this.attendeeID = attendeeID;
        this.name = name;
        this.email = email;
        this.qrCode = qrCode;
        this.registrationStatus = registrationStatus;
        this.locationSharingEnabled = locationSharingEnabled;
    }
    
    // Getters
    public int getAttendeeID() { return attendeeID; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getQrCode() { return qrCode; }
    public String getRegistrationStatus() { return registrationStatus; }
    public boolean isLocationSharingEnabled() { return locationSharingEnabled; }
    
    @Override
    public String toString() {
        return String.format("Attendee{ID=%d, Name='%s', Email='%s', Status='%s'}", 
                           attendeeID, name, email, registrationStatus);
    }
}
