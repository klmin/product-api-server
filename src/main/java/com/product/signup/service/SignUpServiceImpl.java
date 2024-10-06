package com.product.signup.service;

import com.product.signup.dto.SignUpCreateDTO;
import com.product.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SignUpServiceImpl implements SignUpService {

    private final UserService userService;

    public void signUp(SignUpCreateDTO dto){
        userService.create(dto.toUserCreateDTO());
    }

}
