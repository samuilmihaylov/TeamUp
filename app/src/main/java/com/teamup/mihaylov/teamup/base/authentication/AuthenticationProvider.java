package com.teamup.mihaylov.teamup.base.authentication;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import javax.inject.Inject;

/**
 * Created by samui on 3.10.2017 г..
 */

public class AuthenticationProvider {

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Context mContext;
    private Activity mActivity;

    @Inject
    AuthenticationProvider(Context context) {
        mContext = context;

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mUser = firebaseAuth.getCurrentUser();
            }
        };

        mAuth.addAuthStateListener(mAuthListener);
    }

    public FirebaseAuth getFirebaseInstance() {
        return mAuth;
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
    }

    public FirebaseUser getUser() {
        return mAuth.getCurrentUser();
    }

    public String getUserId() {
        return mAuth.getCurrentUser().getUid();
    }

    public String getUserDisplayName() {
        return mAuth.getCurrentUser().getDisplayName();
    }

    public void updateUserProfile(String displayName) {
        if (mUser != null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName).build();

            mUser.updateProfile(profileUpdates);
        }
    }

    public void signUpWithEmail(String email, String password, final String displayName) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(mContext, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                            updateUserProfile(displayName);
                        } else if (!task.isSuccessful()) {
                            Toast.makeText(mContext, "Authentication failed." + task.getException(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void signInWithEmail(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(mContext, "signInWithEmail:success", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, "signInWithEmail:failure", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void signOut() {
        mAuth.signOut();
        Toast.makeText(mContext, "Signed Out", Toast.LENGTH_SHORT).show();
    }
}
