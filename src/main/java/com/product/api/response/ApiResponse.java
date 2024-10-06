package com.product.api.response;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private static final HttpStatus DEFAULT_SUCCESS_STATUS = HttpStatus.OK;

    private ApiResponseMeta meta;
    private T data;

    public static ResponseEntity<ApiResponse<Void>> created() {
        return success(HttpStatus.CREATED);
    }

    public static ResponseEntity<ApiResponse<Void>> success() {
        return success(DEFAULT_SUCCESS_STATUS);
    }

    public static ResponseEntity<ApiResponse<Void>> success(HttpStatus status) {
        return create(status, status.value(), status.getReasonPhrase(), null);
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(T data) {
        return create(DEFAULT_SUCCESS_STATUS, DEFAULT_SUCCESS_STATUS.value(), DEFAULT_SUCCESS_STATUS.getReasonPhrase(), data);
    }

    public static <T> ResponseEntity<ApiResponse<T>> create(HttpStatus status, int httpCode, String message, T data) {
        return ResponseEntity.status(status).body(
                ApiResponse.<T>builder()
                        .meta(ApiResponseMeta.create(httpCode, message))
                        .data(data)
                        .build()
        );
    }

}
