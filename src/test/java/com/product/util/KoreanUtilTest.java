package com.product.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KoreanUtilTest {

    @Test
    void extractInitials() {

        matches("슈크림", "ㅅㅋㄹ");
        matches("라떼", "ㄹㄸ");
        matches("테스트", "ㅌㅅㅌ");
        matches("슈크림 ㄹㄸ", "ㅅㅋㄹ ㄹㄸ");
        matches("크림", "ㅋㄹ");
        matches("test테스트", "testㅌㅅㅌ");
        matches("홍길 동 강 감찬", "ㅎㄱ ㄷ ㄱ ㄱㅊ");
        matches("abcd", "abcd");
    }

    public void matches(String value, String intial){
        String initials = KoreanUtil.extractInitials(value);
        assertEquals(intial, initials);
    }
}