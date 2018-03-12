package com.mahbub.tourmate.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mahbuburrahman on 1/22/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "event.db";
    public static final int DATABASE_VERSION = 1;

    public static final String EVENT_TABLE = "event_tbl";
    public static final String COL_EVENT_ID = "event_id";
    public static final String COL_USER_AUTH_ID = "user_auth_id";
    public static final String COL_EVENT_NAME = "event_name";
    public static final String COL_EVENT_START_LOC_NAME = "event_start_loc_name";
    public static final String COL_EVENT_DEST_LOC_NAME = "event_dest_loc_name";
    public static final String COL_EVENT_START = "staring_date";
    public static final String COL_EVENT_CREATE= "creating_date";
    public static final String COL_EVENT_MEMBER= "event_member";
    public static final String COL_EVENT_BUDGET = "budget";

    public static final String CREATE_TABLE_EVENT = "create table " + EVENT_TABLE + "( " +
            COL_EVENT_ID +" integer primary key, "+
            COL_USER_AUTH_ID +" text, "+
            COL_EVENT_NAME +" text, "+
            COL_EVENT_START_LOC_NAME +" text, "+
            COL_EVENT_DEST_LOC_NAME +" text, "+
            COL_EVENT_START +" text, "+
            COL_EVENT_CREATE +" text, "+
            COL_EVENT_MEMBER +" integer, "+
            COL_EVENT_BUDGET +" real );";



    public static final String EVENT_EXPENSE_TABLE = "expense_tbl";
    public static final String COL_EVENT_EXPENSE_ID = "event_expense_id";
    public static final String COL_EXPENSE_ID = "expense_id";
    public static final String COL_EXPENSE_NAME = "expense_name";
    public static final String COL_EXPENSE_COST = "expense_cost";
    public static final String COL_EXPENSE_DATE = "expense_date";

    public static final String CREATE_TABLE_EXPENSE_EVENT = "create table " + EVENT_EXPENSE_TABLE + "( " +
            COL_EXPENSE_ID +" integer primary key, "+
            COL_EVENT_EXPENSE_ID +" integer, "+
            COL_EXPENSE_NAME +" text, "+
            COL_EXPENSE_COST +" real, "+
            COL_EXPENSE_DATE +" text );";

    public static final String EVENT_MOMENT_TABLE = "moment_tbl";
    public static final String COL_EVENT_MOMENT_ID = "event_moment_id";
    public static final String COL_MOMENT_ID = "moment_id";
    public static final String COL_MOMENT_NAME = "moment_name";
    public static final String COL_MOMENT_PATH = "moment_path";

    public static final String CREATE_TABLE_MOMENT_EVENT = "create table " + EVENT_MOMENT_TABLE + "( " +
            COL_MOMENT_ID +" integer primary key, "+
            COL_EVENT_MOMENT_ID +" integer, "+
            COL_EXPENSE_NAME +" text, "+
            COL_MOMENT_NAME +" text, "+
            COL_MOMENT_PATH +" text );";


    public static final String TODO_TABLE = "todo_tbl";
    public static final String COL_TODO_ID = "todo_id";
    public static final String COL_TODO_NAME = "todo_name";
    public static final String COL_TODO_AUTH= "todo_auth";
    public static final String COL_TODO_TIME = "todo_time";

    public static final String CREATE_TABLE_TODO = "create table " + TODO_TABLE + "( " +
            COL_TODO_ID +" integer primary key, "+
            COL_TODO_NAME +" text, "+
            COL_TODO_AUTH +" text, "+
            COL_TODO_TIME +" text );";

    public static final String USER_TABLE = "user_tbl";
    public static final String COL_USER_ID = "user_id";
    public static final String COL_USER_NAME = "user_name";
    public static final String COL_USER_MAIL = "user_mail";
    public static final String COL_USER_AUTH = "user_auth";
    public static final String COL_USER_PASSWORD = "user_pass";

    public static final String CREATE_TABLE_USER = "create table " + USER_TABLE + "( " +
            COL_USER_ID +" integer primary key, "+
            COL_USER_NAME +" text, "+
            COL_USER_MAIL +" text, "+
            COL_USER_AUTH +" text, "+
            COL_USER_PASSWORD +" text );";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //called once in life time of app
        db.execSQL(CREATE_TABLE_EVENT);
        db.execSQL(CREATE_TABLE_EXPENSE_EVENT);
        db.execSQL(CREATE_TABLE_MOMENT_EVENT);
        db.execSQL(CREATE_TABLE_TODO);
        db.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
