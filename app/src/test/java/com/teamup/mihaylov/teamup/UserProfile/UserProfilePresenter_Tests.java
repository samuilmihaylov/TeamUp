package com.teamup.mihaylov.teamup.UserProfile;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

/**
 * Created by samui on 11.10.2017 г..
 */

public class UserProfilePresenter_Tests {

    @Mock
    AuthenticationProvider mockAuthProvider;

    @Mock
    FirebaseAuth mockFirebaseAuth;

    @Mock
    FirebaseUser mUser;

    @Mock
    UserProfileContracts.View mockView;

    private UserProfilePresenter mPresenter;

    @Before
    public void setupTest() {
        MockitoAnnotations.initMocks(this);

        mPresenter = new UserProfilePresenter();
        mPresenter.subscribe(mockView);

        mPresenter.setAuth(mockAuthProvider);
    }

    @Test
    public void changeEmail_shouldCall_authProviderChangeEmail() {
        String testEmail = "testEmail";

        mPresenter.changeEmail(testEmail);

        verify(mockAuthProvider).changeEmail(testEmail);
    }

    @Test
    public void changePassword_shouldCall_authProviderChangePassword() {
        String testEmail = "testPassword";

        mPresenter.changePassword(testEmail);

        verify(mockAuthProvider).changePassword(testEmail);
    }

    @Test
    public void sendPasswordResetEmail_shouldCall_authProviderSendPasswordResetEmail() {
        String testEmail = "testEmail";

        mPresenter.sendPasswordResetEmail(testEmail);

        verify(mockAuthProvider).sendPasswordResetEmail(testEmail);
    }

    @Test
    public void googleSignOut_shouldCall_authProviderGoogleSignOut() {
        String testEmail = "testEmail";

        mPresenter.googleSignOut();

        verify(mockAuthProvider).googleSignOut();
    }

    @Test
    public void signOut_shouldCall_authProviderSignOut() {
        mPresenter.signOut();

        verify(mockAuthProvider).signOut();
    }
}
