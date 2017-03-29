package com.binzeefox.passwordcollector;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import com.binzeefox.passwordcollector.db.Account;
import com.binzeefox.passwordcollector.db.User;
import org.litepal.crud.DataSupport;

import java.util.List;

public class UserActivity extends AppCompatActivity {

// TODO 添加该页recyclerList的点击事件

    @SuppressLint("WrongViewCast")
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


        setContentView(R.layout.activity_user);
        UserFragment fragment = (UserFragment) getSupportFragmentManager().findFragmentById(R.id.user_fragment);
        String userName = fragment.userName;

        List<Account> list = DataSupport.where("userName = ?", userName).find(Account.class);



        if (!list.isEmpty()) {
            int action = UserFragment.ACTION_SHOWALL;
            Intent intent = new Intent(UserActivity.this, InfoListActivity.class);
            intent.putExtra("userName",fragment.userName);
            intent.putExtra("action", action);
            startActivity(intent);
        }



    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            //TODO something
            Intent intent = new Intent(UserActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
