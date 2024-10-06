package com.product.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumUserStatus  {

    ACTIVE("정상"),
    DELETED("삭제")
    ;

    private final String name;

}
