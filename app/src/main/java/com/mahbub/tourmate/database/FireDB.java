package com.mahbub.tourmate.database;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Mahbuburrahman on 2/18/18.
 */

public class FireDB {

    private static FirebaseDatabase mDatabase;

    public static FirebaseDatabase getInstance() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }



}
