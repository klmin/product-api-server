package com.product.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtil {

    public static String buildUrl(Object... paths) {
        StringBuilder sb = new StringBuilder();
        for (Object path : paths) {
            sb.append("/").append(path.toString().replaceFirst("^/+", ""));
        }
        return sb.toString();
    }
}
