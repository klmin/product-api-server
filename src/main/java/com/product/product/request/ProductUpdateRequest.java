package com.product.product.request;

import com.product.product.dto.ProductUpdateDto;
import com.product.product.enums.EnumProductCategory;
import com.product.product.enums.EnumProductSize;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ProductUpdateRequest(

        EnumProductCategory category,

        Integer price,

        Integer cost,

        String productName,

        String description,

        String barcode,

        LocalDate expirationDate,

        EnumProductSize size
) {

    public ProductUpdateDto toDTO(Long productId, Long userId) {
        return ProductUpdateDto.builder()
                .userId(userId)
                .productId(productId)
                .category(category)
                .price(price)
                .cost(cost)
                .productName(productName)
                .description(description)
                .barcode(barcode)
                .expirationDate(expirationDate)
                .size(size)
                .build();

    }
}
