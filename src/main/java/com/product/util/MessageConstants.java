package com.product.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageConstants {
    public static final String NO_DATA_FOUND = "NO_DATA_FOUND";
    public static final String DELETE_FAILED = "DELETE_FAILED";

    public static final String ERROR_REQUIRED_VALUE = "ERROR_REQUIRED_VALUE";
    public static final String ERROR_SERVER_ERROR = "ERROR_SERVER_ERROR";
    public static final String ERROR_MISSING_HEADER = "ERROR_MISSING_HEADER";
    public static final String ERROR_INVALID_TOKEN = "ERROR_INVALID_TOKEN";
    public static final String ERROR_MALFORMED_JWT = "ERROR_MALFORMED_JWT";
    public static final String ERROR_TOKEN_EXPIRED = "ERROR_TOKEN_EXPIRED";
}
