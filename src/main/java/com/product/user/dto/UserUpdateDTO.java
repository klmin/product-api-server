package com.product.user.dto;


import com.product.user.enums.EnumUserType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserUpdateDTO {

    private Long userId;
    private String userName;
    private EnumUserType userType;
    private String email;
    private String mobile;
    private String description;



}
