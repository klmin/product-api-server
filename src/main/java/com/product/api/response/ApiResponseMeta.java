package com.product.api.response;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponseMeta {

    private int code;
    private String message;

    public static ApiResponseMeta create(int code, String message) {
        return ApiResponseMeta.builder()
                .code(code)
                .message(message)
                .build();
    }
}
