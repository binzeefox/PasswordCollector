package com.binzeefox.passwordcollector.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.binzeefox.passwordcollector.R;
import com.binzeefox.passwordcollector.activity.InfoListActivity;
import com.binzeefox.passwordcollector.activity.MainActivity;
import com.binzeefox.passwordcollector.activity.UserActivity;
import com.binzeefox.passwordcollector.db.Account;
import com.binzeefox.passwordcollector.util.ListViewSelfAdjuster;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    // 状态值
    public static final int ACTION_ADDACOUNT = 0;
    public static final int ACTION_BYACCOUNT = 1;
    public static final int ACTION_BYUSERNAME = 2;
    public static final int ACTION_BYADDINGDATE = 3;
    public static final int ACTION_BYEMAIL = 4;
    public static final int ACTION_BYPHONE = 5;
    public static final int ACTION_SEARCH = 6;
    public static final int ACTION_SHOWALL = 7;

    private TextView stateTap;
    private ListView listView;
    private List<String> dataList;
    private ArrayAdapter<String> adapter;
    private TextView title;

    public String userName;
    public String selected;
    public int action;

    private boolean isOnUserActivity;


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
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        ListViewSelfAdjuster adjuster = new ListViewSelfAdjuster();
        adjuster.setListViewHeightBasedOnChildren(listView);

        if (getActivity() instanceof UserActivity) {
            isOnUserActivity = true;
        // 宿主为UserActivity
        } else if (getActivity() instanceof InfoListActivity) {
            isOnUserActivity = false;                                                                                   // 宿主为ShowInfoActivity
        }

        stateTap.setText("欢迎登陆");
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selected = dataList.get(i);



                switch (selected) {
                    case "添加账户":
                        // 添加账户算法
                        action = ACTION_ADDACOUNT;
                        if (isOnUserActivity) {
                            Intent intent = new Intent(getActivity(), InfoListActivity.class);
                            intent.putExtra("action", action);
                            startActivity(intent);
                        }
                        break;

                    case "查找账户":
                        // 查找账户算法
                        action = ACTION_SEARCH;
                        if (isOnUserActivity) {
                            Intent intent = new Intent(getActivity(),InfoListActivity.class);
                            intent.putExtra("action",action);
                            startActivity(intent);
                        }
                        break;

                    case "按账户类别显示":
                        // 显示账户类别算法
                        action = ACTION_BYACCOUNT;
                        if (isOnUserActivity) {
                            Intent intent = new Intent(getActivity(),InfoListActivity.class);
                            intent.putExtra("action",action);
                            startActivity(intent);
                        }
                        break;

                    case "按用户名显示":
                        // 显示用户名
                        action = ACTION_BYUSERNAME;
                        if (isOnUserActivity) {
                            Intent intent = new Intent(getActivity(),InfoListActivity.class);
                            intent.putExtra("action", action);
                            startActivity(intent);
                        }
                        break;

                    case "按添加日期显示":
                        // 显示添加日期
                        action = ACTION_BYADDINGDATE;
                        if (isOnUserActivity) {
                            Intent intent = new Intent(getActivity(), InfoListActivity.class);
                            intent.putExtra("action",action);
                            startActivity(intent);
                        }
                        break;

                    case "按邮箱显示":
                        // 显示注册邮箱列表
                        action = ACTION_BYEMAIL;
                        if (isOnUserActivity) {
                            Intent intent = new Intent(getActivity(), InfoListActivity.class);
                            intent.putExtra("action",action);
                            startActivity(intent);
                        }
                        break;

                    case "按电话显示":
                        // 显示电话号码列表
                        action = ACTION_BYPHONE;
                        if (isOnUserActivity) {
                            Intent intent = new Intent(getActivity(),InfoListActivity.class);
                            intent.putExtra("action",action);
                            startActivity(intent);
                        }
                        break;

                    case "！清除信息！":
                        // 清除数据库操作
                        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                        dialog.setTitle("注意！！！");
                        dialog.setMessage("警告：是否确定清除数据？");
                        dialog.setCancelable(false);
                        dialog.setNegativeButton("并不", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        dialog.setPositiveButton("没错", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DataSupport.deleteAll(Account.class,"userName = ? ", userName);
                                Intent intent = new Intent(getActivity(),UserActivity.class);
                                startActivity(intent);
                                Toast.makeText(getActivity(),"已经清除所有账户信息",Toast.LENGTH_SHORT).show();
                            }
                        });

                        break;

                    case "登出":
                        // 登出操作
                        String fromWhere;
                        Intent intent= new Intent(getActivity(),MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        Toast.makeText(getActivity(),"登出成功",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }

            }
        });
    }

}
