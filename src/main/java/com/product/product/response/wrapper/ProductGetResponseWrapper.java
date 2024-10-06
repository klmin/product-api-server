package com.product.product.response.wrapper;

import com.product.product.response.ProductGetResponse;

public record ProductGetResponseWrapper(

        ProductGetResponse product
) {

    public static ProductGetResponseWrapper wrapping(ProductGetResponse product){
        return new ProductGetResponseWrapper(product);
    }
}
