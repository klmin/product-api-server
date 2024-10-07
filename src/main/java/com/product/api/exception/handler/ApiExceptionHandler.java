package com.product.api.exception.handler;

import com.product.api.exception.ApiRuntimeException;
import com.product.api.response.ApiExceptionResponse;
import com.product.objectmapper.exception.JsonProcessingRuntimeException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.RequiredTypeException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import java.security.GeneralSecurityException;
import java.security.SignatureException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiExceptionResponse> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e) {
        log.error("## httpRequestMethodNotSupportedExceptionHandler ##");
        this.log(e);
        return ApiExceptionResponse.exception(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiExceptionResponse> httpMediaTypeNotSupportedExceptionHandler(HttpMediaTypeNotSupportedException e) {
        log.error("## httpMediaTypeNotSupportedExceptionHandler ##");
        this.log(e);
        return ApiExceptionResponse.exception(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(JsonProcessingRuntimeException.class)
    public ResponseEntity<ApiExceptionResponse> handlerJsonProcessingRuntimeException(JsonProcessingRuntimeException e) {
        log.error("## handlerJsonProcessingRuntimeException ##");
        this.log(e);
        return ApiExceptionResponse.exception(HttpStatus.BAD_REQUEST, "서버에서 오류가 발생하였습니다.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiExceptionResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("## methodArgumentNotValidExceptionHandler ##");
        this.log(e);

        List<Map<String, String>> list = e.getFieldErrors()
                .stream()
                .map(err -> Map.of(
                                "field", err.getField(),
                                "msg", Objects.requireNonNull(err.getDefaultMessage())
                        )
                )
                .toList();

        return ApiExceptionResponse.exception(HttpStatus.BAD_REQUEST, "필수값이 누락되었습니다.", list);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiExceptionResponse> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        log.error("## httpMessageNotReadableExceptionHandler ##");
        this.log(e);
        return ApiExceptionResponse.exception(HttpStatus.BAD_REQUEST, "필수값이 누락되었습니다.");
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ApiExceptionResponse> missingRequestHeaderExceptionHandler(MissingRequestHeaderException e) {
        log.error("## missingRequestHeaderExceptionHandler ##");
        this.log(e);
        return ApiExceptionResponse.exception(HttpStatus.BAD_REQUEST, "헤더가 잘못 입력되었습니다.");
    }

    @ExceptionHandler(ApiRuntimeException.class)
    public ResponseEntity<ApiExceptionResponse> apiExceptionHandler(ApiRuntimeException e) {
        log.error("## apiExceptionHandler ##");
        this.log(e);
        return ApiExceptionResponse.exception(e.getStatus(), e.getHttpCode(), e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiExceptionResponse> accessDeniedExceptionHandler(AccessDeniedException e) {
        log.error("## accessDeniedExceptionHandler ##");
        this.log(e);
        return ApiExceptionResponse.exception(HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiExceptionResponse> authenticationExceptionHandler(AuthenticationException e) {

        log.error("## authenticationExceptionHandler ##");
        this.log(e);

        return ApiExceptionResponse.exception(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiExceptionResponse> usernameNotFoundExceptionHandler(UsernameNotFoundException e) {

        log.error("## usernameNotFoundExceptionHandler ##");
        this.log(e);

        return ApiExceptionResponse.exception(HttpStatus.UNAUTHORIZED, "사용자 정보가 올바르지 않습니다.");
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ApiExceptionResponse> signatureExceptionHandler(SignatureException e) {
        log.error("## signatureExceptionHandler ##");
        this.log(e);
        return ApiExceptionResponse.exception(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다.");

    }
    @ExceptionHandler(RequiredTypeException.class)
    public ResponseEntity<ApiExceptionResponse> requiredTypeExceptionHandler(RequiredTypeException e) {
        log.error("## requiredTypeExceptionHandler ##");
        this.log(e);
        return ApiExceptionResponse.exception(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다.");

    }
    @ExceptionHandler(GeneralSecurityException.class)
    public ResponseEntity<ApiExceptionResponse> generalSecurityExceptionHandler(GeneralSecurityException e) {
        log.error("## generalSecurityExceptionHandler ##");
        this.log(e);
        return ApiExceptionResponse.exception(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다.");

    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ApiExceptionResponse> malformedJwtExceptionHandler(MalformedJwtException e) {
        log.error("## malformedJwtExceptionHandler ##");
        this.log(e);
        return ApiExceptionResponse.exception(HttpStatus.UNAUTHORIZED, "올바르지 않은 토큰입니다.");
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiExceptionResponse> expiredJwtExceptionHandler(ExpiredJwtException e) {
        log.error("## expiredJwtExceptionHandler ##");
        this.log(e);
        return ApiExceptionResponse.exception(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiExceptionResponse> exceptionHandler(Exception e) {
        log.error("## exceptionHandler ##");
        this.log(e);
        return ApiExceptionResponse.exception(HttpStatus.BAD_REQUEST);
    }

    private void log(Exception e){
        log.error("Exception Message : {}", e.getMessage());
    }
}
