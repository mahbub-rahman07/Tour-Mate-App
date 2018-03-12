package com.mahbub.tourmate.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Display;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mahbub.tourmate.R;
import com.mahbub.tourmate.database.DataSource;
import com.mahbub.tourmate.database.FireDB;
import com.mahbub.tourmate.fragments.AddTodoFragment;
import com.mahbub.tourmate.fragments.CameraFragment;
import com.mahbub.tourmate.fragments.CreateEventFragment;
import com.mahbub.tourmate.fragments.CurrencyConvertFragment;
import com.mahbub.tourmate.fragments.EventDetailFragment;
import com.mahbub.tourmate.fragments.EventFragment;
import com.mahbub.tourmate.fragments.LoginFragment;
import com.mahbub.tourmate.fragments.MapFragment;
import com.mahbub.tourmate.fragments.NearbyFragment;
import com.mahbub.tourmate.fragments.PlaceListFragment;
import com.mahbub.tourmate.fragments.RegisterFragment;
import com.mahbub.tourmate.fragments.TodoListFragment;
import com.mahbub.tourmate.model.Moment;
import com.mahbub.tourmate.model.PlaceResponse;
import com.mahbub.tourmate.model.Task;
import com.mahbub.tourmate.model.TourEvent;
import com.mahbub.tourmate.model.User;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        LoginFragment.OnSignUpButtonClickListener,
        LoginFragment.OnSignInButtonClickListener,
        NearbyFragment.OnSearchPlaceChoiceListener,
        CameraFragment.OnsaveCompleteListener
{

    private static final int PERMISSION_CODE = 100;
    private static final String TAG = "home";
    private static final int IMAGE_CAPTURE_CODE = 199 ;
    private FragmentManager mFragmentManager;
    private FloatingActionButton fab;
    private LatLng MyLocation;
    private String imagePath;
    private String imageName;
    private String eventID = null;
    private DataSource dataSource;

    public static User mUser= null;

    private String lastFrag = null;
    int countBack = 0;

    //GPS location
    private FusedLocationProviderClient mClient = null;
    private LocationCallback mCallback = null;
    private LocationRequest mLocationRequest = null;
    private boolean isLoggedIn = false;
    public static final String DISPLAY_MAIL_KEY = "usermail";
    //Firebase:
    private FirebaseDatabase fDatabase;
    private DatabaseReference fRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthstateListener;

    public static String mUserID = null;


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthstateListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        dataSource = new DataSource(this);
        checkPermission();

        mFragmentManager = getSupportFragmentManager();

        //TODO: set logged user
        //check current user is logged in

        mAuth = FirebaseAuth.getInstance();

        mAuthstateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null ) {
                    checkLoginCredential();
                }else{
                    isLoggedIn = true;
                    lastFrag = "event";
                    mUser = dataSource.getUser(mUserID = mAuth.getUid());
                    DatabaseReference ref = FireDB.getInstance().getReference().child(HomeActivity.mUserID);
                    ref.keepSynced(true);
                    displayCurrentFragment();
                }
            }
        };
        mUserID = mAuth.getUid();
        Log.d(TAG, "onCreate: "+mUserID);


        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void checkLoginCredential() {
        try {
            mFragmentManager
                    .beginTransaction()
                    .replace(R.id.mainContainer, new LoginFragment())
                    .commit();
        }catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {

        Log.d(TAG, "onBackPressed: "+mFragmentManager.getBackStackEntryCount()+" "+lastFrag);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if (countBack > 0) {
            displayCurrentFragment();
            countBack--;
            Log.d(TAG, "onBackPressed: " + countBack +" last "+lastFrag);
        }
        else {
            super.onBackPressed();
        }

    }

    private void showAllBackStack(String tag) {
        int len = mFragmentManager.getBackStackEntryCount();
        StringBuilder msg = new StringBuilder();
        for(int i = len -1; i >= 0 ; i--) {
            msg.append("index "+ i).append(mFragmentManager.getBackStackEntryAt(i).getName()).append("\n");
        }
        Log.d(TAG, "showAllBackStack: "+msg.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem logoutItem = menu.findItem(R.id.action_signout);
        MenuItem loginItem = menu.findItem(R.id.action_signin);
        if (isLoggedIn) {
            logoutItem.setVisible(true);
            loginItem.setVisible(false);

        }else{
            loginItem.setVisible(true);
            logoutItem.setVisible(false);
        }


        return super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_home:
               if (isLoggedIn) {
                   lastFrag = "event";
                   displayCurrentFragment();
               }else{
                   checkLoginCredential();
               }
                break;
            case R.id.action_signin:
                isLoggedIn = true;
                checkLoginCredential();
                break;
            case R.id.action_signout:
                isLoggedIn = false;
                checkLoginCredential();
                mAuth.signOut();
                mUser = null;
                mUserID = null;
                break;
//            case R.id.action_delete_all:
//                DataSource dataSource = new DataSource(this);
//                dataSource.deleteCompleteDB();
//                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.nav_event) {
            if (isLoggedIn) {
                lastFrag = "event";
                displayCurrentFragment();
            }else {
                checkLoginCredential();
            }
        }else if (id == R.id.nav_todo) {
           if (isLoggedIn) {
               lastFrag = "todo";
               displayCurrentFragment();
           }else{
               checkLoginCredential();
           }

        }
        else if (id == R.id.nav_weather) {
            Intent intent = new Intent(HomeActivity.this, WeatherActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_map) {
            lastFrag = "map";
            displayCurrentFragment();
        } else if (id == R.id.nav_nearby) {
            lastFrag = "nearby";
            displayCurrentFragment();
            //mFragmentManager.beginTransaction().replace(R.id.mainContainer,new NearbyFragment(), "nearby").commit();

        }
//        else if (id == R.id.nav_manage) {
//            Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
//            startActivity(intent);
//        }
        else if (id == R.id.nav_about_me) {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.contact_me_layout);

            dialog.show();

        }else if (id == R.id.nav_currency_convert) {
            mFragmentManager.beginTransaction().replace(R.id.mainContainer, new CurrencyConvertFragment()).commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onSignupButtonClicked() {
        RegisterFragment fragment =  new RegisterFragment();
        fragment.setOnRegisterSuccessfullyListener(new RegisterFragment.OnRegisterSuccessfullyListener() {
            @Override
            public void onRegisterSuccess() {
                checkLoginCredential();
            }
        });
        mFragmentManager
                .beginTransaction()
                .replace(R.id.mainContainer,fragment)
                .commit();
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,}, PERMISSION_CODE);
            return;

        }
        getLocation();
    }

    @Override
    public void onSigninButtonClicked(final String uid) {
        //TODO: login credential
        Log.d(TAG, "onSigninButtonClicked: "+uid);

        if (!isLoggedIn) {
            mUserID = uid;
            mUser = dataSource.getUser(uid);
            if (mUser == null) {
                fRef = FirebaseDatabase.getInstance().getReference().child("User");
                fRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot d: dataSnapshot.getChildren()){
                            User u = d.getValue(User.class);
                            if (u != null && u.getUserAuth().equals(uid)) {
                                mUser = u;
                                dataSource.insertUser(mUser);
                                Log.d(TAG, "onDataChange: "+u.toString());
                                break;
                            }else{
                                Log.d(TAG, "onDataChange: null");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
        if (uid == null) {
            isLoggedIn = false;
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }else{
            retrieveDataFromDatabase();
        }


    }

    private void retrieveDataFromDatabase() {
        isLoggedIn = true;
        lastFrag = "event";
        displayCurrentFragment();
    }

    private void displayCurrentFragment() {
        if (lastFrag == null) {
            return;
        }
        Log.d(TAG, "displayCurrentFragment: "+lastFrag);
        countBack++;
        try {
            if (isLoggedIn && lastFrag == null) {
                lastFrag = "event";
            }
            if (lastFrag.equals("event")) {
                EventFragment eventFragment = new EventFragment();
                eventFragment.setOnCreateButtonClickListener(new EventFragment.OnCreateButtonClickListener() {
                    @Override
                    public void onCreateButtonClicked() {
                        lastFrag = "event";
                        mFragmentManager.beginTransaction().replace(R.id.mainContainer, new CreateEventFragment()).commit();
                    }
                });
                eventFragment.setOnEventClickListener(new EventFragment.OnEventClickListener() {
                    @Override
                    public void onEventClicked(TourEvent event) {
                        EventDetailFragment eventDetailFragment = new EventDetailFragment();
                        eventDetailFragment.setEvetID(event);
                        lastFrag = "event";
                        eventDetailFragment.setOnDeleteEventClickedListenr(new EventDetailFragment.OnDeleteEventClickedListenr() {
                            @Override
                            public void onEventDelete() {
                                lastFrag = "event";
                                displayCurrentFragment();
                            }
                        });
                        mFragmentManager.beginTransaction().replace(R.id.mainContainer, eventDetailFragment).commit();
                        eventDetailFragment.setOnEventEditClickListener(new EventDetailFragment.OnEventEditClickListener() {
                            @Override
                            public void onEditClicked(TourEvent event) {
                                CreateEventFragment createEventFragment = new CreateEventFragment();
                                createEventFragment.setUpdateEvent(event);
                                mFragmentManager.beginTransaction().replace(R.id.mainContainer, createEventFragment).commit();
                            }
                        });
                        eventDetailFragment.setOnCameraClickListener(new EventDetailFragment.OnCameraClickListener() {
                            @Override
                            public void onCameraClicked(String eid) {
                                eventID = eid;
                                checkCameraPermission();
                                Log.d(TAG, "onCameraClicked: clicked");
                            }
                        });
                        eventDetailFragment.setOnMomentsViewListener(new EventDetailFragment.OnMomentsViewListener() {
                            @Override
                            public void onGalleryViewClicked(String eid) {
                                Log.d(TAG, "onGalleryViewClicked: " + eid);
                                Intent galleryIntent = new Intent(HomeActivity.this, GridViewActivity.class);
                                galleryIntent.putExtra("EVENT_ID", eid);
                                startActivity(galleryIntent);
                            }
                        });
                    }
                });

                mFragmentManager.beginTransaction().replace(R.id.mainContainer, eventFragment, "event").commit();
            } else if (lastFrag.equals("nearby")) {
                lastFrag = "nearby";
                mFragmentManager.beginTransaction().replace(R.id.mainContainer, new NearbyFragment(), "nearby").commit();
            } else if (lastFrag.equals("map")) {
                MapFragment map = new MapFragment();
                map.setMyLocation(MyLocation);
                mFragmentManager.beginTransaction().replace(R.id.mainContainer, new MapFragment(), "map").commit();
            } else if (lastFrag.equals("todo")) {
                TodoListFragment todo = new TodoListFragment();
                todo.setOnaddTodoClickListener(new TodoListFragment.OnaddTodoClickListener() {
                    @Override
                    public void onAddTodoClicked() {
                        //fo to add task
                        lastFrag = "todo";
                        mFragmentManager.beginTransaction().replace(R.id.mainContainer, new AddTodoFragment()).commit();

                    }
                });
//                todo.setOnTaskEditListener(new TodoListFragment.OnTaskEditListener() {
//                    @Override
//                    public void onTaskEdit(Task task) {
//                        Log.d(TAG, "onTaskEdit: called");
//                        lastFrag = "todo";
//                        AddTodoFragment addTodoFragment = new AddTodoFragment();
//                        addTodoFragment.setUpdateTodo(task);
//                        mFragmentManager.beginTransaction().replace(R.id.mainContainer, addTodoFragment).commit();
//                    }
//                });
                todo.setOnTaskClickedListener(new TodoListFragment.OnTaskClickedListener() {
                    @Override
                    public void onTaskClicked(Task task) {
                        AddTodoFragment addTodoFragment = new AddTodoFragment();
                        addTodoFragment.setUpdateTodo(task);
                        mFragmentManager.beginTransaction().replace(R.id.mainContainer, addTodoFragment).commit();
                    }
                });
                mFragmentManager.beginTransaction().replace(R.id.mainContainer, todo).commit();
            }
        }catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private void captureCamreImage() {
        //TODO: Camera
        Log.d(TAG, "captureCamreImage: called");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //check camera exist
        if (intent.resolveActivity(getPackageManager()) != null) {
            try {
                File photoFile = createImageFile();
                if (photoFile != null) {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                    startActivityForResult(intent, IMAGE_CAPTURE_CODE);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{
            Toast.makeText(this,"Camera not found",Toast.LENGTH_SHORT).show();
        }

    }
    private File createImageFile() throws IOException {
        String timeStapm = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG" + timeStapm+"_";

        //File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
       // File storageDir = this.getDir("photo_dir", Context.MODE_PRIVATE);
        File storageDir = getAlbumStorageDir(this, "photo_folder");

        File img = File.createTempFile(imageFileName, ".jpg", storageDir);
        imageName = imageFileName+".jpg";
        imagePath = img.getAbsolutePath();
        Log.d(TAG, "createImageFile: "+imagePath);
        return img;

    }
    public File getAlbumStorageDir(Context context, String albumName) {

        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e(TAG, "Directory not created");
        }
        return file;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CAPTURE_CODE ) {
            setImage();


        }
    }

    private void setImage() {

        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (java.lang.NoSuchMethodError ignore) { // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }


        int targetWidth = point.x;
        int targetHight = point.y;


        BitmapFactory.Options options = new BitmapFactory.Options();
        BitmapFactory.decodeFile(imagePath);

        int photoWidth = options.outWidth;
        int photoHight = options.outHeight;

        int scaleFactor = Math.min(photoHight/targetHight, photoWidth/targetWidth );

        options.inSampleSize = scaleFactor;

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
      //  CameraFragment cameraFragment = new CameraFragment();
       // cameraFragment.setImage(eventID, bitmap, imageName, imagePath);
        Log.d("CAMERA", "setImage: "+imagePath);

       if( bitmap != null && dataSource.insertMoment(new Moment(eventID, imageName,imagePath))){
           Toast.makeText(this, "Image has been saved",Toast.LENGTH_SHORT).show();
           Intent i = new Intent(this, GridViewActivity.class);
           i.putExtra("EVENT_ID", eventID);
           startActivity(i);
       }
        //mFragmentManager.beginTransaction().replace(R.id.mainContainer, cameraFragment).commit();


    }
    private void checkCameraPermission() {
        Log.d(TAG, "checkCameraPermission: called");
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
                ) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA
                    }, PERMISSION_CODE);
            return;

        }
        Log.d(TAG, "checkCameraPermission: end");
        captureCamreImage();
    }

    public void getLocation() {
        mClient = LocationServices.getFusedLocationProviderClient(this);


        mLocationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(3000)
                .setFastestInterval(1000);

        mCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        MyLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        stopLocationUpdates();
                        Log.d("location", "onLocationResult: " + MyLocation.toString());
                        // return;
                    } else {
                        Log.d("location", "onLocationResult: null");
                    }
                }

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mClient.requestLocationUpdates(mLocationRequest, mCallback, null);

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("location", "onStop: called");
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        if (mClient != null && mCallback != null) {
            mClient.removeLocationUpdates(mCallback);
        }
    }

    @Override
    public void onPlaceChoiceClicked(String placeType, int radius) {
        if (MyLocation != null) {
            PlaceListFragment fragment = new PlaceListFragment();
            fragment.setSearchPlaceByType(placeType, radius, MyLocation);
            lastFrag = "nearby";
            mFragmentManager.beginTransaction().replace(R.id.mainContainer, fragment).commit();

            fragment.setOnPlaceDetailClickListener(new PlaceListFragment.OnPlaceDetailClickListener() {
                @Override
                public void onPlaceClicked(PlaceResponse.Result result) {
                    Log.d(TAG, "onPlaceClicked: "+result.getName()+" "+
                            result.getGeometry().getLocation().getLat()+" "+
                            result.getGeometry().getLocation().getLng()
                    );
                    double lat = result.getGeometry().getLocation().getLat();
                    double lng = result.getGeometry().getLocation().getLng();
                    String snip = null;
                    if (result.getOpeningHours() != null) {
                        snip = result.getOpeningHours().isOpenNow() ? "Open Now" : "Closed";
                    }
                    MapFragment mapFragment = new MapFragment();
                    mapFragment.setMarkerOnMap(lat,lng, result.getName(), snip);
                    lastFrag = "nearby";
                    mFragmentManager.beginTransaction().replace(R.id.mainContainer, mapFragment).commit();
                }
            });

        }else{
            Toast.makeText(this, "Please activate the GPS",Toast.LENGTH_SHORT).show();
            Log.d("location", "onPlaceChoiceClicked: null");
        }
    }

    @Override
    public void onSaveCompeleted() {
        final EventDetailFragment fragment = new EventDetailFragment();
        TourEvent e = null;
        Query val = FirebaseDatabase.getInstance().getReference().child(mUserID).child("Event").child(eventID);
        val.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: "+dataSnapshot.getKey());
                TourEvent e = dataSnapshot.getValue(TourEvent.class);
                if (e != null) {
                    fragment.setEvetID(e);
                    lastFrag = "event";
                    mFragmentManager.beginTransaction().replace(R.id.mainContainer, fragment).commit();
                    eventID = null;
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}
