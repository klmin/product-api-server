package com.product.product.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumProductSize {

    SMALL("소"),
    LARGE("대")
    ;
    
    private final String name;
}
