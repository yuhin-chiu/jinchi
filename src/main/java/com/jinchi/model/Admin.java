package com.jinchi.model;

/**
 * @author yuxuanjiao
 * @date 2017年8月1日 下午7:53:49
 * @version 1.0
 */

public class Admin {
    private long id;
    private String userName;
    private String account;
    private String passwd;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
