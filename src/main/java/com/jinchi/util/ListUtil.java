package com.jinchi.util;

import java.util.Collection;

/**
 * @author yuxuanjiao
 * @date 2017年9月30日 下午2:54:24
 * @version 1.0
 */

public class ListUtil {

    // 将集合中元素用分隔符链接
    public static String connect(Collection<?> objects, String split) {
        if (objects == null || objects.isEmpty()) {
            return null;
        }
        if (split == null) {
            split = "";
        }
        StringBuilder strObjects = null;

        for (Object tmp : objects) {
            if (tmp == null) {
                continue;
            }
            if (strObjects == null) {
                strObjects = new StringBuilder();
            } else {
                strObjects.append(split);
            }
            strObjects.append(tmp.toString().trim());
        }

        return strObjects == null ? "" : strObjects.toString();
    }

    public static String connect(Collection<?> objects) {
        return connect(objects, ",");
    }
}
