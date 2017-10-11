package com.teamup.mihaylov.teamup.SignIn;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;
import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;
import com.teamup.mihaylov.teamup.base.data.LocalUsersData;
import com.teamup.mihaylov.teamup.base.utils.schedulers.BaseSchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by samui on 3.10.2017 г..
 */

class SignInPresenter implements SignInContracts.Presenter {

    private final BaseSchedulerProvider mScheduleProvider;
    private SignInContracts.View mView;
    private AuthenticationProvider mAuthenticationProvider;

    @Inject
    public SignInPresenter(AuthenticationProvider authProvider,
                           BaseSchedulerProvider schedulerProvider) {
        mAuthenticationProvider = authProvider;
        mScheduleProvider = schedulerProvider;
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
    public Observable<Boolean> signInWithEmail(String email, String password) {
        return mAuthenticationProvider.signInWithEmail(email, password);
    }

    @Override
    public void signInWithGoogle() {
        mAuthenticationProvider.signInWithGoogle();
    }

    @Override
    public GoogleApiClient getGoogleApi() {
        return mAuthenticationProvider.getGoogleApi();
    }

    @Override
    public void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        Observable<Boolean> observable = mAuthenticationProvider
                .firebaseAuthWithGoogle(account);

        observable
                .subscribeOn(mScheduleProvider.io())
                .observeOn(mScheduleProvider.ui())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isSuccessful) throws Exception {
                        if(isSuccessful){
                            mView.navigate();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }
}
