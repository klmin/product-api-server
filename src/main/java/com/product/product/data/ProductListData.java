package com.product.product.data;

import com.product.product.enums.EnumProductCategory;
import com.product.product.enums.EnumProductSize;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;

@Getter
public class ProductListData{

    private Long productId;
    private EnumProductCategory category;
    private Integer price;
    private Integer cost;
    private String productName;
    private String description;
    private String barcode;
    private EnumProductSize size;
    private LocalDateTime regDttm;

    @QueryProjection
    public ProductListData(Long productId, EnumProductCategory category, Integer price, Integer cost,
                           String productName, String description, String barcode, EnumProductSize size,
                           LocalDateTime regDttm) {
        this.productId = productId;
        this.category = category;
        this.price = price;
        this.cost = cost;
        this.productName = productName;
        this.description = description;
        this.barcode = barcode;
        this.size = size;
        this.regDttm = regDttm;
    }

}
