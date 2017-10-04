package com.teamup.mihaylov.teamup.SignIn;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.teamup.mihaylov.teamup.DrawerNavMain.DrawerNavMainActivity;
import com.teamup.mihaylov.teamup.R;
import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;

import javax.inject.Inject;

public class SignInActivity extends DrawerNavMainActivity
        implements GoogleApiClient.OnConnectionFailedListener {

    @Inject
    public SignInContracts.Presenter mPresenter;

    @Inject
    AuthenticationProvider mAuthProvider;

    private SignInFragment mView;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mView = SignInFragment.newInstance();
        mView.setPresenter(mPresenter);

        mAuthProvider.setActivity(this);
        mPresenter.setAuth(mAuthProvider);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_container, mView)
                .commit();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("695242278170-tn551iph1cuf7jpvbpl2tl4c51b1ufaq.apps.googleusercontent.com")
                .requestEmail()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    public void signInWithGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuthProvider.getFirebaseInstance().signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(getApplicationContext(), DrawerNavMainActivity.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(getApplicationContext(), DrawerNavMainActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }

    public void signOutWithGoogle() {
        for (UserInfo user : FirebaseAuth.getInstance().getCurrentUser().getProviderData()) {
            if (mAuthProvider.getUser().getProviderId().equals("google.com")) {
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "onConnectionFailed" + connectionResult, Toast.LENGTH_SHORT).show();
    }
}
