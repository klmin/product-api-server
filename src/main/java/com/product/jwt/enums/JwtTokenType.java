package com.product.jwt.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum JwtTokenType {

    ACCESS("access", "접근"),
    REFRESH("refresh", "재발급");

    private final String name;
    private final String description;

    public String getValue(){
        return this.toString();
    }

}
