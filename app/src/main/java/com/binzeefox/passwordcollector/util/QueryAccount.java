package com.binzeefox.passwordcollector.util;

import com.binzeefox.passwordcollector.db.Account;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tong.xiwen on 2017/3/24.
 */
public class QueryAccount {

    /**
     * 查询全部
     * @param userName
     * @return
     */
    public List<Account> getListForAll(String userName) {
        List<Account> list = DataSupport.where("userName = ?",userName).find(Account.class);
        return list;
    }

    /**
     * 查询账户类别
     * @param userName
     * @param accountType
     * @return
     */
    public List<Account> getListForAccount(String userName,String accountType) {
        List<Account> list = DataSupport.where("userName = ? and accountType = ?",userName, accountType)
                                        .find(Account.class);
        return list;
    }

    /**
     * 查询账户用户名
     * @param userName
     * @param accountName
     * @return
     */
    public List<Account> getListForAccountName(String userName, String accountName){
        List<Account> list = DataSupport.where("userName = ? and accountName = ?",userName, accountName)
                                        .find(Account.class);
        return list;
    }

    /**
     * 查询创建时间
     * @param createDate
     * @param userName
     * @return
     */
    public List<Account> getListForDate(String createDate,String userName){
        List<Account> list = DataSupport.where("userName = ? and createDate = ?",userName, createDate)
                                        .order("createData desc").find(Account.class);
        return list;
    }

    /**
     * 查询邮箱
     * @param userName
     * @param userEmail
     * @return
     */
    public List<Account> getListForEmail(String userName,String userEmail){
        List<Account> list = DataSupport.where("userName = ? and userEmail = ?",userName, userEmail)
                                        .find(Account.class);
        return list;
    }

    /**
     * 查询电话
     * @param userName
     * @param userPhone
     * @return
     */
    public List<Account> getListForPhone(String userName,String userPhone){
        List<Account> list = DataSupport.where("userName = ? and userPhone = ?",userName, userPhone)
                                        .find(Account.class);
        return list;
    }

    /**
     * 搜索
     * @param input
     * @param userName
     * @return
     */
    public List<Account> search(String input,String userName){
        List<Account> list1 = DataSupport.where("userName = ? and accountName = ?",userName, input)
                                         .find(Account.class);
        List<Account> list2 = DataSupport.where("userName = ? and accountType = ?",userName, input)
                                         .find(Account.class);
        List<Account> list3 = DataSupport.where("userName = ? and userEmail = ?",userName, input)
                                         .find(Account.class);
        List<Account> list4 = DataSupport.where("userName = ? and userPhone = ?",userName, input)
                                         .find(Account.class);
        List<Account> list5 = DataSupport.where("userName = ? and createDate = ?",userName, input)
                                         .find(Account.class);

        List<Account> list = new ArrayList<>();
        list.addAll(list1);
        list.addAll(list2);
        list.addAll(list3);
        list.addAll(list4);
        list.addAll(list5);
        return list;
    }


}
