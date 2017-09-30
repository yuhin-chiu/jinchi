package com.jinchi.enums;

import java.util.HashMap;
import java.util.Map;

public enum ApiResponseEnum {
    SUCCESS(200, "success"), BAD_REQUEST(400, "请求格式错误(必要参数未传等)"), NON_LOGIN_IN(401, "用户未登录"), PAGE_NOT_FOUND(404,
            "请求接口不存在"), INTERNAL_ERROR(500, "服务器内部错误"), FILE_SAVE_FAILED(5001, "文件保存错误"), FILE_SAVE_EMPTY(5002,
                    "上传文件为空"), COMMON_ERROR(888, "请求异常"), SIGN_NOT_MATCH(666, "签名验证未通过");

    private Integer code;
    private String content;

    public Integer getCode() {
        return code;
    }

    public String getContent() {
        return content;
    }

    ApiResponseEnum(Integer code, String content) {
        this.code = code;
        this.content = content;
    }

    private static final Map<Integer, String> ERROR_CODE_MAP = new HashMap<>();

    static {
        for (ApiResponseEnum e : ApiResponseEnum.values()) {
            ERROR_CODE_MAP.put(e.code, e.content);
        }
    }

    public static String getContent(Integer code) {
        return ERROR_CODE_MAP.get(code);
    }
}
