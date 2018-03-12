package com.mahbub.tourmate.model;

import android.content.ContentValues;

import com.mahbub.tourmate.database.DatabaseHelper;

/**
 * Created by Mahbuburrahman on 2/7/18.
 */

public class EventExpense {
    private String expenseID;
    private String eventExpenseID;
    private String expenseName;
    private double expenseCost;
    private String expenseDate;

    public EventExpense() {
    }

    public EventExpense(String expenseID, String eventExpenseID, String expenseName, double expenseCost, String expenseDate) {
        this.expenseID = expenseID;
        this.eventExpenseID = eventExpenseID;
        this.expenseName = expenseName;
        this.expenseCost = expenseCost;
        this.expenseDate = expenseDate;
    }

    public String getEventExpenseID() {
        return eventExpenseID;
    }

    public void setEventExpenseID(String eventExpenseID) {
        this.eventExpenseID = eventExpenseID;
    }

    public String getExpenseID() {
        return expenseID;
    }

    public void setExpenseID(String expenseID) {
        this.expenseID = expenseID;
    }

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }




    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public double getExpenseCost() {
        return expenseCost;
    }

    public void setExpenseCost(double expenseCost) {
        this.expenseCost = expenseCost;
    }

    @Override
    public String toString() {
        return "EventExpense{" +
                "expenseID=" + expenseID +
                ", eventExpenseID=" + eventExpenseID +
                ", expenseName='" + expenseName + '\'' +
                ", expenseCost=" + expenseCost +
                ", expenseDate='" + expenseDate + '\'' +
                '}';
    }

    public ContentValues toValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_EVENT_EXPENSE_ID, getEventExpenseID());
        contentValues.put(DatabaseHelper.COL_EXPENSE_NAME, getExpenseName());
        contentValues.put(DatabaseHelper.COL_EXPENSE_COST, getExpenseCost());
        contentValues.put(DatabaseHelper.COL_EXPENSE_DATE, getExpenseDate());
        return contentValues;
    }
}
