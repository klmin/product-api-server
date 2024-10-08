package com.product.product.controller;

import com.product.api.response.ApiResponse;
import com.product.api.response.pagination.CursorPaginationResponse;
import com.product.product.mapper.ProductMapper;
import com.product.product.request.ProductCreateRequest;
import com.product.product.request.ProductListRequest;
import com.product.product.request.ProductUpdateRequest;
import com.product.product.response.ProductGetResponse;
import com.product.product.response.ProductListResponse;
import com.product.product.service.ProductService;

import com.product.security.dto.SecurityDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductListResponse>>> list(@ModelAttribute @Valid ProductListRequest request,
                                                                        @AuthenticationPrincipal SecurityDto securityDto){

        CursorPaginationResponse<List<ProductListResponse>> response = productService.list(productMapper.toListDto(request, securityDto.getUserId()));
        return ApiResponse.success(response.getList(),response.toPaginationResponse());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductGetResponse>> get(@PathVariable Long productId, @AuthenticationPrincipal SecurityDto securityDto){
        ProductGetResponse productGetResponse = productService.findByProductIdAndUserUserId(productId, securityDto.getUserId(), ProductGetResponse.class);
        return ApiResponse.success(productGetResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> create(@RequestBody @Valid ProductCreateRequest request, @AuthenticationPrincipal SecurityDto securityDto){

        productService.create(productMapper.toCreateDto(request, securityDto.getUserId()));
        return ApiResponse.created();
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ApiResponse<Void>> update(@PathVariable Long productId, @RequestBody @Valid ProductUpdateRequest request,
                                                    @AuthenticationPrincipal SecurityDto securityDto){

        productService.update(productMapper.toUpdateDto(request, productId, securityDto.getUserId()));
        return ApiResponse.success();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long productId, @AuthenticationPrincipal SecurityDto securityDto){
        productService.delete(productId, securityDto.getUserId());
        return ApiResponse.success();
    }
    

}
