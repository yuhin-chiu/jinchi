package com.jinchi.constant;

public class HttpConstant {

    // 连接超时, 单位: ms
    public static int CONNECT_TIME_OUT = 800;
    // 读取超时, 单位: ms
    public static int READ_TIME_OUT = 800;
    // 重试次数, 设置为1即表示不重试
    public static int RETRY_MAX_ATTEMPTS = 1;

    //
    public static final int PERIOD = 100;

    // 不验证签名的 url
    public static final String[] NOT_CHECK_URL = { "/", "/health", "/health/", "/index", "/index/" };
    // 不用登陆的url
    public static final String[] NOT_LOGIN_URL = { "/api/**", "/backend/login", "/backend/loginAction",
            "/backend/logout", "/backend/addUser" };

}
