package com.product.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumUserType  {

    OWNER("카페관리자"),
    ADMIN("관리자");

    private final String name;


}
