package com.teamup.mihaylov.teamup.SignUp;

import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;
import com.teamup.mihaylov.teamup.base.contracts.BaseContracts;

import io.reactivex.Observable;

/**
 * Created by samui on 5.10.2017 г..
 */

public interface SignUpContracts {
    interface View extends BaseContracts.View<Presenter> {

    }

    interface Presenter extends BaseContracts.Presenter<View> {

        Observable<Boolean> signUpWithEmail(String email, String password, String displayName);

        void setAuth(AuthenticationProvider authProvider);
    }
}
