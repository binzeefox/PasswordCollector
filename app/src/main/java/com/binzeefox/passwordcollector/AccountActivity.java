package com.binzeefox.passwordcollector;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.binzeefox.passwordcollector.db.Account;
import com.binzeefox.passwordcollector.util.ToastUtil;
import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.List;

import static com.binzeefox.passwordcollector.UserFragment.ACTION_ADDACOUNT;

public class AccountActivity extends AppCompatActivity {

    /**
     * 界面控件
     */
    private Toolbar toolbar;
    private EditText ed_accountType;
    private EditText ed_userName;
    private EditText ed_password;
    private TextView tv_createData;
    private EditText ed_email;
    private EditText ed_phone;
    private EditText ed_comment;

    private Button bt_collect;
    private Button bt_refactor;


    private List<Account> listCheak;
    private String userName;
    private boolean isAddAccount;
    private int infoID;         // 查询到的id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_account);


        /**
         * 注册各控件
         */
        ed_accountType = (EditText) findViewById(R.id.ed_userType);
        ed_userName = (EditText) findViewById(R.id.ed_username);
        ed_password = (EditText) findViewById(R.id.ed_password);
        tv_createData = (TextView) findViewById(R.id.tv_createdata);
        ed_email = (EditText) findViewById(R.id.ed_email);
        ed_phone = (EditText) findViewById(R.id.ed_phone);
        ed_comment = (EditText) findViewById(R.id.ed_comment);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        bt_collect = (Button) findViewById(R.id.bt_collect);
        bt_refactor = (Button) findViewById(R.id.bt_refactor);

//        ed_accountType.setFocusable(false);
//        ed_accountType.setFocusableInTouchMode(false);
//        bt_collect.setVisibility(View.GONE);


        /**
         * 从intent中读取数据
         */
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        infoID = intent.getIntExtra("infoID", -1);
        /**
         * 界面判断
         */


        if (intent.getIntExtra("action", 0) == ACTION_ADDACOUNT) {
            isAddAccount = true;
        } else {
            isAddAccount = false;
        }

