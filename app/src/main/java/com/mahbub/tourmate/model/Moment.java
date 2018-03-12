package com.mahbub.tourmate.model;

import android.content.ContentValues;

import com.mahbub.tourmate.database.DatabaseHelper;

/**
 * Created by Mahbuburrahman on 2/10/18.
 */

public class Moment {
    private int momentID;
    private String eventMomentID;
    private String momentName;
    private String momentImagePath;

    public Moment() {
    }

    public Moment(int momentID, String eventMomentID, String momentName, String momentImagePath) {
        this.momentID = momentID;
        this.eventMomentID = eventMomentID;
        this.momentName = momentName;
        this.momentImagePath = momentImagePath;
    }

    public Moment(String eventMomentID, String momentName, String momentImagePath) {
        this.eventMomentID = eventMomentID;
        this.momentName = momentName;
        this.momentImagePath = momentImagePath;
    }

    public void setMomentID(int momentID) {
        this.momentID = momentID;
    }

    public String getEventMomentID() {
        return eventMomentID;
    }

    public void setEventMomentID(String eventMomentID) {
        this.eventMomentID = eventMomentID;
    }

    public int getMomentID() {
        return momentID;
    }

    public String getMomentName() {
        return momentName;
    }

    public void setMomentName(String momentName) {
        this.momentName = momentName;
    }

    public String getMomentImagePath() {
        return momentImagePath;
    }

    public void setMomentImagePath(String momentImagePath) {
        this.momentImagePath = momentImagePath;
    }

    @Override
    public String toString() {
        return "Moment{" +
                "momentID=" + momentID +
                ", eventMomentID=" + eventMomentID +
                ", momentName='" + momentName + '\'' +
                ", momentImagePath='" + momentImagePath + '\'' +
                '}';
    }
    public ContentValues toValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_EVENT_MOMENT_ID, getEventMomentID());
        contentValues.put(DatabaseHelper.COL_MOMENT_NAME, getMomentName());
        contentValues.put(DatabaseHelper.COL_MOMENT_PATH, getMomentImagePath());
        return contentValues;
    }
}
