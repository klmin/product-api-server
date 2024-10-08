package com.product.product.service;

import com.product.api.response.pagination.CursorPaginationResponse;
import com.product.product.dto.ProductCreateDto;
import com.product.product.dto.ProductListDto;
import com.product.product.dto.ProductUpdateDto;
import com.product.product.entity.Product;
import com.product.product.response.ProductListResponse;

import java.util.List;

public interface ProductService {

    CursorPaginationResponse<List<ProductListResponse>> list(ProductListDto dto);
    <T> T findByProductId(Long productId, Class<T> clazz);
    <T> T findByProductIdAndUserUserId(Long productId, Long userId, Class<T> clazz);
    <T> List<T> findByUserId(Long userId, Class<T> clazz);
    Product create(ProductCreateDto dto);
    Product update(ProductUpdateDto dto);
    void delete(Long productId, Long userId);
}
