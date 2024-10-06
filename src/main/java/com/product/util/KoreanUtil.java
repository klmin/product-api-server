package com.product.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KoreanUtil {

    private static final char[] CHOSEONG = {
            'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    };

    public static String extractInitials(String input) {
        if (!StringUtils.hasText(input)) {
            return "";
        }
        StringBuilder initials = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (c >= 0xAC00 && c <= 0xD7A3) {
                int unicodeValue = c - 0xAC00;
                int choIndex = unicodeValue / (21 * 28);
                initials.append(CHOSEONG[choIndex]);
            } else {
                initials.append(c);
            }
        }
        return initials.toString();
    }
}
