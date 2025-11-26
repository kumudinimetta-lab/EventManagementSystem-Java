// EventStatus.java
package com.eventmanagement.model;

import java.sql.Timestamp;

public class EventStatus {
    private int statusID;
    private int eventID;
    private int adminID;
    private Timestamp statusCheckTime;
    private boolean resourcesAchieved;
    private String emergencyCircumstances;
    private String overallStatus;
    private String remarks;
    
    public EventStatus(int statusID, int eventID, int adminID, Timestamp statusCheckTime,
                      boolean resourcesAchieved, String emergencyCircumstances, 
                      String overallStatus, String remarks) {
        this.statusID = statusID;
        this.eventID = eventID;
        this.adminID = adminID;
        this.statusCheckTime = statusCheckTime;
        this.resourcesAchieved = resourcesAchieved;
        this.emergencyCircumstances = emergencyCircumstances;
        this.overallStatus = overallStatus;
        this.remarks = remarks;
    }
    
    // Getters
    public int getStatusID() { return statusID; }
    public int getEventID() { return eventID; }
    public int getAdminID() { return adminID; }
    public Timestamp getStatusCheckTime() { return statusCheckTime; }
    public boolean isResourcesAchieved() { return resourcesAchieved; }
    public String getEmergencyCircumstances() { return emergencyCircumstances; }
    public String getOverallStatus() { return overallStatus; }
    public String getRemarks() { return remarks; }
}
