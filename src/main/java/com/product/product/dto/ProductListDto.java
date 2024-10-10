package com.product.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductListDto {

    private String searchKeyword;
    private Integer limit;
    private Long lastCursorId;
}


