package com.product.product.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.product.product.enums.EnumProductCategory;
import com.product.product.enums.EnumProductSize;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ProductGetResponse(
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
        EnumProductSize size,
        LocalDateTime regDttm

) {
}
