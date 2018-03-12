package com.mahbub.tourmate.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mahbub.tourmate.model.EventExpense;
import com.mahbub.tourmate.model.Moment;
import com.mahbub.tourmate.model.Task;
import com.mahbub.tourmate.model.TourEvent;
import com.mahbub.tourmate.model.User;

import java.util.ArrayList;

/**
 * Created by Mahbuburrahman on 1/22/18.
 */

public class DataSource {

    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;
    private Context mContext;


    public static final String TAG = "datasource";

    public DataSource(Context context) {
        mContext = context;
        mDatabaseHelper = new DatabaseHelper(context);
        mDatabase = mDatabaseHelper.getWritableDatabase();
    }

    public void open() {
        mDatabase = mDatabaseHelper.getWritableDatabase();
    }
    public void close() {
        mDatabase.close();
    }


    public boolean deleteCompleteDB() {
        this.open();
        mContext.deleteDatabase(mDatabaseHelper.DATABASE_NAME);
        this.close();
        return true;
    }
    public boolean deleteExpenseTable() {
        this.open();
        mDatabase.delete(mDatabaseHelper.EVENT_EXPENSE_TABLE, null, null);
        this.close();
        return true;
    }

    public boolean deleteEventTable() {
        this.open();
        mDatabase.delete(mDatabaseHelper.EVENT_TABLE, null, null);
        this.close();
        return true;
    }
    public boolean deleteTaskTable() {
        this.open();
        mDatabase.delete(mDatabaseHelper.TODO_TABLE, null, null);
        this.close();
        return true;
    }
/*    //TODO: insert
    public boolean insertEvent(TourEvent event){
        this.open();
        ContentValues values = event.toValues();
        long insertedRow = mDatabase.insert(mDatabaseHelper.EVENT_TABLE, null, values);
        this.close();
        Log.d(TAG, "insertEvent: "+insertedRow+" " +event.toString());

        if (insertedRow > 0) {
            return true;
        }
        return false;
    }

    //TODO: read
    public ArrayList<TourEvent> readAllEvents(String auth) {
        this.open();
        //String[] columns = new String[]{mDatabaseHelper.COL_EVENT_ID, mDatabaseHelper.COL_EVENT_NAME, mDatabaseHelper.COL};

       Cursor cursor = mDatabase.query(mDatabaseHelper.EVENT_TABLE, null, mDatabaseHelper.COL_USER_AUTH_ID+ " = '" + auth + "'",
                null,null,null,mDatabaseHelper.COL_EVENT_ID +" DESC");

       ArrayList<TourEvent> allEvents= new ArrayList<>();

       cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_EVENT_ID));
                String name = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_EVENT_NAME));
                String start_loc = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_EVENT_START_LOC_NAME));
                String dest_loc = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_EVENT_DEST_LOC_NAME));
                String create = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_EVENT_CREATE));
                String start = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_EVENT_START));
                int people = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_EVENT_MEMBER));
                double budget = cursor.getDouble(cursor.getColumnIndex(mDatabaseHelper.COL_EVENT_BUDGET));

                allEvents.add(new TourEvent(id, auth, name,start_loc,dest_loc, start, create,people, budget ));
                Log.d("all", "event ID: " + id);
            } while (cursor.moveToNext());

        }
        cursor.close();
        this.close();
       return allEvents;
    }
    //Read Event by id

    public TourEvent getEventById(int id) {
        Log.d(TAG, "getEventById: "+id);
        this.open();
        Cursor cursor = mDatabase.query(mDatabaseHelper.EVENT_TABLE, null,
                mDatabaseHelper.COL_EVENT_ID +"="+id,null,null,null,null);
        TourEvent event = null;
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0){
            String auth = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_USER_AUTH_ID));
            String name = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_EVENT_NAME));
            String start_loc = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_EVENT_START_LOC_NAME));
            String dest_loc = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_EVENT_DEST_LOC_NAME));
            String create = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_EVENT_CREATE));
            String start = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_EVENT_START));
            int people = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_EVENT_MEMBER));
            double budget = cursor.getDouble(cursor.getColumnIndex(mDatabaseHelper.COL_EVENT_BUDGET));
            event = new TourEvent(id, auth, name,start_loc,dest_loc,start, create, people,budget );
            Log.d(TAG, "getEventById: "+event.toString());
        }

        cursor.close();
        this.close();

        return event;
    }



    //TODO: update

    public boolean updateEvent(TourEvent event) {
        this.open();
        ContentValues contentValues = event.toValues();
        Log.d("update", "updateEvent: "+event.toString());

        int updatedValue = mDatabase.update(mDatabaseHelper.EVENT_TABLE, contentValues,
                mDatabaseHelper.COL_EVENT_ID +"="+event.getEventID(),null);

        this.close();
        if (updatedValue > 0) {
            return true;
        }
        return false;

    }

    //TODO: delete
    public boolean deleteById(int id) {
        this.open();
        int deleteValue = mDatabase.delete(mDatabaseHelper.EVENT_TABLE, mDatabaseHelper.COL_EVENT_ID +"="+ id, null);
        this.close();
        if (deleteValue > 0) {
            return true;
        }
        return false;
    }

//Expense table
    public ArrayList<EventExpense> getExpenseByEventId(int id) {
        Log.d(TAG, "getEventById: "+id);
        this.open();
        Cursor cursor = mDatabase.query(mDatabaseHelper.EVENT_EXPENSE_TABLE, null,
                mDatabaseHelper.COL_EVENT_EXPENSE_ID +"="+id,null,null,null,null);
        ArrayList<EventExpense> allEventsExpense= new ArrayList<>();
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0){
                do {
                    int ex_id = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_EXPENSE_ID));
                    String ex_name = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_EXPENSE_NAME));
                    String ex_date = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_EXPENSE_DATE));
                    double cost = cursor.getDouble(cursor.getColumnIndex(mDatabaseHelper.COL_EXPENSE_COST));
                    allEventsExpense.add( new EventExpense(ex_id, id, ex_name, cost ,ex_date));
                    Log.d(TAG, "getEventExpenseName: " + ex_name);
                }while (cursor.moveToNext());
        }else{
            Log.d(TAG, "getEventExpenseName: null");
        }

        cursor.close();
        this.close();

        return allEventsExpense;
    }
    public boolean insertExpense(EventExpense eventExpense) {
        Log.d(TAG, "insertExpense: "+eventExpense.toString());
        this.open();
        ContentValues values = eventExpense.toValues();

        long inserValue = mDatabase.insert(mDatabaseHelper.EVENT_EXPENSE_TABLE, null, values);
        this.close();
        if (inserValue > 0) {
            return true;
        }
        return false;
    }
    public boolean deleteExpenseById(int id) {
        this.open();
        int deleteValue = mDatabase.delete(mDatabaseHelper.EVENT_EXPENSE_TABLE, mDatabaseHelper.COL_EXPENSE_ID +"="+ id, null);
        this.close();
        if (deleteValue > 0) {
            return true;
        }
        return false;
    }
    public boolean deleteAllExpenseByEventId(int id) {
        this.open();
        int deleteValue = mDatabase.delete(mDatabaseHelper.EVENT_EXPENSE_TABLE, mDatabaseHelper.COL_EVENT_EXPENSE_ID +"="+ id, null);
        this.close();
        if (deleteValue > 0) {
            return true;
        }
        return false;
    }

    public boolean updateExpense(EventExpense eventExpense) {
        Log.d(TAG, "insertExpense: "+eventExpense.toString());
        this.open();
        ContentValues values = eventExpense.toValues();

        int updatedValue = mDatabase.update(mDatabaseHelper.EVENT_EXPENSE_TABLE, values,
                mDatabaseHelper.COL_EXPENSE_ID +"="+eventExpense.getExpenseID(),null);
        this.close();
        if (updatedValue > 0) {
            return true;
        }
        return false;
    }
    */

