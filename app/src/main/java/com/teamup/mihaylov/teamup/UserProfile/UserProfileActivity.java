package com.teamup.mihaylov.teamup.UserProfile;

import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.teamup.mihaylov.teamup.DrawerNavMain.DrawerNavMainActivity;
import com.teamup.mihaylov.teamup.R;
import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;

import javax.inject.Inject;

public class UserProfileActivity extends DrawerNavMainActivity {

    @Inject
    UserProfileContracts.Presenter mPresenter;

    @Inject
    AuthenticationProvider mAuthProvider;

    private GoogleProfileView mGoogleProfileView;
    private UserBasicProfileView mUserBasicProfileView;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mGoogleApiClient = mAuthProvider.getGoogleApi();
        mPresenter.setAuth(mAuthProvider);

        for (UserInfo user : FirebaseAuth.getInstance().getCurrentUser().getProviderData()) {
            if (user.getProviderId().equals("google.com")) {
                mGoogleProfileView = GoogleProfileView.newInstance();
                mGoogleProfileView.setPresenter(mPresenter);

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_container, mGoogleProfileView)
                        .commit();
            } else {
                mUserBasicProfileView = UserBasicProfileView.newInstance();
                mUserBasicProfileView.setPresenter(mPresenter);

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_container, mUserBasicProfileView)
                        .commit();
            }
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }
}
