package com.product.product.request;

import com.product.product.dto.ProductCreateDto;
import com.product.product.enums.EnumProductCategory;
import com.product.product.enums.EnumProductSize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ProductCreateRequest(

        @NotNull
        EnumProductCategory category,

        @NotNull
        Integer price,

        @NotNull
        Integer cost,

        @NotBlank
        String productName,

        @NotBlank
        String description,

        @NotBlank
        String barcode,

        @NotNull
        LocalDate expirationDate,

        @NotNull
        EnumProductSize size


) {

        public ProductCreateDto toDTO(Long userId) {
                return ProductCreateDto.builder()
                        .userId(userId)
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
