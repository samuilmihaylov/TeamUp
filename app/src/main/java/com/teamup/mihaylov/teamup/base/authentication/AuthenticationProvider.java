package com.teamup.mihaylov.teamup.base.authentication;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
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

public class AuthenticationProvider implements FirebaseAuth.AuthStateListener, GoogleApiClient.OnConnectionFailedListener{

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;

    private Context mContext;
    private Activity mActivity;
    private GoogleApiClient mGoogleApiClient;

    @Inject
    AuthenticationProvider(Context context) {
        mContext = context;

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("695242278170-tn551iph1cuf7jpvbpl2tl4c51b1ufaq.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAuth = FirebaseAuth.getInstance();

        mAuth.addAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        mUser = firebaseAuth.getCurrentUser();
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

    public GoogleApiClient getGoogleApi(){
        return mGoogleApiClient;
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

    public void changeEmail(String newEmail) {
        mUser.updateEmail(newEmail.trim())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(mContext, "Email address is updated. Please sign in with the new email address!", Toast.LENGTH_LONG).show();
                            signOut();
                        } else {
                            Toast.makeText(mContext, "Failed to update email!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void changePassword(String newPassword) {
        mUser.updatePassword(newPassword.trim())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(mContext, "Password is updated, sign in with new password!", Toast.LENGTH_SHORT).show();
                            signOut();
                        } else {
                            Toast.makeText(mContext, "Failed to update password!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void sendPasswordResetEmail(String email) {
        mAuth.sendPasswordResetEmail(email.trim())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(mContext, "Reset password email is sent!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void signOut() {
        mAuth.signOut();
        Toast.makeText(mContext, "Signed Out", Toast.LENGTH_SHORT).show();
    }

    public void googleSignOut(){
        signOut();
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient);
        Toast.makeText(mContext, "Signed Out", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(mContext, "onConnectionFailed" + connectionResult, Toast.LENGTH_SHORT).show();
    }
}
