package com.jinchi.config.holder;

import com.jinchi.model.Admin;

/**
 * @author yuxuanjiao
 * @date 2017年8月1日 下午7:53:09 
 * @version 1.0
 */

public class AdminInfoHolder {
    private static final ThreadLocal<Admin> adminInfoHolder = new ThreadLocal<>();

    public static void setAdmin(Admin admin) {
        adminInfoHolder.set(admin);
    }

    public static Admin getAdmin() {
        return adminInfoHolder.get();
    }

    public static void clear() {
        adminInfoHolder.remove();
    }
}
