package com.product.product.query;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductListQuery {

    private Long userId;
    private String searchKeyword;
    private Integer limit;
    private Long lastCursorId;
}
