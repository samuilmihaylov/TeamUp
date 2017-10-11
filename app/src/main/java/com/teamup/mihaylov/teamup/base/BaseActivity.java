package com.teamup.mihaylov.teamup.base;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.teamup.mihaylov.teamup.R;
import com.teamup.mihaylov.teamup.TeamUpApplication;
import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;
import com.teamup.mihaylov.teamup.base.data.LocalUsersData;
import com.teamup.mihaylov.teamup.base.models.DaoUser;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by samui on 9.10.2017 г..
 */

public class BaseActivity extends DaggerAppCompatActivity {

    private OnRequestPermissionResultLister mOnRequestPermissionResultLister;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Ubuntu-Medium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((TeamUpApplication) getApplication())
                .setCurrentActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((TeamUpApplication) getApplication())
                .setCurrentActivity(null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ((TeamUpApplication) getApplication())
                .setCurrentActivity(null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mOnRequestPermissionResultLister == null) {
            return;
        }

        mOnRequestPermissionResultLister.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void setOnRequestPermissionResultLister(OnRequestPermissionResultLister onRequestPermissionResultLister) {
        mOnRequestPermissionResultLister = onRequestPermissionResultLister;
    }

    public interface OnRequestPermissionResultLister {
        void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
    }
}