package com.product.product.dto;

import com.product.product.enums.EnumProductCategory;
import com.product.product.enums.EnumProductSize;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ProductUpdateDTO {

    private Long userId;
    private Long productId;
    private EnumProductCategory category;
    private Integer price;
    private Integer cost;
    private String productName;
    private String description;
    private String barcode;
    private LocalDate expirationDate;
    private EnumProductSize size;
}
