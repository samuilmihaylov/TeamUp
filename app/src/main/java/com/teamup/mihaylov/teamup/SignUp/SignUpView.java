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

import com.teamup.mihaylov.teamup.DrawerNavMain.DrawerNavMainActivity;
import com.teamup.mihaylov.teamup.R;
import com.teamup.mihaylov.teamup.SignIn.SignInActivity;
import com.teamup.mihaylov.teamup.UserProfile.UserProfileActivity;
import com.teamup.mihaylov.teamup.base.utils.Constants;
import com.teamup.mihaylov.teamup.base.utils.Validator;
import com.teamup.mihaylov.teamup.base.utils.ui.LoadingIndicator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SignUpView extends Fragment implements SignUpContracts.View, LoadingIndicator.LoadingView {

    private Button mBtnSignIn;
    private Button mBtnSignUp;
    private Button mBtnResetPassword;
    private EditText mInputEmail;
    private EditText mInputPassword;
    private EditText mInputFirstName;
    private EditText mInputLastName;
    private ProgressBar mProgressBar;

    private SignUpContracts.Presenter mPresenter;

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
            String firstName = mInputFirstName.getText().toString().trim();
            String lastName = mInputLastName.getText().toString().trim();
            String displayName = firstName + " " + lastName;

            if (TextUtils.isEmpty(firstName)) {
                mInputFirstName.setError("First name is required!");
            } else if (TextUtils.isEmpty(lastName)) {
                mInputLastName.setError("Last name is required!");
            } else if (TextUtils.isEmpty(email)) {
                mInputEmail.setError("Enter email address!");
            } else if (!Validator.isValidEmail(email)) {
                mInputEmail.setError("Enter valid email address!");
            } else if (TextUtils.isEmpty(password)) {
                mInputPassword.setError("Enter password!");
            } else if (password.length() < 6) {
                mInputPassword.setError("Password too short, enter minimum 6 characters!");
            } else {

                showLoading();

                mPresenter
                        .signUpWithEmail(email, password, displayName)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean isSuccessful) throws Exception {
                                if (isSuccessful) {
                                    Intent intent = new Intent(getActivity(), DrawerNavMainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent(getActivity(), SignUpActivity.class);
                                    startActivity(intent);
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

    };

    private Button.OnClickListener mBtnResetPasswordListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    public SignUpView() {
        // Required empty public constructor
    }


    public static SignUpView newInstance() {
        return new SignUpView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        mInputEmail = (EditText) view.findViewById(R.id.input_email);
        mInputPassword = (EditText) view.findViewById(R.id.input_password);
        mInputFirstName = (EditText) view.findViewById(R.id.input_firstname);
        mInputLastName = (EditText) view.findViewById(R.id.input_lastname);

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
        mPresenter.subscribe(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPresenter != null) {
            mPresenter.unsubscribe();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.unsubscribe();
        }

        mPresenter = null;
    }

    @Override
    public void setPresenter(SignUpContracts.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public View getContentContainer() {
        return getView().findViewById(R.id.content_container);
    }

    @Override
    public ViewGroup getLoadingContainer() {
        return getView().findViewById(R.id.loading_container);
    }

    @Override
    public void showLoading() {
        LoadingIndicator.showLoadingIndicator(SignUpView.this);
    }

    @Override
    public void hideLoading() {
        LoadingIndicator.hideLoadingIndicator(SignUpView.this);
    }
}
