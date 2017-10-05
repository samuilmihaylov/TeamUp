package com.teamup.mihaylov.teamup.UserProfile;

import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;
import com.teamup.mihaylov.teamup.base.contracts.BaseContracts;

/**
 * Created by samui on 5.10.2017 г..
 */

public class UserProfileContracts {
    interface View extends BaseContracts.View<Presenter> {

    }

    interface Presenter extends BaseContracts.Presenter<View> {
        void setAuth(AuthenticationProvider authProvider);

        void changeEmail(String newEmail);

        void changePassword(String newPassword);

        void sendPasswordResetEmail(String email);

        void signOut();

        void googleSignOut();
    }
}
