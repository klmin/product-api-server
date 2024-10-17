package com.product.signup.controller;

import com.product.api.response.ApiResponse;
import com.product.signup.mapper.SignUpMapper;
import com.product.signup.request.SignUpCreateRequest;
import com.product.signup.service.SignUpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/signup")
public class SignUpController {

    private final SignUpService signUpService;
    private final SignUpMapper signUpMapper;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> signup(@RequestBody @Valid SignUpCreateRequest request)  {
        signUpService.signUp(signUpMapper.toCreateDto(request));
        return ApiResponse.created();
    }
}
