package com.mahbub.tourmate.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.mahbub.tourmate.R;

import java.util.ArrayList;
import java.util.List;



public class PendingIntentService extends IntentService {
    private static final String TAG = "intent";

    public PendingIntentService() {
        super("PendingIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GeofencingEvent event = GeofencingEvent.fromIntent(intent);
        int transitionType = event.getGeofenceTransition();
        List<Geofence> trigraringGeofence = event.getTriggeringGeofences();

        String trasString = null;

        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                trasString = "Entered ";
                break;
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                trasString = "Exited ";
                break;
        }

        ArrayList<String> triggraingGeoffenceIDs = new ArrayList<>();
        try{

            for (Geofence geofence : trigraringGeofence) {
                triggraingGeoffenceIDs.add(geofence.getRequestId());
            }
        }catch (NullPointerException e){}

        String notifyString = trasString +": "+ TextUtils.join(", ",triggraingGeoffenceIDs);
        Log.d(TAG, "onHandleIntent: "+notifyString);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setContentTitle(notifyString);
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(991, builder.build());

    }
}
