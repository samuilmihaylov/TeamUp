package com.teamup.mihaylov.teamup.UserProfile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.teamup.mihaylov.teamup.DrawerNavMain.DrawerNavMainActivity;
import com.teamup.mihaylov.teamup.Events.ListEvents.ListCreatedEvents.ListCreatedEventsActivity;
import com.teamup.mihaylov.teamup.Events.ListEvents.ListJoinedEvents.ListJoinedEventsActivity;
import com.teamup.mihaylov.teamup.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserBasicProfileView extends Fragment implements UserProfileContracts.View {

    private Button changeEmailBtnTrigger;
    private Button changePasswordBtnTrigger;
    private Button sendResetEmailBtnTrigger;
    private Button btnChangeEmail;
    private Button btnChangePassword;
    private Button btnSendEmail;
    private Button btnSignOut;

    private EditText currentEmail;
    private EditText newEmail;
    private EditText password;
    private EditText newPassword;
    private ProgressBar progressBar;

    private Button.OnClickListener btnChangeEmailListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            progressBar.setVisibility(View.VISIBLE);
            String email = newEmail.getText().toString();
            if (email.trim().equals("")) {
                newEmail.setError("Enter email!");
            } else {
                mPresenter.changeEmail(email);
            }
        }
    };

    private Button.OnClickListener btnChangePasswordListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            progressBar.setVisibility(View.VISIBLE);
            String password = newPassword.getText().toString();
            if (password.trim().length() < 6) {
                newPassword.setError("Password too short, enter minimum 6 characters");
            } else if (password.trim().equals("")) {
                newPassword.setError("Enter password");
            } else {
                mPresenter.changePassword(password);
            }
        }
    };

    private Button.OnClickListener btnSendEmailListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            progressBar.setVisibility(View.VISIBLE);
            String email = currentEmail.getText().toString();

            if (email.trim().equals("")) {
                currentEmail.setError("Enter email");
            } else {
                mPresenter.sendPasswordResetEmail(email);
            }
        }
    };

    private Button.OnClickListener btnSignOutListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            mPresenter.signOut();
            Intent intent = new Intent(getActivity(), DrawerNavMainActivity.class);
            startActivity(intent);
        }
    };

    private Button.OnClickListener changeEmailBtnTriggerListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            currentEmail.setVisibility(View.GONE);
            btnChangePassword.setVisibility(View.GONE);
            btnSendEmail.setVisibility(View.GONE);
            password.setVisibility(View.GONE);
            newPassword.setVisibility(View.GONE);
            newEmail.setVisibility(View.VISIBLE);
            btnChangeEmail.setVisibility(View.VISIBLE);
        }
    };

    private Button.OnClickListener changePasswordBtnTriggerListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            currentEmail.setVisibility(View.GONE);
            newEmail.setVisibility(View.GONE);
            password.setVisibility(View.GONE);
            btnChangeEmail.setVisibility(View.GONE);
            btnSendEmail.setVisibility(View.GONE);
            newPassword.setVisibility(View.VISIBLE);
            btnChangePassword.setVisibility(View.VISIBLE);
        }
    };

    private Button.OnClickListener sendResetEmailBtnTriggerListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            currentEmail.setVisibility(View.VISIBLE);
            newEmail.setVisibility(View.GONE);
            password.setVisibility(View.GONE);
            newPassword.setVisibility(View.GONE);
            btnChangeEmail.setVisibility(View.GONE);
            btnChangePassword.setVisibility(View.GONE);
            btnSendEmail.setVisibility(View.VISIBLE);
        }
    };

    private Spinner.OnItemSelectedListener spinnerSelectListener = new Spinner.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
            if (pos == 1) {
                Intent intent = new Intent(getContext(), ListJoinedEventsActivity.class);
                startActivity(intent);
            } else if (pos == 2) {
                Intent intent = new Intent(getContext(), ListCreatedEventsActivity.class);
                startActivity(intent);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    private UserProfileContracts.Presenter mPresenter;

    public UserBasicProfileView() {
        // Required empty public constructor
    }

    public static UserBasicProfileView newInstance() {
        return new UserBasicProfileView();
    }

    @Override
    public void setPresenter(UserProfileContracts.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_basic_profile, container, false);

        Spinner spinner = (Spinner) view.findViewById(R.id.my_collections);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.my_collections_dropdown, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(spinnerSelectListener);

        changeEmailBtnTrigger = (Button) view.findViewById(R.id.change_email_button);
        changeEmailBtnTrigger.setOnClickListener(changeEmailBtnTriggerListener);

        changePasswordBtnTrigger = (Button) view.findViewById(R.id.change_password_button);
        changePasswordBtnTrigger.setOnClickListener(changePasswordBtnTriggerListener);

        sendResetEmailBtnTrigger = (Button) view.findViewById(R.id.sending_pass_reset_button);
        sendResetEmailBtnTrigger.setOnClickListener(sendResetEmailBtnTriggerListener);

        btnChangeEmail = (Button) view.findViewById(R.id.changeEmail);
        btnChangeEmail.setOnClickListener(btnChangeEmailListener);

        btnChangePassword = (Button) view.findViewById(R.id.changePass);
        btnChangePassword.setOnClickListener(btnChangePasswordListener);

        btnSendEmail = (Button) view.findViewById(R.id.send);
        btnSendEmail.setOnClickListener(btnSendEmailListener);

        btnSignOut = (Button) view.findViewById(R.id.sign_out);
        btnSignOut.setOnClickListener(btnSignOutListener);

        currentEmail = (EditText) view.findViewById(R.id.reset_email);
        newEmail = (EditText) view.findViewById(R.id.new_email);
        password = (EditText) view.findViewById(R.id.password);
        newPassword = (EditText) view.findViewById(R.id.new_password);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        setupUI();

        return view;
    }

    public void setupUI() {
        currentEmail.setVisibility(View.GONE);
        newEmail.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
        newPassword.setVisibility(View.GONE);
        btnChangeEmail.setVisibility(View.GONE);
        btnChangePassword.setVisibility(View.GONE);
        btnSendEmail.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.unsubscribe();
        }
    }
}
