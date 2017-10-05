package com.teamup.mihaylov.teamup.UserProfile;

import android.widget.Toast;

import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;

import javax.inject.Inject;

/**
 * Created by samui on 5.10.2017 г..
 */

public class UserProfilePresenter implements UserProfileContracts.Presenter {

    private AuthenticationProvider mAuthProvider;
    private UserProfileContracts.View mView;

    @Inject
    UserProfilePresenter(){
    }

    @Override
    public void setAuth(AuthenticationProvider authProvider) {
        mAuthProvider = authProvider;
    }

    @Override
    public void changeEmail(String newEmail) {
        mAuthProvider.changeEmail(newEmail);
    }

    @Override
    public void changePassword(String newPassword) {
        mAuthProvider.changePassword(newPassword);
    }

    @Override
    public void sendPasswordResetEmail(String email) {
        mAuthProvider.sendPasswordResetEmail(email);
    }

    @Override
    public void signOut() {
        mAuthProvider.signOut();
    }

    @Override
    public void googleSignOut(){
        mAuthProvider.googleSignOut();
    }

    @Override
    public void subscribe(UserProfileContracts.View view) {
        mView = view;
    }

    @Override
    public void unsubscribe() {
        mView = null;
    }
}
