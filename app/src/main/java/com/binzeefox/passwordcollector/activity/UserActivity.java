package com.binzeefox.passwordcollector.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.binzeefox.passwordcollector.R;
import com.binzeefox.passwordcollector.db.Account;
import com.binzeefox.passwordcollector.fragment.UserFragment;
import org.litepal.crud.DataSupport;

import java.util.List;
import java.util.Objects;

public class UserActivity extends AppCompatActivity {

    private boolean isEverLogin;


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

        List<Account> list = DataSupport.findAll(Account.class);
        for (Account account: list){
            if (Objects.equals(account.getUserName(), userName)){
                isEverLogin = true;
            }
        }
        if (isEverLogin ){
            int action = UserFragment.ACTION_SHOWALL;
            Intent intent = new Intent(UserActivity.this,InfoListActivity.class);
            intent.putExtra("action",action);
            startActivity(intent);
        }



    }
}
