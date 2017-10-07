package com.teamup.mihaylov.teamup.SignUp;

import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;
import com.teamup.mihaylov.teamup.base.data.RemoteUsersData;
import com.teamup.mihaylov.teamup.base.models.User;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by samui on 5.10.2017 г..
 */

public class SignUpPresenter implements SignUpContracts.Presenter {

    private SignUpContracts.View mView;
    private AuthenticationProvider mAuthenticationProvider;

    @Inject
    public SignUpPresenter() {
    }

    @Override
    public Observable<Boolean> signUpWithEmail(String email, String password, String displayName) {
       return mAuthenticationProvider.signUpWithEmail(email, password, displayName);
    }

    @Override
    public void setAuth(AuthenticationProvider authProvider) {
        mAuthenticationProvider = authProvider;
    }

    @Override
    public void subscribe(SignUpContracts.View view) {
        mView = view;
    }

    @Override
    public void unsubscribe() {
        mView = null;
    }
}
