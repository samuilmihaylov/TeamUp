package com.teamup.mihaylov.teamup.SignUp;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.teamup.mihaylov.teamup.DrawerNavMain.DrawerNavMainActivity;
import com.teamup.mihaylov.teamup.R;
import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;

import javax.inject.Inject;

public class SignUpActivity extends DrawerNavMainActivity {

    private FirebaseAuth mAuth;
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

//        mAuth = FirebaseAuth.getInstance();
//
//        if (mAuth.getCurrentUser() != null) {
//            Fragment homeFragment = new HomeFragment();
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.content_container, homeFragment)
//                    .commit();
//        }
    }

//    public void emailSignUp(String email, String password, final String displayName) {
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(getApplicationContext(), "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
//
//                            updateUserProfile(displayName);
//                        }
//                        else if (!task.isSuccessful()) {
//                            Toast.makeText(getApplicationContext(), "Authentication failed." + task.getException(), Toast.LENGTH_LONG).show();
//                            finish();
//                        }
//                    }
//                });
//    }
//
//    private void updateUserProfile(String displayName) {
//        FirebaseUser user = mAuth.getCurrentUser();
//
//        if(user != null){
//            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                    .setDisplayName(displayName).build();
//
//            user.updateProfile(profileUpdates)
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()){
//                                Intent intent = new Intent(getApplicationContext(), DrawerNavMainActivity.class);
//                                startActivity(intent);
//                            }
//                        }
//                    });
//        }
//    }
}
