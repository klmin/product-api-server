package com.product.user.mapper;

import com.product.user.dto.UserCreateDto;
import com.product.user.dto.UserUpdateDto;
import com.product.user.request.UserCreateRequest;
import com.product.user.request.UserUpdateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserCreateDto toCreateDto(UserCreateRequest request);

    UserUpdateDto toUpdateDto(UserUpdateRequest request);
}
