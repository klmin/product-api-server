package com.product.product.projection;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.product.product.enums.EnumProductCategory;
import com.product.product.enums.EnumProductSize;

import java.time.LocalDate;

public record ProductByUserIdProjection(

        Long productId,
        @JsonProperty("userId")
        Long userUserId,
        EnumProductCategory category,
        Integer price,
        Integer cost,
        String productName,
        String productNameInitials,
        String description,
        String barcode,
        LocalDate expirationDate,
        EnumProductSize size
) {
}
