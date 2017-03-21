package com.binzeefox.passwordcollector.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.binzeefox.passwordcollector.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    // 状态值
//    public static final int LEVEL_ACTION = 0;
//    public static final int LEVEL_BYACCOUNT = 1;
//    public static final int LEVEL_BYACCOUNTID = 2;
//    public static final int LEVEL_BYEMAIL = 3;
//    public static final int LEVEL_BYPHONE = 4;
//    public static final int LEVEL_BYCREATEDATE = 5;
//    private int currentLevel;

    private TextView stateTap;
    private ListView listView;
    private List<String> dataList;
    private ArrayAdapter<String> adapter;
    private TextView title;

    public String userName;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        stateTap = (TextView) view.findViewById(R.id.tv_tap);
        listView = (ListView) view.findViewById(R.id.lv_action);
        Intent intent = getActivity().getIntent();
        userName = intent.getStringExtra("username");                                                            // 获取来自MainActivity的username
        title = (TextView) view.findViewById(R.id.tv_username_userfragment);
        title.setText(userName);
        dataList = new ArrayList<>();
        dataList.add("添加账户");
        dataList.add("查找账户");
        dataList.add("按账户类别显示");
        dataList.add("按用户名显示");
        dataList.add("按添加日期显示");
        dataList.add("按邮箱显示");
        dataList.add("按电话显示");
        dataList.add("！清除信息！");
        dataList.add("登出");
        adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = dataList.get(i);

                switch (selected){
                    case "添加账户":
                        // 添加账户算法
                        break;

                    case "查找账户":
                        // 查找账户算法
                        break;

                    case "按账户类别显示":
                        // 显示账户类别算法
                        break;

                    case "按用户名显示":
                        // 显示用户名
                        break;

                    case "按添加日期显示":
                        // 显示添加日期
                        break;

                    case "按邮箱显示":
                        // 显示注册邮箱列表
                        break;

                    case "按电话显示":
                        // 显示电话号码列表
                        break;

                    case "！清除信息！":
                        // 清除数据库操作
                        break;

                    case "登出":
                        // 登出操作
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
