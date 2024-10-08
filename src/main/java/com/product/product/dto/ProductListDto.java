package com.product.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductListDto {

    private Long userId;
    private String searchKeyword;
    @Builder.Default
    private Integer limit = 10;
    private Long lastCursorId;
}


