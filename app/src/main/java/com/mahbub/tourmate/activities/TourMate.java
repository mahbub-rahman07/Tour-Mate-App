package com.mahbub.tourmate.activities;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Mahbuburrahman on 2/16/18.
 */

public class TourMate extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
       // FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
