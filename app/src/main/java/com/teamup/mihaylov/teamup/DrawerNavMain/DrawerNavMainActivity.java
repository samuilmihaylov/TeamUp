package com.teamup.mihaylov.teamup.DrawerNavMain;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.teamup.mihaylov.teamup.Events.CreateEvent.CreateEventActivity;
import com.teamup.mihaylov.teamup.Events.ListEvents.ListEventsActivity;
import com.teamup.mihaylov.teamup.Home.HomeFragment;
import com.teamup.mihaylov.teamup.SignUp.SignUpActivity;
import com.teamup.mihaylov.teamup.UserProfile.UserProfileActivity;
import com.teamup.mihaylov.teamup.R;
import com.teamup.mihaylov.teamup.SignIn.SignInActivity;

public class DrawerNavMainActivity extends AppCompatActivity {

    private Drawer mDrawer;
    private Toolbar mToolbar;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Fragment mFragment;
    private FirebaseUser mUser;
    private DrawerBuilder mDrawerBuilder;
    private PrimaryDrawerItem mHomeDrawerItem;
    private PrimaryDrawerItem mSignInDrawerItem;
    private PrimaryDrawerItem mSignUpDrawerItem;
    private PrimaryDrawerItem mSignOutDrawerItem;
    private PrimaryDrawerItem mProfileDrawerItem;
    private AccountHeader mHeaderResult;
    private PrimaryDrawerItem mCreateEventDrawerItem;
    private PrimaryDrawerItem mListEventsDrawerItem;
    private SectionDrawerItem mEventsSectionDrawerItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mUser = firebaseAuth.getCurrentUser();
                updateDrawer();
            }
        };

        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder, String tag) {
                Glide.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Glide.clear(imageView);
            }

            @Override
            public Drawable placeholder(Context ctx, String tag) {
                if (DrawerImageLoader.Tags.PROFILE.name().equals(tag)) {
                    return DrawerUIUtils.getPlaceHolder(ctx);
                } else if (DrawerImageLoader.Tags.ACCOUNT_HEADER.name().equals(tag)) {
                    return new IconicsDrawable(ctx)
                            .iconText(" ")
                            .backgroundColorRes(com.mikepenz.materialdrawer.R.color.primary)
                            .sizeDp(56);
                }

                return super.placeholder(ctx, tag);
            }
        });

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_container, new HomeFragment())
                .commit();
    }

    protected void onStart() {
        mAuth.addAuthStateListener(mAuthListener);
        mUser = mAuth.getCurrentUser();

        setupDrawerItems();
        setupDrawer();

        super.onStart();
    }

    protected void onStop() {
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }

        super.onStop();
    }

    public void setupDrawerHeader() {
        ProfileDrawerItem userProfile = new ProfileDrawerItem();

        if (mUser != null) {
            String userEmail = mUser.getEmail();
            String userName = mUser.getDisplayName();
            if (mUser.getPhotoUrl() != null) {
                userProfile.withName(userEmail).withTextColor(Color.BLACK)
                        .withEmail(userName).withTextColor(Color.BLACK)
                        .withIcon(mUser.getPhotoUrl().toString());
            }

            userProfile.withName(userEmail).withTextColor(Color.BLACK)
                    .withEmail(userName).withTextColor(Color.BLACK);

        } else {
            userProfile
                    .withName("Not signed in").withTextColor(Color.BLACK)
                    .withEmail("Anonymous").withTextColor(Color.BLACK);
        }

        mHeaderResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.drawer_profile_background)
                .addProfiles(userProfile)
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();
    }

    public void setupDrawerItems() {

        mHomeDrawerItem =
                new PrimaryDrawerItem().withName(R.string.drawer_home).withIdentifier(1)
                        .withIcon(GoogleMaterial.Icon.gmd_home)
                        .withIconColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));

        mProfileDrawerItem =
                new PrimaryDrawerItem().withName(R.string.drawer_profile).withIdentifier(2)
                        .withIcon(GoogleMaterial.Icon.gmd_person)
                        .withIconColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));

        mSignInDrawerItem =
                new PrimaryDrawerItem().withName(R.string.drawer_sign_in).withIdentifier(3)
                        .withIcon(FontAwesome.Icon.faw_sign_in)
                        .withIconColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));

        mSignUpDrawerItem =
                new PrimaryDrawerItem().withName(R.string.drawer_sign_up).withIdentifier(4)
                        .withIcon(FontAwesome.Icon.faw_user_plus)
                        .withIconColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));

        mSignOutDrawerItem =
                new PrimaryDrawerItem().withName(R.string.drawer_sign_out).withIdentifier(5)
                        .withIcon(FontAwesome.Icon.faw_sign_out)
                        .withIconColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));

        mListEventsDrawerItem = new PrimaryDrawerItem().withName(R.string.drawer_list_events).withIdentifier(6)
                .withIcon(FontAwesome.Icon.faw_list)
                .withIconColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));

        mCreateEventDrawerItem = new PrimaryDrawerItem().withName(R.string.drawer_create_event).withIdentifier(7)
                .withIcon(GoogleMaterial.Icon.gmd_create)
                .withIconColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));

        mEventsSectionDrawerItem = new SectionDrawerItem().withName(R.string.drawer_events_section);
    }

    public void setupDrawer() {

        setupDrawerHeader();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mDrawerBuilder = new DrawerBuilder()
                .withSelectedItem(-1)
                .withActivity(this)
                .withToolbar(mToolbar)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withAccountHeader(mHeaderResult)
                .withRootView(R.id.drawer_layout);

        if (mAuth.getCurrentUser() == null) {
            mDrawer = mDrawerBuilder.addDrawerItems(
                    mHomeDrawerItem,
                    mSignInDrawerItem,
                    mSignUpDrawerItem)
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                            Intent intent;

                            switch (position) {
                                case 1:
                                    mFragment = new HomeFragment();
                                    break;
                                case 2:
                                    intent = new Intent(getApplicationContext(), SignInActivity.class);
                                    startActivity(intent);
                                    break;
                                case 3:
                                    intent = new Intent(getApplicationContext(), SignUpActivity.class);
                                    startActivity(intent);
                                    break;
                            }

                            if (mFragment != null) {
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.content_container, mFragment)
                                        .commit();
                            }

                            return false;
                        }
                    })
                    .build();
        } else {
            mDrawer = mDrawerBuilder.addDrawerItems(
                    mHomeDrawerItem,
                    mProfileDrawerItem,
                    mSignOutDrawerItem,
                    mEventsSectionDrawerItem,
                    mListEventsDrawerItem,
                    mCreateEventDrawerItem)
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {

                        private Intent intent;

                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                            switch ((int) drawerItem.getIdentifier()) {
                                case 1:
                                    mFragment = new HomeFragment();
                                    break;
                                case 2:
                                    intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                                    startActivity(intent);
                                    break;
                                case 5:
                                    mAuth.signOut();
                                    updateDrawer();
                                    break;
                                case 6:
                                    intent = new Intent(getApplicationContext(), ListEventsActivity.class);
                                    startActivity(intent);
                                    break;
                                case 7:
                                    intent = new Intent(getApplicationContext(), CreateEventActivity.class);
                                    startActivity(intent);
                                    break;
                            }

                            if (mFragment != null) {
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.content_container, mFragment)
                                        .commit();
                            }

                            return false;
                        }
                    })
                    .build();
        }
    }

    public void updateDrawer() {
        mDrawer.removeHeader();
        setupDrawerHeader();
        setupDrawer();
    }
}
