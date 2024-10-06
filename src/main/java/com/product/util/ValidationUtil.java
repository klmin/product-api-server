package com.product.util;

import com.product.api.exception.ApiRuntimeException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationUtil {

    public static void validateNotNull(Object field, String errorMessage) {
        if (field == null) {
            throw new ApiRuntimeException(HttpStatus.BAD_REQUEST, errorMessage);
        }
    }

    public static void validateHasText(String field, String errorMessage) {
        if (!StringUtils.hasText(field)) {
            throw new ApiRuntimeException(HttpStatus.BAD_REQUEST, errorMessage);
        }
    }
}
