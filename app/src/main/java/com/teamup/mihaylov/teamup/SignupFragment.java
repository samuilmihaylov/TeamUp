package com.teamup.mihaylov.teamup;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupFragment extends Fragment {

    private Button mBtnSignIn;
    private Button mBtnSignUp;
    private Button mBtnResetPassword;
    private EditText mInputEmail;
    private EditText mInputPassword;
    private ProgressBar mProgressBar;
    private FirebaseAuth mAuth;

    private Button.OnClickListener mBtnSignInListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            Fragment signinFragment = new SigninFragment();
//            getActivity().getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.content_container, signinFragment)
//                .commit();
        }
    };

    private Button.OnClickListener mBtnSignUpListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            String email = mInputEmail.getText().toString().trim();
            String password = mInputPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getActivity().getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(getActivity().getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.length() < 6) {
                Toast.makeText(getActivity().getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                return;
            }

            mProgressBar.setVisibility(View.VISIBLE);

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "createUserWithEmail:onComplete:" + task.isSuccessful(),
                                    Toast.LENGTH_SHORT).show();

                            mProgressBar.setVisibility(View.GONE);

                            if (!task.isSuccessful()) {
                                Toast.makeText(getActivity().getApplicationContext(), "Authentication failed." + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                            } else {
//                                ((MainActivity)getActivity()).updateUI();

                                Fragment homeFragment = new HomeFragment();

//                                getActivity().getSupportFragmentManager()
//                                        .beginTransaction()
//                                        .replace(R.id.content_container, homeFragment)
//                                        .commit();
                            }
                        }
                    });
        }
    };

    private Button.OnClickListener mBtnResetPasswordListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    public SignupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            Fragment homeFragment = new HomeFragment();

//            getActivity().getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.content_container, homeFragment)
//                    .commit();
        }

        mInputEmail = (EditText) view.findViewById(R.id.input_email);
        mInputPassword = (EditText) view.findViewById(R.id.input_password);

        mBtnSignIn = (Button) view.findViewById(R.id.btn_sign_in);
        mBtnSignUp = (Button) view.findViewById(R.id.btn_sign_up);
        mBtnResetPassword = (Button) view.findViewById(R.id.btn_reset_password);

        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        mBtnSignIn.setOnClickListener(mBtnSignInListener);
        mBtnSignUp.setOnClickListener(mBtnSignUpListener);
        mBtnResetPassword.setOnClickListener(mBtnResetPasswordListener);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mProgressBar.setVisibility(View.GONE);
    }
}
