package com.product.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PatternUtil {
    public static final String ID_REGEX = "^[a-zA-Z0-9]{5,20}$";
    public static final String PHONE_NUMBER_REGEX = "^010\\d{8}$";
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    public static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&#*])[A-Za-z\\d@$!%*?&#*]{8,20}$";
}
