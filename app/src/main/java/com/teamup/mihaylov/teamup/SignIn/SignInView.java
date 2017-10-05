package com.teamup.mihaylov.teamup.SignIn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.teamup.mihaylov.teamup.DrawerNavMain.DrawerNavMainActivity;
import com.teamup.mihaylov.teamup.R;
import com.teamup.mihaylov.teamup.SignUp.SignUpActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInView extends Fragment implements SignInContracts.View {

    private SignInContracts.Presenter mPresenter;

    private Button mBtnSignIn;
    private Button mBtnSignUp;
    private Button mBtnResetPassword;

    private EditText mInputEmail;
    private EditText mInputPassword;

    private ProgressBar mProgressBar;
    private View mBtnSignInGoogle;

    private Button.OnClickListener mBtnSignInListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            String email = mInputEmail.getText().toString().trim();
            String password = mInputPassword.getText().toString().trim();

            mProgressBar.setVisibility(View.VISIBLE);

            mPresenter.signInWithEmail(email, password);

            Intent intent = new Intent(getActivity(), DrawerNavMainActivity.class);
            startActivity(intent);
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
            mProgressBar.setVisibility(View.VISIBLE);
            ((SignInActivity)getActivity()).signInWithGoogle();
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
        View view =  inflater.inflate(R.layout.fragment_signin, container, false);

        mInputEmail = (EditText) view.findViewById(R.id.input_email);
        mInputPassword = (EditText) view.findViewById(R.id.input_password);

        mBtnSignIn = (Button) view.findViewById(R.id.btn_sign_in);
        mBtnSignIn.setOnClickListener(mBtnSignInListener);

        mBtnSignInGoogle = view.findViewById(R.id.sign_in_button);
        mBtnSignUp = (Button) view.findViewById(R.id.btn_sign_up);

        mBtnResetPassword = (Button) view.findViewById(R.id.btn_reset_password);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        mBtnSignInGoogle.setOnClickListener(mBtnSignInGoogleListener);
        mBtnSignUp.setOnClickListener(mBtnSignUpListener);
        mBtnResetPassword.setOnClickListener(mBtnResetPasswordListener);

        return view;
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
        mPresenter.unsubscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
        mPresenter = null;
    }
}
