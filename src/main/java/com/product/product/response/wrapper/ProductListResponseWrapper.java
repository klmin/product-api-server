package com.product.product.response.wrapper;

import com.product.api.response.pagination.ApiCursorPaginationResponse;
import com.product.product.response.ProductListResponse;

import java.util.List;

public record ProductListResponseWrapper(

        List<ProductListResponse> products,
        ApiCursorPaginationResponse pagination
) {

    public static ProductListResponseWrapper wrapWithPagination(List<ProductListResponse> products, ApiCursorPaginationResponse pagination){
        return new ProductListResponseWrapper(products, pagination);
    }
}
