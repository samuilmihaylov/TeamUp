package com.teamup.mihaylov.teamup.SignIn;

import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;
import com.teamup.mihaylov.teamup.base.contracts.BaseContracts;

import io.reactivex.Observable;

/**
 * Created by samui on 3.10.2017 г..
 */

public interface SignInContracts {

    interface View extends BaseContracts.View<Presenter> {

    }

    interface Presenter extends BaseContracts.Presenter<View> {

        void setAuth(AuthenticationProvider mAuthProvider);

        Observable<Boolean> signInWithEmail(String email, String password);
    }
}
