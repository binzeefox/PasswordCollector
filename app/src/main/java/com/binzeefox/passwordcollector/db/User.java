package com.binzeefox.passwordcollector.db;

import org.litepal.crud.DataSupport;

/**
 * Created by tong.xiwen on 2017/3/17.
 */
public class User extends DataSupport{

    private int id;
    private String userName;
    private String userPassword;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
