package com.teamup.mihaylov.teamup.SignUp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.teamup.mihaylov.teamup.R;
import com.teamup.mihaylov.teamup.SignIn.SignInActivity;

public class SignUpFragment extends Fragment {

    private Button mBtnSignIn;
    private Button mBtnSignUp;
    private Button mBtnResetPassword;
    private EditText mInputEmail;
    private EditText mInputPassword;
    private ProgressBar mProgressBar;

    private Button.OnClickListener mBtnSignInListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), SignInActivity.class);
            startActivity(intent);
        }
    };

    private Button.OnClickListener mBtnSignUpListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            String email = mInputEmail.getText().toString().trim();
            String password = mInputPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                mInputEmail.setError("Enter email address!");
            }
            else if (TextUtils.isEmpty(password)) {
                mInputPassword.setError("Enter password!");
            }
            else if (password.length() < 6) {
                mInputPassword.setError("Password too short, enter minimum 6 characters!");
            }
            else {
                mProgressBar.setVisibility(View.VISIBLE);
                ((SignUpActivity)getActivity()).emailSignUp(email, password);
            }
        }
    };

    private Button.OnClickListener mBtnResetPasswordListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

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
