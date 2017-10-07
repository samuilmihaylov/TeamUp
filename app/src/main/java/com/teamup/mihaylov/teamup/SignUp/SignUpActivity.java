package com.teamup.mihaylov.teamup.SignUp;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.teamup.mihaylov.teamup.DrawerNavMain.DrawerNavMainActivity;
import com.teamup.mihaylov.teamup.R;
import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;

import javax.inject.Inject;

public class SignUpActivity extends DrawerNavMainActivity {

    private SignUpView mView;

    @Inject
    public SignUpContracts.Presenter mPresenter;

    @Inject
    AuthenticationProvider mAuthProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mView = SignUpView.newInstance();

        mView.setPresenter(mPresenter);

        mAuthProvider.setActivity(this);
        mPresenter.setAuth(mAuthProvider);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_container, mView)
                .commit();
    }
}
