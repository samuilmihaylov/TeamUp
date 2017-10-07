package com.teamup.mihaylov.teamup.DrawerNavMain;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class DrawerNavMainActivity extends DaggerAppCompatActivity {

    private static final int CAMERA_REQUEST = 1;
    private static final int REQUEST_WRITE_PERMISSION = 2;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 3;
    private Fragment mFragment;

    private Toolbar mToolbar;
    private Drawer mDrawer;
    private File photoFile;
    private String mCurrentPhotoPath;
    private DrawerBuilder mDrawerBuilder;
    private AccountHeader mHeaderResult;
    private PrimaryDrawerItem mHomeDrawerItem;
    private PrimaryDrawerItem mSignInDrawerItem;
    private PrimaryDrawerItem mSignUpDrawerItem;
    private PrimaryDrawerItem mSignOutDrawerItem;
    private PrimaryDrawerItem mProfileDrawerItem;
    private PrimaryDrawerItem mCreateEventDrawerItem;
    private PrimaryDrawerItem mListEventsDrawerItem;
    private SectionDrawerItem mEventsSectionDrawerItem;

    @Inject
    AuthenticationProvider mAuthProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_container, new HomeFragment())
                .commit();
    }

    protected void onStart() {
        setupDrawerHeaderProfileImage();
        setupDrawerItems();
        setupDrawer();

        super.onStart();
    }

    protected void onStop() {
        super.onStop();
    }

    public void setupDrawerHeaderProfileImage() {
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
    }

    public void setupDrawerHeader(FirebaseUser user) {
        ProfileDrawerItem userProfile = new ProfileDrawerItem();

        if (user != null) {
            String userEmail = user.getEmail();
            String userName = user.getDisplayName();
            if (user.getPhotoUrl() != null) {
                userProfile.withName(userEmail).withTextColor(Color.BLACK)
                        .withEmail(userName).withTextColor(Color.BLACK)
                        .withIcon(user.getPhotoUrl().toString());
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
                        if (mAuthProvider.getUser().getProviderId().equals("email")){
                            if(checkAndRequestPermissions()){
                                openCamera();
                            }
                        }
                            return false;
                    }
                })
                .build();
    }

    private boolean checkAndRequestPermissions() {
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String imageFileName = "profile_image.jpg";

        File path = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "TeamUp");

        try {
            if (!path.exists()) {
                path.mkdirs();
            }

            File imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "TeamUp" + File.separator + imageFileName);

            Uri imageUri = FileProvider.getUriForFile(getApplicationContext(), "com.TeamUpApplication.android.fileprovider", imageFile);

            takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(takePictureIntent, CAMERA_REQUEST);

            MediaScannerConnection.scanFile(this,
                    new String[]{imageFile.toString()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("ExternalStorage", "Scanned " + path + ":");
                            Log.i("ExternalStorage", "-> uri=" + uri);
                        }
                    });
        } catch (Exception e) {
            Log.w("ExternalStorage", "Error writing ", e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            switch (resultCode) {
                case Activity.RESULT_OK: {
                    Toast.makeText(getApplicationContext(), "saved", Toast.LENGTH_LONG).show();
                    break;
                }
                case Activity.RESULT_CANCELED: {
                    Toast.makeText(getApplicationContext(), "not saved", Toast.LENGTH_LONG).show();
                    break;
                }
            }
        }
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

        FirebaseUser user = mAuthProvider.getUser();

        setupDrawerHeader(user);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        long selectedDrawerItem = getIntent().getLongExtra("SELECTED_DRAWER_ITEM", 1);

        mDrawerBuilder = new DrawerBuilder()
                .withSelectedItem(selectedDrawerItem)
                .withActivity(this)
                .withToolbar(mToolbar)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withAccountHeader(mHeaderResult)
                .withRootView(R.id.drawer_layout);

        if (user == null) {
            mDrawer = mDrawerBuilder.addDrawerItems(
                    mHomeDrawerItem,
                    mSignInDrawerItem,
                    mSignUpDrawerItem)
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        Intent intent;

                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            switch ((int) drawerItem.getIdentifier()) {
                                case 1:
                                    mFragment = new HomeFragment();
                                    break;
                                case 3:
                                    intent = new Intent(getApplicationContext(), SignInActivity.class);
                                    break;
                                case 4:
                                    intent = new Intent(getApplicationContext(), SignUpActivity.class);
                                    break;
                            }

                            if (mFragment != null) {
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.content_container, mFragment)
                                        .commit();
                            }

                            if (intent != null) {
                                intent.putExtra("SELECTED_DRAWER_ITEM", drawerItem.getIdentifier());
                                startActivity(intent);
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

                        Intent intent;

                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                            switch ((int) drawerItem.getIdentifier()) {
                                case 1:
                                    mFragment = new HomeFragment();
                                    break;
                                case 2:
                                    intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                                    break;
                                case 5:
                                    mAuthProvider.signOut();
                                    updateDrawer();
                                    break;
                                case 6:
                                    intent = new Intent(getApplicationContext(), ListEventsActivity.class);
                                    break;
                                case 7:
                                    intent = new Intent(getApplicationContext(), CreateEventActivity.class);
                                    break;
                            }

                            if (mFragment != null) {
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.content_container, mFragment)
                                        .commit();
                            }

                            if (intent != null) {
                                intent.putExtra("SELECTED_DRAWER_ITEM", drawerItem.getIdentifier());
                                startActivity(intent);
                            }

                            return false;
                        }
                    })
                    .build();
        }
    }

    public void updateDrawer() {
        mDrawer.removeHeader();
        setupDrawer();
    }
}