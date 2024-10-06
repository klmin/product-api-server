package com.product.api.response;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiExceptionResponse {

    private ApiResponseMeta meta;
    private Object data;

    public static ResponseEntity<ApiExceptionResponse> exception(HttpStatus status) {
        return exception(status, status.value(), status.getReasonPhrase(), null);
    }

    public static ResponseEntity<ApiExceptionResponse> exception(HttpStatus status, String message) {
        return exception(status, status.value(), message, null);
    }

    public static ResponseEntity<ApiExceptionResponse> exception(HttpStatus status, String message, Object data) {
        return exception(status, status.value(), message, data);
    }

    public static ResponseEntity<ApiExceptionResponse> exception(HttpStatus status, int httpCode, String message) {
        return exception(status, httpCode, message, null);
    }

    private static ResponseEntity<ApiExceptionResponse> exception(HttpStatus status, int httpCode, String message, Object data) {
        return ResponseEntity.status(status).body(
                ApiExceptionResponse.builder()
                        .data(data)
                        .meta(ApiResponseMeta.create(httpCode, message))
                        .build()
        );
    }
}
