package com.teamup.mihaylov.teamup.SignIn;

import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by samui on 3.10.2017 г..
 */

class SignInPresenter implements SignInContracts.Presenter {

    private SignInContracts.View mView;
    private AuthenticationProvider mAuthenticationProvider;

    @Inject
    public SignInPresenter() {
    }

    @Override
    public void subscribe(SignInContracts.View view) {
        mView = view;
    }

    @Override
    public void unsubscribe() {
        mView = null;
    }

    @Override
    public void setAuth(AuthenticationProvider authProvider) {
        mAuthenticationProvider = authProvider;
    }

    @Override
    public Observable<Boolean> signInWithEmail(String email, String password) {
        return mAuthenticationProvider.signInWithEmail(email, password);
    }
}