        if (isAddAccount) {
            showAddingView();
        } else {
            showInfoView();
        }
    }

    /**
     * 显示信息
     */
    private void showInfoView() {

        toolbar.setTitle("详细信息");

        bt_collect.setVisibility(View.GONE);
        bt_refactor.setVisibility(View.VISIBLE);

        ed_accountType.setFocusable(false);
        ed_accountType.setFocusableInTouchMode(false);

        ed_userName.setFocusable(false);
        ed_userName.setFocusableInTouchMode(false);

        ed_password.setFocusable(false);
        ed_password.setFocusableInTouchMode(false);

        ed_email.setFocusable(false);
        ed_email.setFocusableInTouchMode(false);

        ed_phone.setFocusable(false);
        ed_phone.setFocusableInTouchMode(false);

        ed_comment.setFocusable(false);
        ed_comment.setFocusableInTouchMode(false);

        bt_collect.setVisibility(View.GONE);
        bt_refactor.setVisibility(View.VISIBLE);

        Account account = DataSupport.find(Account.class, infoID);
        ed_accountType.setText(account.getAccountType());
        ed_userName.setText(account.getAccountName());
        ed_password.setText(account.getAccountPassword());
        tv_createData.setText(account.getCreateDate());
        ed_email.setText(account.getUserEmail());
        ed_phone.setText(account.getUserPhone());
        ed_comment.setText(account.getComment());


    }

    private SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private String date = sDateFormat.format(new java.util.Date());

    /**
     * 添加信息
     */
    private void showAddingView() {

        bt_collect.setVisibility(View.VISIBLE);
        bt_refactor.setVisibility(View.GONE);

        toolbar.setTitle("添加纪录");
        ed_accountType.requestFocus();
        ed_accountType.setFocusable(true);
        ed_accountType.setFocusableInTouchMode(true);


        ed_userName.setFocusable(true);
        ed_userName.setFocusableInTouchMode(true);

        ed_password.setFocusable(true);
        ed_password.setFocusableInTouchMode(true);

        ed_email.setFocusable(true);
        ed_email.setFocusableInTouchMode(true);

        ed_phone.setFocusable(true);
        ed_phone.setFocusableInTouchMode(true);

        ed_comment.setFocusable(true);
        ed_comment.setFocusableInTouchMode(true);
        /**
         * 注册键按钮事件
         */
        bt_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 判断各个文本框文字状态


                listCheak = DataSupport.where("userName = ? and accountType = ? and accountName = ?"
                        ,userName,ed_accountType.getText().toString()
                        ,ed_userName.getText().toString()).find(Account.class);

                if ("".equals(date)) {
                    ToastUtil.showToast(AccountActivity.this, "获取日期失败，请重试");
                } else {
                    if ("".equals(ed_accountType.getText().toString()) || "".equals(ed_userName.getText().toString())
                            || "".equals(ed_password.getText().toString())) {
                        ToastUtil.showToast(AccountActivity.this, "账户类别、用户名和密码不能为空");
                    } else {
                        if ("".equals(ed_email.getText().toString())) {
                            ed_email.setText("Null");
                        }
                        if ("".equals(ed_phone.getText().toString())) {
                            ed_phone.setText("Null");
                        }
                        if ("".equals(ed_comment.getText().toString())){
                            ed_comment.setText("Null");
                        }
                        if (!listCheak.isEmpty()){
                            AlertDialog.Builder builder = new AlertDialog.Builder(AccountActivity.this);
                            builder.setTitle("嗯哼？？？");
                            builder.setMessage("该条目已经存在，是否替换修改？");
                            builder.setCancelable(false);
                            builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    setInfo();
                                }
                            });
                            builder.setNegativeButton("并不", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            builder.show();
                        } else {
                            setInfo();
                            Account account = DataSupport.findLast(Account.class);
                            infoID = account.getId();
                            showInfoView();
                        }
                    }
                }


            }
        });


    }

    private void setInfo() {
        Account account = new Account();
        account.setUserName(userName);
        account.setAccountType(ed_accountType.getText().toString());
        account.setAccountName(ed_userName.getText().toString());
        account.setAccountPassword(ed_password.getText().toString());
        account.setCreateDate(date);
        account.setUserEmail(ed_email.getText().toString());
        account.setUserPhone(ed_phone.getText().toString());
        account.setComment(ed_comment.getText().toString());
        boolean result = account.saveOrUpdate("userName = ? and accountType = ? and accountName = ?",
                userName, account.getAccountType(), account.getAccountName());
//        boolean result = account.saveOrUpdate("userName = ? and accountType = ? and accountName = ?",
//                userName,ed_accountType.getText().toString(),ed_userName.getText().toString());
        if (result){
            ToastUtil.showToast(this,"写入成功");
            Account data = DataSupport.findLast(Account.class);
            Log.d("AccountActivity","userName:" + data.getUserName());
            Log.d("AccountActivity","AccountType:" + data.getAccountType());
            Log.d("AccountActivity","AccountName:" + data.getAccountName());
            Log.d("AccountActivity","AccountPass:" + data.getAccountPassword());
        }else {
            ToastUtil.showToast(this,"写入失败");
        }

        Account lastDoing = DataSupport.findLast(Account.class);
        String a = lastDoing.getAccountType();
        String b = lastDoing.getAccountName();
        Log.d("AccountActivity","a = " + a);
        Log.d("AccountActivity","b = " + b);
        if (a.equals(ed_accountType.getText().toString()) && b.equals(ed_userName.getText().toString())) {
            ToastUtil.showToast(AccountActivity.this, "操作成功");
        }else {
            ToastUtil.showToast(AccountActivity.this, "操作失败");
        }

    }
}
