
package com.binzeefox.passwordcollector;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.binzeefox.passwordcollector.db.Account;
import com.binzeefox.passwordcollector.db.User;
import com.binzeefox.passwordcollector.util.ToastUtil;
import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

/**
 *
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView title_toobar;
    private TextView tv_passConfirm;
    private EditText ed_userName;
    private EditText ed_password;
    private EditText ed_passConfirm;
    private Button bt_ontop;
    private Button bt_oncenter;
    private Button bt_onbotton;

//    private SharedPreferences last = getSharedPreferences("last login",0);


    /**
     * pin = LOGIN ：登陆界面
     * pin = REGIEST ：注册界面
     */
    private final int LOGIN = 0;
    private final int REGIEST = 1;
    private int pin;                // 界面标记
    private boolean mCompare;    // 判断输入值是否已存在于数据库
    private boolean nameCompare; // 判断用户名是否注册
    private String user_lastlog;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        SharedPreferences pref = getSharedPreferences("last login",MODE_PRIVATE);
        user_lastlog = pref.getString("userName_last","");


        title_toobar = (TextView) findViewById(R.id.tv_title);
        ed_userName = (EditText) findViewById(R.id.ed_user);
        ed_userName.setFocusable(true);
        ed_userName.setFocusableInTouchMode(true);
        ed_password = (EditText) findViewById(R.id.ed_userpassword);
        ed_password.setFocusable(true);
        ed_password.setFocusableInTouchMode(true);
        ed_passConfirm = (EditText) findViewById(R.id.ed_passConfirm);
        tv_passConfirm = (TextView) findViewById(R.id.tv_passconfirm);
        bt_ontop = (Button) findViewById(R.id.bt_ontop);
        bt_oncenter = (Button) findViewById(R.id.bt_oncenter);
        bt_onbotton = (Button) findViewById(R.id.bt_onbotton);

        pin = LOGIN;

        // 注册按钮
        bt_ontop.setOnClickListener(this);
        bt_oncenter.setOnClickListener(this);
        bt_onbotton.setOnClickListener(this);

        /**
         * 登陆布局，隐藏注册相关，title设置为登陆
         */
        ed_userName.requestFocus();
        LitePal.getDatabase();
        logview();



    }

    /**
     * 按钮算法
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (pin == LOGIN) {                          // 登陆界面时
            switch (v.getId()) {
                case R.id.bt_ontop:         // 登陆键
                    // 登陆键算法
                    loginAction();
                    break;

                case R.id.bt_oncenter:      // 注册键
                    ed_passConfirm.setText("");
                    ed_password.setText("");
                    ed_userName.setText("");
                    regiestview();
                    pin = REGIEST;
                    break;

                case R.id.bt_onbotton:      // 退出键
                    finish();
                    break;
                default:
                    break;
            }
        } else if (pin == REGIEST) {
            switch (v.getId()) {
                case R.id.bt_ontop:         // 注册键
                    // 注册键算法
                    regiestAction();
                    break;

                case R.id.bt_oncenter:      // 返回键
                    ed_passConfirm.setText("");
                    ed_password.setText("");
                    ed_userName.setText("");
                    logview();
                    pin = LOGIN;
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * 登陆界面
     */
    private void logview() {

        title_toobar.setText("登陆界面");
        bt_ontop.setText("登陆");
        bt_oncenter.setText("注册");
        tv_passConfirm.setVisibility(View.GONE);
        ed_passConfirm.setVisibility(View.GONE);
        bt_onbotton.setVisibility(View.VISIBLE);
        pin = LOGIN;

        if ("".equals(ed_userName.getText().toString())){
            if (!"".equals(user_lastlog)){
                ed_userName.setText(user_lastlog);
                ed_password.requestFocus();
            }else {
                ed_userName.requestFocus();
            }
        }else {
            ed_password.requestFocus();
        }
    }

    /**
     * 注册界面
     */
    private void regiestview() {

        title_toobar.setText("用户注册");
        tv_passConfirm.setVisibility(View.VISIBLE);
        ed_passConfirm.setVisibility(View.VISIBLE);
        bt_ontop.setText("注册");
        bt_oncenter.setText("返回");
        bt_onbotton.setVisibility(View.GONE);
        ed_userName.requestFocus();
        pin = REGIEST;
    }

    /**
     * 注册算法
     */
    private void regiestAction() {

        List<User> userNames = DataSupport.findAll(User.class);
        for (User user : userNames) {
            if (user.getUserName().equals(ed_userName)) {
                mCompare = true;
            }
        }

        if ("".equals(ed_userName.getText())) {
            ToastUtil.showToast(this,"用户名不能为空");
            ed_userName.setText("");
        } else if (mCompare == true) {
            ToastUtil.showToast(this,"该用户名已经存在");
        } else if (ed_password == null) {
            ToastUtil.showToast(this,"请输入密码");
        } else if (!ed_password.getText().toString().equals(ed_passConfirm.getText().toString())) {
            ToastUtil.showToast(this,"密码与重复密码不一致");
            ed_password.setText("");
            ed_passConfirm.setText("");
        } else {
            User user = new User();
            user.setUserName(ed_userName.getText().toString());
            user.setUserPassword(ed_password.getText().toString());
            user.save();
            logview();
            pin = LOGIN;
            ed_passConfirm.setText("");
            ed_password.setText("");
//            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
            ToastUtil.showToast(this,"注册成功");
        }
    }

    /**
     * 登陆算法
     */
    private void loginAction() {

        String name = ed_userName.getText().toString();
        String password = ed_password.getText().toString();

        List<User> users = DataSupport.findAll(User.class);
        for (User user : users) {
            if (user.getUserName().equals(name) && user.getUserPassword().equals(password)) {
                mCompare = true;
            }
        }

        for (User user : users)
            if (user.getUserName().equals(name)) {
                nameCompare = true;
            }
        if ("".equals(ed_userName.getText().toString())) {
//            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            ToastUtil.showToast(this,"请输入用户名");

        } else if (!nameCompare) {
//            Toast.makeText(this, "该账号未注册", Toast.LENGTH_SHORT).show();
            ToastUtil.showToast(this,"该账号未注册");
        } else if (mCompare) {
            Intent intent = new Intent(MainActivity.this, UserActivity.class);
            intent.putExtra("userName", ed_userName.getText().toString());
            SharedPreferences last = getSharedPreferences("last login",0);
            SharedPreferences.Editor editor = last.edit();
            editor.clear();
            editor.putString("userName_last",ed_userName.getText().toString());
            editor.apply();
            startActivity(intent);

            ed_userName.setText("");
            ed_password.setText("");
            ed_password.setText("");
            finish();
            ToastUtil.showToast(this,"欢迎登陆，用户 \0" + name);
        } else {
            ToastUtil.showToast(this,"用户名或密码错误");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dbclean_menu:
                AlertDialog.Builder dialogDbclean = new AlertDialog.Builder(MainActivity.this);
                dialogDbclean.setTitle("警告！！！");
                dialogDbclean.setMessage("确定要清除所有信息吗？");
                dialogDbclean.setCancelable(false);
                dialogDbclean.setNegativeButton("并不。", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        logview();
                    }
                });
                dialogDbclean.setPositiveButton("没错", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DataSupport.deleteAll(User.class);
                        DataSupport.deleteAll(Account.class);
                        SharedPreferences last = getSharedPreferences("last login",0);
                        SharedPreferences.Editor editor = last.edit();
                        editor.clear();
                        editor.commit();

                        logview();
                        ed_userName.setText("");
                        ed_password.setText("");
                        ed_passConfirm.setText("");
                        ed_userName.requestFocus();

                        ToastUtil.showToast(MainActivity.this,"已清除所有数据");

                    }
                });
                dialogDbclean.show();
//                List<User> users = DataSupport.findAll(User.class);
//                List<Account> accounts = DataSupport.findAll(Account.class);
                break;

            case R.id.about_menu:
                AlertDialog.Builder dialogAbout = new AlertDialog.Builder(MainActivity.this);
                dialogAbout.setTitle("你好");
                dialogAbout.setMessage("\t 欢迎使用此软件，说实话这软件其实不怎么安全，所以重要的密码就不要放在这个软件里了");
                dialogAbout.setCancelable(true);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (pin == REGIEST) {
            ed_passConfirm.setText("");
            ed_password.setText("");
            ed_userName.setText("");
            logview();
            pin = LOGIN;
        }else if(pin == LOGIN) {
            finish();
        }
    }


}


