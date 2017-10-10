package com.teamup.mihaylov.teamup.base.authentication;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
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
import com.teamup.mihaylov.teamup.base.data.RemoteUsersData;
import com.teamup.mihaylov.teamup.base.models.User;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by samui on 3.10.2017 г..
 */

public class AuthenticationProvider implements FirebaseAuth.AuthStateListener, GoogleApiClient.OnConnectionFailedListener {

    private final RemoteUsersData<User> mUsersData;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;

    private Context mContext;
    private Activity mActivity;

    @Inject
    AuthenticationProvider(Context context, RemoteUsersData<User> usersData) {
        mContext = context;
        mUsersData = usersData;

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("695242278170-tn551iph1cuf7jpvbpl2tl4c51b1ufaq.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAuth.addAuthStateListener(this);
    }

    public Observable<Boolean> isAuthenticated() {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(final ObservableEmitter<Boolean> emitter) throws Exception {
                mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        if (firebaseAuth.getCurrentUser() != null) {
                            mUser = firebaseAuth.getCurrentUser();
                            emitter.onNext(true);
                        } else {
                            emitter.onNext(false);
                        }
                        emitter.onComplete();
                    }
                });
            }
        });
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

    public Observable<Boolean> updateUserProfile(final String displayName) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(final ObservableEmitter<Boolean> emitter) throws Exception {
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest
                        .Builder()
                        .setDisplayName(displayName)
                        .build();

                String uid = mUser.getUid();
                mUsersData.setKey(uid);
                mUsersData.add(new User(uid, displayName));

                mUser.updateProfile(profileUpdates).addOnCompleteListener(mActivity, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    emitter.onNext(true);
                                    emitter.onComplete();
                                }
                            }
                        });
            }
        });
    }

    public Observable<Boolean> signUpWithEmail(final String email, final String password, final String displayName) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(final ObservableEmitter<Boolean> emitter) throws Exception {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(mContext, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                                    Observable<Boolean> observable = updateUserProfile(displayName);

                                    observable
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Consumer<Boolean>() {
                                                @Override
                                                public void accept(Boolean isSuccessful) throws Exception {
                                                    if (isSuccessful) {
                                                        emitter.onNext(true);
                                                        emitter.onComplete();
                                                    }
                                                }
                                            }, new Consumer<Throwable>() {
                                                @Override
                                                public void accept(Throwable throwable) throws Exception {
                                                    throwable.printStackTrace();
                                                    emitter.onNext(false);
                                                    emitter.onComplete();
                                                }
                                            });
                                } else if (!task.isSuccessful()) {
                                    Toast.makeText(mContext, "Authentication failed." + task.getException(), Toast.LENGTH_LONG).show();
                                    emitter.onNext(false);
                                }
                            }
                        });
            }
        });
    }

    public Observable<Boolean> signInWithEmail(final String email, final String password) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(final ObservableEmitter<Boolean> emitter) throws Exception {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(mContext, "signInWithEmail:success", Toast.LENGTH_SHORT).show();
                                    emitter.onNext(true);
                                } else {
                                    Toast.makeText(mContext, "signInWithEmail:failure", Toast.LENGTH_SHORT).show();
                                    emitter.onNext(false);
                                }
                            }
                        });
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

    public GoogleApiClient getGoogleApi() {
        return mGoogleApiClient;
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

    public void googleSignOut() {
        signOut();
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient);
        Toast.makeText(mContext, "Signed Out", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(mContext, "onConnectionFailed" + connectionResult, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        mUser = firebaseAuth.getCurrentUser();
    }
}
