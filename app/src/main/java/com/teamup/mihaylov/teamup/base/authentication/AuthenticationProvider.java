package com.teamup.mihaylov.teamup.base.authentication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.teamup.mihaylov.teamup.base.contracts.CurrentActivityProvider;
import com.teamup.mihaylov.teamup.base.data.LocalUsersData;
import com.teamup.mihaylov.teamup.base.data.RemoteUsersData;
import com.teamup.mihaylov.teamup.base.models.DaoUser;
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

    private static final int RC_SIGN_IN = 9001;
    private final RemoteUsersData<User> mRemoteUsersData;
    private final CurrentActivityProvider mCurrentActivityProvider;
    private final LocalUsersData mLocalUsersData;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;

    private Context mContext;
    private Activity mActivity;
    private GoogleApiClient mGoogleApiClient;

    @Inject
    AuthenticationProvider(
            Context context,
            RemoteUsersData<User> usersData,
            LocalUsersData localUsersData,
            CurrentActivityProvider currentActivityProvider) {

        mContext = context;
        mRemoteUsersData = usersData;
        mLocalUsersData = localUsersData;
        mCurrentActivityProvider = currentActivityProvider;

        mAuth = FirebaseAuth.getInstance();

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

    public Observable<Boolean> updateUserProfile(final String displayName) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(final ObservableEmitter<Boolean> emitter) throws Exception {
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest
                        .Builder()
                        .setDisplayName(displayName)
                        .build();

                String uid = mUser.getUid();
                mRemoteUsersData.setKey(uid);
                mRemoteUsersData.add(new User(uid, displayName));

                mUser.updateProfile(profileUpdates).addOnCompleteListener(mCurrentActivityProvider.getCurrentActivity(), new OnCompleteListener<Void>() {
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
                        .addOnCompleteListener(mCurrentActivityProvider.getCurrentActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = task.getResult().getUser();
                                    Toast.makeText(mContext, "signInWithEmail:success", Toast.LENGTH_SHORT).show();
                                    emitter.onNext(true);
                                } else {
                                    Toast.makeText(mContext, "signInWithEmail:failure", Toast.LENGTH_SHORT).show();
                                    emitter.onNext(false);
                                }

                                emitter.onComplete();
                            }
                        });
            }
        });
    }

    public void signInWithGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        mCurrentActivityProvider.getCurrentActivity().startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public Observable<Boolean> firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(final ObservableEmitter<Boolean> emitter) throws Exception {
                final AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(mCurrentActivityProvider.getCurrentActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = task.getResult().getUser();
                                    Toast.makeText(mContext, "signInWithGoogle:success", Toast.LENGTH_SHORT).show();
                                    emitter.onNext(true);
                                } else {
                                    Toast.makeText(mContext, "signInWithGoogle:failure", Toast.LENGTH_SHORT).show();
                                    emitter.onNext(false);
                                }

                                emitter.onComplete();
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

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("695242278170-tn551iph1cuf7jpvbpl2tl4c51b1ufaq.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

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
        mLocalUsersData.clear();
        Toast.makeText(mContext, "Signed Out", Toast.LENGTH_SHORT).show();
    }

    public void googleSignOut() {
        signOut();
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient);
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
