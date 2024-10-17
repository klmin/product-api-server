package com.product.signup.mapper;

import com.product.signup.dto.SignUpCreateDto;
import com.product.signup.request.SignUpCreateRequest;
import com.product.user.dto.UserCreateDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SignUpMapper {

    SignUpCreateDto toCreateDto(SignUpCreateRequest request);
    UserCreateDto toUserCreateDto(SignUpCreateDto dto);
}
