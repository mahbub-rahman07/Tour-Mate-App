package com.mahbub.tourmate.model;

import android.content.ContentValues;

import com.mahbub.tourmate.database.DatabaseHelper;

/**
 * Created by Mahbuburrahman on 10/2/18.
 */

public class Task {

    private String task_id;
    private String name,time, auth;

    public Task() {
    }

    public Task(String task_id, String name, String time, String auth) {
        this.task_id = task_id;
        this.name = name;
        this.time = time;
        this.auth = auth;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Task{" +
                "task_id=" + task_id +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", auth='" + auth + '\'' +
                '}';
    }

    public ContentValues toValues(){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_TODO_NAME, getName());
        values.put(DatabaseHelper.COL_TODO_AUTH, getAuth());
        values.put(DatabaseHelper.COL_TODO_TIME, getTime());
        return values;
    }
}
