package com.teamup.mihaylov.teamup;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

public class MainActivity extends AppCompatActivity {

    private ActionBarDrawerToggle mDrawerToggle;
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
    private AccountHeader mHeaderResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mUser = firebaseAuth.getCurrentUser();
                if (mUser!=null){
                    Toast.makeText(getApplicationContext(), "User logged in", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "User not logged in", Toast.LENGTH_SHORT).show();
                }
            }
        };

        setupDrawerHeader();
        setupDrawerItems();
        setupDrawer();
    }

    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_container, new HomeFragment())
                .commit();
    }

    public void onStop(){
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
        super.onStop();
    }

    public void setupDrawerHeader(){
        String userEmail;
        String userName;

        if(mAuth.getCurrentUser() != null){
            userEmail = mAuth.getCurrentUser().getEmail();
            userName = mAuth.getCurrentUser().getDisplayName();
        }else {
            userEmail = "Not signed in";
            userName = "Anonymous";
        }

        mHeaderResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.drawer_profile_background)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName(userEmail).withTextColor(Color.BLACK)
                                .withEmail(userName).withTextColor(Color.BLACK)
                                .withIcon(FontAwesome.Icon.faw_user_circle)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();
    }

    public void setupDrawerItems(){
        mHomeDrawerItem =
                new PrimaryDrawerItem().withName(R.string.drawer_home).withIdentifier(1)
                        .withIcon(GoogleMaterial.Icon.gmd_home);

        mSignInDrawerItem =
                new PrimaryDrawerItem().withName(R.string.drawer_sign_in).withIdentifier(2)
                        .withIcon(FontAwesome.Icon.faw_sign_in);

        mSignUpDrawerItem =
                new PrimaryDrawerItem().withName(R.string.drawer_sign_up).withIdentifier(3)
                        .withIcon(FontAwesome.Icon.faw_user_plus);

        mSignOutDrawerItem =
                new PrimaryDrawerItem().withName(R.string.drawer_sign_out).withIdentifier(4)
                        .withIcon(FontAwesome.Icon.faw_sign_out);
    }

    public void setupDrawer(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mDrawerBuilder = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withAccountHeader(mHeaderResult)
                .withRootView(R.id.drawer_layout);

        if(mAuth.getCurrentUser() == null){
            mDrawer = mDrawerBuilder.addDrawerItems(
                    mHomeDrawerItem,
                    mSignInDrawerItem,
                    mSignUpDrawerItem)
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                            switch (position){
                                case 1:
                                    mFragment = new HomeFragment();
                                    break;
                                case 2:
                                    mFragment = new SigninFragment();
                                    break;
                                case 3:
                                    mFragment = new SignupFragment();
                                    break;
                            }

                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.content_container, mFragment)
                                    .commit();

                            return false;
                        }
                    })
                    .build();
        }
        else {
            mDrawer = mDrawerBuilder.addDrawerItems(
                    mHomeDrawerItem,
                    mSignOutDrawerItem)
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                            switch (position){
                                case 1:
                                    mFragment = new HomeFragment();
                                    break;
                                case 2:
                                    mAuth.signOut();
                                    updateDrawer();
                                    break;
                            }

                            if(mFragment != null){
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

    public void updateDrawer(){
        mDrawer.removeHeader();
        setupDrawerHeader();
        setupDrawer();
    }
}
