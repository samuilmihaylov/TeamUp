package com.teamup.mihaylov.teamup.base.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by samui on 11.10.2017 г..
 */

@Entity
public class DaoUser {

    @Id
    private String id;
    private String name;

    public DaoUser(){

    }

    @Generated(hash = 1599338689)
    public DaoUser(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
