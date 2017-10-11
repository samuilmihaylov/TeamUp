package com.teamup.mihaylov.teamup.SingUp;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.teamup.mihaylov.teamup.SignUp.SignUpContracts;
import com.teamup.mihaylov.teamup.SignUp.SignUpPresenter;
import com.teamup.mihaylov.teamup.UserProfile.UserProfileContracts;
import com.teamup.mihaylov.teamup.UserProfile.UserProfilePresenter;
import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

/**
 * Created by samui on 11.10.2017 г..
 */

public class SignUpPresenter_Tests {
    @Mock
    AuthenticationProvider mockAuthProvider;

    @Mock
    FirebaseAuth mockFirebaseAuth;

    @Mock
    FirebaseUser mUser;

    @Mock
    SignUpContracts.View mockView;

    private SignUpPresenter mPresenter;

    @Before
    public void setupTest() {
        MockitoAnnotations.initMocks(this);

        mPresenter = new SignUpPresenter();
        mPresenter.subscribe(mockView);

        mPresenter.setAuth(mockAuthProvider);
    }

    @Test
    public void changeEmail_shouldCall_authProviderChangeEmail() {
        String testEmail = "testEmail";
        String testPassword = "testPassword";
        String testDisplayName = "testDisplayName";

        mPresenter.signUpWithEmail(testEmail, testPassword, testDisplayName);

        verify(mockAuthProvider).signUpWithEmail(testEmail, testPassword, testDisplayName);
    }
}
