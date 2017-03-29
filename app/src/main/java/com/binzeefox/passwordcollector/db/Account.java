package com.binzeefox.passwordcollector.db;

import org.litepal.crud.DataSupport;

/**
 * Created by tong.xiwen on 2017/3/17.
 */
public class Account extends DataSupport{

    private int id;                                                                                                     // 主键
    private String userName;                                                                                            // 用户名
    private String accountType;
    private String accountName;                                                                                         // 账户名
    private String comment;
    private String accountPassword;                                                                                     // 账户密码
    private String createDate;                                                                                          // 创建日期
    private String userEmail;                                                                                           // 注册邮箱
    private String userPhone;                                                                                              // 绑定电话

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