    //Moment table
    public ArrayList<Moment> getMomentByEventId(String id) {
        Log.d(TAG, "getEventById: "+id);
        this.open();
        Cursor cursor = mDatabase.query(mDatabaseHelper.EVENT_MOMENT_TABLE, null,
                mDatabaseHelper.COL_EVENT_MOMENT_ID +"= '" + id +"' " ,null,null,null,null);
        ArrayList<Moment> allEventsMoments= new ArrayList<>();
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0){
            do {
                int mo_id = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_MOMENT_ID));
                String mo_name = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_MOMENT_NAME));
                String mo_path = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_MOMENT_PATH));
                allEventsMoments.add( new Moment(mo_id, id, mo_name, mo_path ));
                Log.d(TAG, "getEventExpenseName: " + mo_name);
            }while (cursor.moveToNext());
        }else{
            Log.d(TAG, "getEventExpenseName: null");
        }

        cursor.close();
        this.close();

        return allEventsMoments;
    }
    public boolean insertMoment(Moment moment) {
        Log.d(TAG, "insertMoment: "+moment.toString());
        this.open();
        ContentValues values = moment.toValues();

        long inserValue = mDatabase.insert(mDatabaseHelper.EVENT_MOMENT_TABLE, null, values);
        this.close();
        if (inserValue > 0) {
            return true;
        }
        return false;
    }

    public boolean updateMoment(Moment moment) {
        Log.d(TAG, "insertExpense: "+moment.toString());
        this.open();
        ContentValues values = moment.toValues();

        int updatedValue = mDatabase.update(mDatabaseHelper.EVENT_MOMENT_TABLE, values,
                mDatabaseHelper.COL_MOMENT_ID +"="+moment.getMomentID(),null);
        this.close();
        if (updatedValue > 0) {
            return true;
        }
        return false;
    }
    public boolean deleteMomentById(int id) {
        this.open();
        int deleteValue = mDatabase.delete(mDatabaseHelper.EVENT_MOMENT_TABLE, mDatabaseHelper.COL_MOMENT_ID +"="+ id, null);
        this.close();
        if (deleteValue > 0) {
            return true;
        }
        return false;
    }
    public boolean deleteAllMomentByEventId(int id) {
        this.open();
        int deleteValue = mDatabase.delete(mDatabaseHelper.EVENT_MOMENT_TABLE, mDatabaseHelper.COL_EVENT_MOMENT_ID +"="+ id, null);
        this.close();
        if (deleteValue > 0) {
            return true;
        }
        return false;
    }


    //TODO: Task Table;
  /*
    public boolean insertTodo(Task task) {
        Log.d(TAG, "inserttask: "+task.toString());
        this.open();
        ContentValues values = task.toValues();

        long inserValue = mDatabase.insert(mDatabaseHelper.TODO_TABLE, null, values);
        this.close();
        if (inserValue > 0) {
            return true;
        }
        return false;
    }
    public boolean deleteTodoById(int id) {
        this.open();
        int deleteValue = mDatabase.delete(mDatabaseHelper.TODO_TABLE, mDatabaseHelper.COL_TODO_ID +"="+ id, null);
        this.close();
        if (deleteValue > 0) {
            return true;
        }
        return false;
    }
    public boolean updateTask(Task task) {
        Log.d(TAG, "update todo: "+task.toString());
        this.open();
        ContentValues values = task.toValues();

        int updatedValue = mDatabase.update(mDatabaseHelper.TODO_TABLE, values,
                mDatabaseHelper.COL_TODO_ID +"="+task.getTask_id(),null);
        this.close();
        if (updatedValue > 0) {
            return true;
        }
        return false;
    }
    public ArrayList<Task> getAllTask(String userauth) {
        Log.d(TAG, "allTask: ");
        this.open();
        Cursor cursor = mDatabase.query(mDatabaseHelper.TODO_TABLE, null,
                mDatabaseHelper.COL_TODO_AUTH +" = '" + userauth + "'",null,null,null,null);
        ArrayList<Task> allTask= new ArrayList<>();
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0){
            do {
                int task_id = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_TODO_ID));
                String task_name = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_TODO_NAME));
                String task_time = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_TODO_TIME));
                allTask.add( new Task(task_id, task_name, task_time,userauth));
                Log.d(TAG, "allTask : " + task_name);
            }while (cursor.moveToNext());
        }else{
            Log.d(TAG, "allTask: null");
        }

        cursor.close();
        this.close();

        return allTask;
    }*/
