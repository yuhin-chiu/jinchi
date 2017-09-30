package com.jinchi.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 * @author yuxuanjiao
 * @date 2017年8月1日 下午8:04:33 
 * @version 1.0
 */

public class MD5Util {

    public static List<NameValuePair> sign(Map<String, String> map, String signKey, String split) {
        StringBuilder builder = new StringBuilder();
        List<NameValuePair> params = new ArrayList<>();
        if (!MapUtils.isEmpty(map)) {
            for (String key : map.keySet()) {
                String value = map.get(key) == null ? "" : map.get(key).trim();
                builder.append(key).append("=").append(value).append(split);
                params.add(new BasicNameValuePair(key, value));
            }
        }

        if (!StringUtils.isEmpty(split)) {
            builder.deleteCharAt(builder.length() - 1);
        }

        params.add(new BasicNameValuePair("sign", toMD5(builder.toString() + signKey)));
        return params;
    }



    public static String esfApiSign(Map<String, String> map, String split) {
        StringBuilder builder = new StringBuilder();
        if (!MapUtils.isEmpty(map)) {
            builder.append(map.get("appid")).append(split);
            builder.append(map.get("timestamp")).append(split);
            builder.append(map.get("key")).append(split);
        }

        return toMD5(builder.toString());
    }

    public static String toMD5(String toHash) {
        return DigestUtils.md5Hex(toHash);
    }

    public static String generateToken(Long brokerId, Long timestamp) {
        return toMD5("xxx" + brokerId + timestamp);
    }

    public static String sohuMD5(String toHash) {
        int len = toHash.length();
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        int j = len / 2;
        for (; i < len / 2; ++i, ++j) {
            stringBuilder.append(toHash.substring(i, i + 1));
            stringBuilder.append(toHash.substring(j, j + 1));
        }

        if (j < len - 1) {
            stringBuilder.append(toHash.substring(j, j + 1));
        }

        return toMD5(toMD5(stringBuilder.toString()));
    }
}

