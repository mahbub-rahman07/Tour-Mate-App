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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mahbub.tourmate.R;
import com.mahbub.tourmate.activities.HomeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    public static final String EMAIL_KEY = "email_key";
    // UI references.
    private TextView mEmailView;
    private EditText mPasswordView;
    private  String password;
    private   String email;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthstateListener;

    private OnSignUpButtonClickListener mSignupListener;
    private OnSignInButtonClickListener mSigninListener;
    private Context mContext;
    private ProgressDialog mProgressDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mSignupListener = (OnSignUpButtonClickListener) context;
        mSigninListener = (OnSignInButtonClickListener) context;
    }

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mAuth = FirebaseAuth.getInstance();
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage("Please wait...");

        mEmailView = view.findViewById(R.id.email);
        mPasswordView = view.findViewById(R.id.password);
        Button mEmailSignInButton = view.findViewById(R.id.sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               attemptLogin();
            }
        });
        Button mRegisterButton = view.findViewById(R.id.register_btn);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSignupListener.onSignupButtonClicked();
            }
        });


        return view;
    }
    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.

        email = mEmailView.getText().toString();

        password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        if (TextUtils.isEmpty(password) ){
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            //TODO: login here
            //mSigninListener.onSigninButtonClicked(email, password);
            // TODO: Use FirebaseAuth to sign in with email & password
            mProgressDialog.show();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        mProgressDialog.dismiss();
                        Log.d("login", "singin: "+task.isSuccessful() );
                        mSigninListener.onSigninButtonClicked(HomeActivity.mUserID = mAuth.getUid());
                    } else {
                        mProgressDialog.dismiss();
                        String message = "invalid email or password";
                        Log.d("login", "singin failed: "+message );
                        showErrorDialog(message);
                    }
                }
            });

        }

    }
    private void showErrorDialog(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    public interface OnSignUpButtonClickListener{
       void onSignupButtonClicked();
    }
    public interface OnSignInButtonClickListener{
        void onSigninButtonClicked(String uid);
    }
}
