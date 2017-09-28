package com.teamup.mihaylov.teamup.SignUp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.teamup.mihaylov.teamup.DrawerNavMain.DrawerNavMainActivity;
import com.teamup.mihaylov.teamup.Home.HomeFragment;
import com.teamup.mihaylov.teamup.R;

public class SignUpActivity extends DrawerNavMainActivity {

    private FirebaseAuth mAuth;
    private SignUpFragment mSignUpFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSignUpFragment = new SignUpFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_container, mSignUpFragment)
                .commit();

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            Fragment homeFragment = new HomeFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_container, homeFragment)
                    .commit();
        }

        setupDrawer();
    }

    public void emailSignUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(), DrawerNavMainActivity.class);
                            startActivity(intent);
                        }
                        else if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Authentication failed." + task.getException(), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
    }
}
