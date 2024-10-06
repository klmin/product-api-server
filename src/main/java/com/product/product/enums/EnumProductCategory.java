package com.product.product.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumProductCategory {

    COFFEE("커피"),
    TEA("차"),
    DESSERT("디저트"),
    BAKERY("빵"),
    ;

    private final String name;
}
