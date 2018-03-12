package com.mahbub.tourmate.model;

import android.content.ContentValues;

import com.mahbub.tourmate.database.DatabaseHelper;

/**
 * Created by Mahbuburrahman on 2/6/18.
 */

public class TourEvent {

    private String eventID;
    private String userAuthID;
    private String eventName;
    private String satrtLocationName;
    private String destLocationName;
    private String startingDate;
    private String creatingDate;
    private int numOfPeople;
    private double estimateBudget;

    public TourEvent() {
    }

    public TourEvent(String eventID, String userAuthID, String eventName, String satrtLocationName, String destLocationName, String startingDate, String creatingDate, int numOfPeople, double estimateBudget) {
        this.eventID = eventID;
        this.userAuthID = userAuthID;
        this.eventName = eventName;
        this.satrtLocationName = satrtLocationName;
        this.destLocationName = destLocationName;
        this.startingDate = startingDate;
        this.creatingDate = creatingDate;
        this.numOfPeople = numOfPeople;
        this.estimateBudget = estimateBudget;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getUserAuthID() {
        return userAuthID;
    }

    public void setUserAuthID(String userAuthID) {
        this.userAuthID = userAuthID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getSatrtLocationName() {
        return satrtLocationName;
    }

    public void setSatrtLocationName(String satrtLocationName) {
        this.satrtLocationName = satrtLocationName;
    }

    public String getDestLocationName() {
        return destLocationName;
    }

    public void setDestLocationName(String destLocationName) {
        this.destLocationName = destLocationName;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public String getCreatingDate() {
        return creatingDate;
    }

    public void setCreatingDate(String creatingDate) {
        this.creatingDate = creatingDate;
    }

    public int getNumOfPeople() {
        return numOfPeople;
    }

    public void setNumOfPeople(int numOfPeople) {
        this.numOfPeople = numOfPeople;
    }

    public double getEstimateBudget() {
        return estimateBudget;
    }

    public void setEstimateBudget(double estimateBudget) {
        this.estimateBudget = estimateBudget;
    }

    public ContentValues toValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_USER_AUTH_ID, getUserAuthID());
        contentValues.put(DatabaseHelper.COL_EVENT_NAME, getEventName());
        contentValues.put(DatabaseHelper.COL_EVENT_START_LOC_NAME, getSatrtLocationName());
        contentValues.put(DatabaseHelper.COL_EVENT_DEST_LOC_NAME, getDestLocationName());
        contentValues.put(DatabaseHelper.COL_EVENT_START, getStartingDate());
        contentValues.put(DatabaseHelper.COL_EVENT_CREATE, getCreatingDate());
        contentValues.put(DatabaseHelper.COL_EVENT_MEMBER, getNumOfPeople());
        contentValues.put(DatabaseHelper.COL_EVENT_BUDGET, getEstimateBudget());
        return contentValues;
    }

    @Override
    public String toString() {
        return "TourEvent{" +
                "eventID=" + eventID +
                ", userAuthID='" + userAuthID + '\'' +
                ", eventName='" + eventName + '\'' +
                ", satrtLocationName='" + satrtLocationName + '\'' +
                ", destLocationName='" + destLocationName + '\'' +
                ", startingDate='" + startingDate + '\'' +
                ", creatingDate='" + creatingDate + '\'' +
                ", numOfPeople=" + numOfPeople +
                ", estimateBudget=" + estimateBudget +
                '}';
    }
}
