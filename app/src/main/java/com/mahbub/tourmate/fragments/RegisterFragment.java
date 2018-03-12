package com.mahbub.tourmate.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mahbub.tourmate.R;
import com.mahbub.tourmate.database.DataSource;
import com.mahbub.tourmate.model.User;
import com.mahbub.tourmate.utils.NetworkHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {
    private TextView mEmailRegisterView;
    private EditText mPasswordRegisterView;
    private EditText mNameRegisterView;
    private Context mContext;
    private ProgressDialog mProgressDialog;

    public static final String TAG = "REGISTER";

    // Firebase instance variables
    private FirebaseAuth mAuth;

    private OnRegisterSuccessfullyListener mListener;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        // TODO: Get hold of an instance of FirebaseAuth
        mAuth = FirebaseAuth.getInstance();
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage("Please wait...");

        mNameRegisterView = view.findViewById(R.id.register_name);
        mEmailRegisterView = view.findViewById(R.id.register_email);
        mPasswordRegisterView = view.findViewById(R.id.register_password);
        Button mEmailSignUpButton = view.findViewById(R.id.signup_in_button);
        mEmailSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });

        return view;
    }

    private void attemptRegister() {

        // Reset errors.
        mEmailRegisterView.setError(null);
        mNameRegisterView.setError(null);
        mPasswordRegisterView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailRegisterView.getText().toString();
        String name = mNameRegisterView.getText().toString();
        String password = mPasswordRegisterView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordRegisterView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordRegisterView;
            cancel = true;
        }
        if (TextUtils.isEmpty(password) ) {
            mPasswordRegisterView.setError(getString(R.string.error_field_required));
            focusView = mPasswordRegisterView;
            cancel = true;
        }
        // Check for a valid name and email address.
        if (TextUtils.isEmpty(name) ) {
            mNameRegisterView.setError(getString(R.string.error_field_required));
            focusView = mNameRegisterView;
            cancel = true;
        }else if(!isNameValid(name)){
            mNameRegisterView.setError(getString(R.string.error_invalid_name));
            focusView = mNameRegisterView;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            mEmailRegisterView.setError(getString(R.string.error_field_required));
            focusView = mPasswordRegisterView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailRegisterView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailRegisterView;
            cancel = true;
        }else if (alreadyRegisteredEmail(email)){
            mEmailRegisterView.setError(getString(R.string.error_email_already_registered));
            focusView = mPasswordRegisterView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            Log.d(TAG, "attemptRegister: Success");
            //TODO: save account on DB
            User user = new User(name, email, password);
          createFirebaseUser(user);

        }
    }

    private boolean alreadyRegisteredEmail(String email) {
        DataSource dataSource = new DataSource(mContext);
        if (dataSource.isUserExist(email)){
           return true;
        }else{
            return false;
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }
    private boolean isNameValid(String name) {
        return name.length() >= 3;
    }

    public void setOnRegisterSuccessfullyListener(OnRegisterSuccessfullyListener listener) {
        mListener = listener;
    }

    public interface OnRegisterSuccessfullyListener{
        void onRegisterSuccess();
    }



    // TODO: Create a Firebase user
    private void createFirebaseUser(final User user) {
        final String email = user.getUserMail();
        final String password = user.getUserPass();
        mProgressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "Create user Complete: "+task.isSuccessful());
                            Toast.makeText(mContext, "User Register Successfully!",Toast.LENGTH_SHORT).show();
                            user.setUserAuth(mAuth.getUid());
                            saveLocalDB(user);

                            if (mListener != null) {
                                mListener.onRegisterSuccess();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                String message = "Registration Failed.";
                                showErrorDialog(message);
                            }


                        }else{
                            if (NetworkHelper.hasNetwork(mContext)) {
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                String message = "Registration Failed. User already exist.";
                                showErrorDialog(message);
                            }else {
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                String message = "Registration Failed.Please check the network.";
                                showErrorDialog(message);
                            }
                        }
                        mProgressDialog.dismiss();
                        // ...
                    }


                });
    }
    private void saveLocalDB(User user) {
           DataSource dataSource = new DataSource(mContext);
            dataSource.insertUser(user);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("User").push();
        database.setValue(user);
    }

    private void showErrorDialog(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
    }


}
