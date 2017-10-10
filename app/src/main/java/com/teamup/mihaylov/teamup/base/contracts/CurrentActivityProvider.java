package com.teamup.mihaylov.teamup.base.contracts;

import com.teamup.mihaylov.teamup.base.BaseActivity;

/**
 * Created by samui on 9.10.2017 г..
 */

public interface CurrentActivityProvider {
    void setCurrentActivity(BaseActivity activity);
    BaseActivity getCurrentActivity();
}
