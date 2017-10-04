package com.teamup.mihaylov.teamup.SignIn;

import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;
import com.teamup.mihaylov.teamup.base.contracts.BaseContracts;

/**
 * Created by samui on 3.10.2017 г..
 */

public interface SignInContracts {

    interface View extends BaseContracts.View<Presenter> {

    }

    interface Presenter extends BaseContracts.Presenter<View> {

        void signInWithEmail(String email, String password);

        void setAuth(AuthenticationProvider mAuthProvider);
    }
}
