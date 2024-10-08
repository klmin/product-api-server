package com.product.signup.service;

import com.product.signup.dto.SignUpCreateDto;
import com.product.user.entity.User;

public interface SignUpService {

    User signUp(SignUpCreateDto dto);
}
