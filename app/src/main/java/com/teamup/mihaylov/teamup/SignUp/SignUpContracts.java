package com.teamup.mihaylov.teamup.SignUp;

import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;
import com.teamup.mihaylov.teamup.base.contracts.BaseContracts;

/**
 * Created by samui on 5.10.2017 г..
 */

public interface SignUpContracts {
    interface View extends BaseContracts.View<Presenter> {

    }

    interface Presenter extends BaseContracts.Presenter<View> {

        void signUpWithEmail(String email, String password, String displayName);

        void setAuth(AuthenticationProvider authProvider);
    }
}
