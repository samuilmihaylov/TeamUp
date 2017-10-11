package com.teamup.mihaylov.teamup.SignIn;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;
import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;
import com.teamup.mihaylov.teamup.base.contracts.BaseContracts;

import io.reactivex.Observable;

/**
 * Created by samui on 3.10.2017 г..
 */

public interface SignInContracts {

    interface View extends BaseContracts.View<Presenter> {

        void navigate();
    }

    interface Presenter extends BaseContracts.Presenter<View> {

//        void setAuth(AuthenticationProvider mAuthProvider);

        Observable<Boolean> signInWithEmail(String email, String password);

        void signInWithGoogle();

        void firebaseAuthWithGoogle(GoogleSignInAccount account);

        GoogleApiClient getGoogleApi();
    }
}
