package com.binzeefox.passwordcollector;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import com.binzeefox.passwordcollector.adapter.InfoItem;
import com.binzeefox.passwordcollector.adapter.InfoItemAdapter;
import com.binzeefox.passwordcollector.db.Account;
import com.binzeefox.passwordcollector.util.QueryAccount;

import java.util.ArrayList;
import java.util.List;

import static com.binzeefox.passwordcollector.UserFragment.*;

public class InfoListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView infoList;
    private DrawerLayout drawer;
    private EditText ed_search;
    private RelativeLayout bt_search;

    private String userName;
    private String title;
    private QueryAccount query;


    /**
     * 注册数据表
     */
    private List<Account> list;

    private List<InfoItem> infoItemList = new ArrayList<>();

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
        setContentView(R.layout.activity_info_layout);

        query = new QueryAccount();




        /**
         * 标题
         */
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        int action = intent.getIntExtra("action", 0);

        initInfoItem(userName);

        prepareInfoItem(action,list);

        showInfoItem(infoItemList);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        /**
         * 滑动菜单
         */
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);

        }



    }

            /**
            * RecyclerList 相关
            */

    /**
     * 初始化item数据
     */
    private void initInfoItem(String userName) {

        list = query.getListForAll(userName);
    }

    /**
     * 区分和准备item数据
     * @param action
     */
    private void prepareInfoItem(int action, List<Account> list) {
        switch (action) {
            case ACTION_SHOWALL:
                title = "全部账户";
                for (Account account : list) {
                    InfoItem infoItem = new InfoItem();
                    infoItem.setTitle(account.getAccountType());
                    infoItem.setMessage("ID:" + account.getAccountName());
                    infoItemList.add(infoItem);
                }
                break;
            case ACTION_BYACCOUNT:
                title = "查找账户类别";
                List<String> mList = new ArrayList();
                for (Account account : list) {
                    if (!mList.contains(account.getAccountType())) {
                        mList.add(account.getAccountType());
                    }
                    for (String in : mList) {
                        InfoItem infoItem = new InfoItem();
                        infoItem.setTitle(in);
                        infoItemList.add(infoItem);
                    }
                }
                // TODO 记得添加点击后的列表显示
                break;
            case ACTION_BYUSERNAME:
                title = "查找用户名";
                mList = new ArrayList<>();
                for (Account account : list) {
                    if (!mList.contains(account.getAccountName())) {
                        mList.add(account.getAccountName());
                    }
                    for (String in : mList) {
                        InfoItem infoItem = new InfoItem();
                        infoItem.setTitle(in);
                        infoItemList.add(infoItem);
                    }
                }
                // TODO 记得添加点击后的列表显示
                break;
            case ACTION_BYADDINGDATE:
                title = "查找记录日期";
                mList = new ArrayList<>();
                for (Account account : list) {
                    if (!mList.contains(account.getCreateDate())) {
                        mList.add(account.getCreateDate());
                    }
                    for (String in : mList){
                        InfoItem infoItem = new InfoItem();
                        infoItem.setTitle(in);
                        infoItemList.add(infoItem);
                    }
                }
                // TODO 记得添加点击后的列表显示
                break;
            case ACTION_BYEMAIL:
                title = "查找邮箱";
                mList = new ArrayList<>();
                for (Account account : list) {
                    if (!mList.contains(account.getUserEmail())){
                        mList.add(account.getUserEmail());
                    }
                    for (String in : mList){
                        InfoItem infoItem = new InfoItem();
                        infoItem.setTitle(in);
                        infoItemList.add(infoItem);
                    }
                }
                // TODO 记得添加点击后的列表显示
                break;
            case ACTION_BYPHONE:
                title = "查找号码";
                mList = new ArrayList<>();
                for (Account account : list) {
                    if (!mList.contains(account.getUserPhone())) {
                        mList.add(account.getUserPhone());
                    }
                    for (String in : mList){
                        InfoItem infoItem = new InfoItem();
                        infoItem.setTitle(in);
                        infoItemList.add(infoItem);
                    }
                }
                // TODO 记得添加点击后的列表显示
                break;
            default:
                break;
        }
    }

    /**
     * 设置recyclerView
     */
    private void showInfoItem(List<InfoItem> infoItemList){

        infoList = (RecyclerView) findViewById(R.id.rv_infolist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        infoList.setLayoutManager(layoutManager);
        InfoItemAdapter adapter = new InfoItemAdapter(infoItemList);
        infoList.setAdapter(adapter);

        ed_search = (EditText) findViewById(R.id.ed_search);
        bt_search = (RelativeLayout) findViewById(R.id.bt_search);
        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = ed_search.getText().toString();
                list = query.search(input,userName);

            }
        });
    }

    /**
     * 滑动菜单相关
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        drawer.closeDrawer(GravityCompat.START);
    }
}
