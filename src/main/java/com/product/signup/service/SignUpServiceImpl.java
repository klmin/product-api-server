package com.product.signup.service;

import com.product.signup.dto.SignUpCreateDto;
import com.product.signup.mapper.SignUpMapper;
import com.product.user.entity.User;
import com.product.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SignUpServiceImpl implements SignUpService {

    private final UserService userService;
    private final SignUpMapper signUpMapper;

    public User signUp(SignUpCreateDto dto){
        return userService.create(signUpMapper.toUserCreateDto(dto));
    }

}