//TODO: User table
public boolean insertUser(User user) {
    Log.d(TAG, "insert user: "+user.toString());
    this.open();
    ContentValues values = user.toValues();

    long inserValue = mDatabase.insert(mDatabaseHelper.USER_TABLE, null, values);
    this.close();
    if (inserValue > 0) {
        return true;
    }
    return false;
}
    public boolean deleteUserById(int id) {
        this.open();
        int deleteValue = mDatabase.delete(mDatabaseHelper.USER_TABLE, mDatabaseHelper.COL_USER_ID +"="+ id, null);
        this.close();
        if (deleteValue > 0) {
            return true;
        }
        return false;
    }
    public boolean deleteUserAuthById(String id) {
        this.open();
        int deleteValue = mDatabase.delete(mDatabaseHelper.USER_TABLE, mDatabaseHelper.COL_USER_AUTH+ " = '" + id + "'" , null);
        this.close();
        if (deleteValue > 0) {
            return true;
        }
        return false;
    }
    public boolean updateUser(User user) {
        Log.d(TAG, "update user: "+user.toString());
        this.open();
        ContentValues values = user.toValues();

        int updatedValue = mDatabase.update(mDatabaseHelper.USER_TABLE, values,
                mDatabaseHelper.COL_USER_ID +"="+user.getUserId(),null);
        this.close();
        if (updatedValue > 0) {
            return true;
        }
        return false;
    }
    public User getUser(String auth) {
        Log.d(TAG, "getUserById: "+auth );
        if (auth == null ) {
            return  null;
        }
        this.open();
        Cursor cursor = mDatabase.query(mDatabaseHelper.USER_TABLE, null,
                mDatabaseHelper.COL_USER_AUTH+ " = '" + auth + "'" ,null,null,null,null);
        User user = null;
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0){
            int id = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_USER_ID));
            String name = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_USER_NAME));
            String mail = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_USER_MAIL));
            user = new User(id,auth, name, mail );
            Log.d(TAG, "getUserByMail: "+user.toString());
        }

        cursor.close();
        this.close();

        return user;
    }

    public boolean isUserExist(String mail) {
        Log.d(TAG, "getUserById: "+mail );
        if (mail == null) {
            return false;
        }
        this.open();
        Cursor cursor = mDatabase.query(mDatabaseHelper.USER_TABLE, null,
                mDatabaseHelper.COL_USER_MAIL+ " = '" + mail + "'",null,null,null,null);

        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0){
            cursor.close();
            this.close();
            Log.d(TAG, "Mail: exist");
            return true;
        }
        cursor.close();
        this.close();
        return false;
    }


}
