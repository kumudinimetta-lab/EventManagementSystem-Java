// RoleAssignment.java
package com.eventmanagement.model;

import java.sql.Time;

public class RoleAssignment {
    private int assignmentID;
    private int eventID;
    private int managerID;
    private int assignedToOrganizerID;
    private String assignedRole;
    private Time shiftStartTime;
    private Time shiftEndTime;
    private String locationAssigned;
    
    public RoleAssignment(int assignmentID, int eventID, int managerID, int assignedToOrganizerID,
                         String assignedRole, Time shiftStartTime, Time shiftEndTime, String locationAssigned) {
        this.assignmentID = assignmentID;
        this.eventID = eventID;
        this.managerID = managerID;
        this.assignedToOrganizerID = assignedToOrganizerID;
        this.assignedRole = assignedRole;
        this.shiftStartTime = shiftStartTime;
        this.shiftEndTime = shiftEndTime;
        this.locationAssigned = locationAssigned;
    }
    
    // Getters
    public int getAssignmentID() { return assignmentID; }
    public int getEventID() { return eventID; }
    public int getManagerID() { return managerID; }
    public int getAssignedToOrganizerID() { return assignedToOrganizerID; }
    public String getAssignedRole() { return assignedRole; }
    public Time getShiftStartTime() { return shiftStartTime; }
    public Time getShiftEndTime() { return shiftEndTime; }
    public String getLocationAssigned() { return locationAssigned; }
}
