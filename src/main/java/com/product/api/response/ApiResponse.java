package com.product.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.product.api.response.pagination.ApiCursorPaginationResponse;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private static final HttpStatus DEFAULT_SUCCESS_STATUS = HttpStatus.OK;
    private static final HttpStatus DEFAULT_FAIL_STATUS = HttpStatus.BAD_REQUEST;

    private boolean result;
    private int code;
    private String message;
    private T data;
    private ApiCursorPaginationResponse page;

    public static ResponseEntity<ApiResponse<Void>> created() {
        return success(HttpStatus.CREATED);
    }

    public static ResponseEntity<ApiResponse<Void>> success() {
        return success(DEFAULT_SUCCESS_STATUS);
    }

    public static ResponseEntity<ApiResponse<Void>> fail() {
        return create(false, null, DEFAULT_FAIL_STATUS, null, null);
    }

    public static ResponseEntity<ApiResponse<Void>> success(HttpStatus status) {
        return success(status, (String)null);
    }
    public static ResponseEntity<ApiResponse<Void>> success(HttpStatus status, String message) {
        return create(true, message, status, null, null);
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(T data) {
        return success(data, null);
    }
    public static <T> ResponseEntity<ApiResponse<T>> success(T data, ApiCursorPaginationResponse page) {
        return create(true, null, DEFAULT_SUCCESS_STATUS, data, page);
    }

    public static <T> ResponseEntity<ApiResponse<T>> create(boolean result, String message, HttpStatus status, T data, ApiCursorPaginationResponse page) {
        return ResponseEntity.status(status).body(
                ApiResponse.<T>builder()
                        .result(result)
                        .code(status.value())
                        .message(Optional.ofNullable(message).orElse(status.getReasonPhrase()))
                        .data(data)
                        .page(page)
                        .build()
        );
    }

}
