package com.product.product.request;

import lombok.Builder;

@Builder
public record ProductListRequest(
        String searchKeyword,
        Long lastCursorId,
        Integer limit
) {

}
