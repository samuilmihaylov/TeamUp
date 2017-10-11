package com.teamup.mihaylov.teamup.SignIn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.SignInButton;
import com.teamup.mihaylov.teamup.DrawerNavMain.DrawerNavMainActivity;
import com.teamup.mihaylov.teamup.R;
import com.teamup.mihaylov.teamup.SignUp.SignUpActivity;
import com.teamup.mihaylov.teamup.base.utils.Validator;
import com.teamup.mihaylov.teamup.base.utils.ui.LoadingIndicator;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInView extends Fragment implements SignInContracts.View, LoadingIndicator.LoadingView {

    private SignInContracts.Presenter mPresenter;

    private Button mBtnSignIn;
    private Button mBtnSignUp;
    private Button mBtnResetPassword;

    private EditText mInputEmail;
    private EditText mInputPassword;

    private SignInButton mBtnSignInGoogle;

    private Button.OnClickListener mBtnSignInListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            String email = mInputEmail.getText().toString().trim();
            String password = mInputPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                mInputEmail.setError("Enter email address!");
            } else if (!Validator.isValidEmail(email)) {
                mInputEmail.setError("Enter valid email address!");
            } else if (TextUtils.isEmpty(password)) {
                mInputPassword.setError("Enter password!");
            } else if (password.length() < 6) {
                mInputPassword.setError("Password too short, enter minimum 6 characters!");
            } else {
                showLoading();

                mPresenter.signInWithEmail(email, password)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean isSuccessful) throws Exception {
                                if (isSuccessful) {
                                    Intent intent = new Intent(getActivity(), DrawerNavMainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent(getActivity(), SignInActivity.class);
                                    startActivity(intent);
                                    hideLoading();
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

    private Button.OnClickListener mBtnSignUpListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), SignUpActivity.class);
            startActivity(intent);
        }
    };

    private Button.OnClickListener mBtnResetPasswordListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private Button.OnClickListener mBtnSignInGoogleListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            showLoading();
            mPresenter.signInWithGoogle();
        }
    };

    public SignInView() {
        // Required empty public constructor
    }

    public static SignInView newInstance() {
        return new SignInView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin, container, false);

        mInputEmail = (EditText) view.findViewById(R.id.input_email);
        mInputPassword = (EditText) view.findViewById(R.id.input_password);

        mBtnSignIn = (Button) view.findViewById(R.id.btn_sign_in);
        mBtnSignIn.setOnClickListener(mBtnSignInListener);

        mBtnSignInGoogle = view.findViewById(R.id.google_sign_in_button);
        customizeSignInBtn(mBtnSignInGoogle, "Sign in with Google");

        mBtnSignUp = (Button) view.findViewById(R.id.btn_sign_up);

        mBtnResetPassword = (Button) view.findViewById(R.id.btn_reset_password);

        mBtnSignInGoogle.setOnClickListener(mBtnSignInGoogleListener);
        mBtnSignUp.setOnClickListener(mBtnSignUpListener);
        mBtnResetPassword.setOnClickListener(mBtnResetPasswordListener);

        return view;
    }

    protected void customizeSignInBtn(SignInButton signInButton, String buttonText) {
        SpannableStringBuilder sBuilder = new SpannableStringBuilder();
        sBuilder.append(buttonText);
        CalligraphyTypefaceSpan typefaceSpan =
                new CalligraphyTypefaceSpan(TypefaceUtils.load(getContext().getAssets(), "fonts/Ubuntu-Medium.ttf"));
        sBuilder.setSpan(typefaceSpan, 0, buttonText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(sBuilder, TextView.BufferType.SPANNABLE);
                tv.setTextSize(16);
                return;
            }
        }
    }

    @Override
    public void setPresenter(SignInContracts.Presenter presenter) {
        mPresenter = presenter;
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
    public View getContentContainer() {
        return getView().findViewById(R.id.content_container);
    }

    @Override
    public ViewGroup getLoadingContainer() {
        return getView().findViewById(R.id.loading_container);
    }

    @Override
    public void showLoading() {
        LoadingIndicator.showLoadingIndicator(SignInView.this);
    }

    @Override
    public void hideLoading() {
        LoadingIndicator.hideLoadingIndicator(SignInView.this);
    }

    @Override
    public void navigate() {
        Intent intent = new Intent(getContext(), DrawerNavMainActivity.class);
        startActivity(intent);
    }
}
