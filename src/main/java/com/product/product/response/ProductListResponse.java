package com.product.product.response;

import com.product.product.enums.EnumProductCategory;
import com.product.product.enums.EnumProductSize;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ProductListResponse(
        Long productId,
        EnumProductCategory category,
        Integer price,
        Integer cost,
        String productName,
        String description,
        String barcode,
        EnumProductSize size,
        LocalDateTime regDttm
) {

}
