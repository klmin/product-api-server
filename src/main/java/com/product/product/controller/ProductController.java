package com.product.product.controller;

import com.product.api.response.ApiResponse;
import com.product.api.response.pagination.CursorPaginationResponse;
import com.product.product.request.ProductCreateRequest;
import com.product.product.request.ProductListRequest;
import com.product.product.request.ProductUpdateRequest;
import com.product.product.response.ProductGetResponse;
import com.product.product.response.ProductListResponse;
import com.product.product.response.wrapper.ProductGetResponseWrapper;
import com.product.product.response.wrapper.ProductListResponseWrapper;
import com.product.product.service.ProductService;
import com.product.security.dto.SecurityDTO;
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

    @GetMapping
    public ResponseEntity<ApiResponse<ProductListResponseWrapper>> list(@ModelAttribute @Valid ProductListRequest request,
                                                                        @AuthenticationPrincipal SecurityDTO securityDTO){

        CursorPaginationResponse<List<ProductListResponse>> response = productService.list(request.toDTO(securityDTO.getUserId()));
        return ApiResponse.success(ProductListResponseWrapper.wrapWithPagination(response.getList(),
                response.toPaginationResponse()));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductGetResponseWrapper>> get(@PathVariable Long productId, @AuthenticationPrincipal SecurityDTO securityDTO){
        ProductGetResponse productGetResponse = productService.findByProductIdAndUserUserId(productId, securityDTO.getUserId(), ProductGetResponse.class);

        return ApiResponse.success(ProductGetResponseWrapper.wrapping(productGetResponse));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> create(@RequestBody @Valid ProductCreateRequest request, @AuthenticationPrincipal SecurityDTO securityDTO){
        productService.create(request.toDTO(securityDTO.getUserId()));
        return ApiResponse.created();
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ApiResponse<Void>> update(@PathVariable Long productId, @RequestBody @Valid ProductUpdateRequest request,
                                                    @AuthenticationPrincipal SecurityDTO securityDTO){

        productService.update(request.toDTO(productId, securityDTO.getUserId()));
        return ApiResponse.success();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long productId, @AuthenticationPrincipal SecurityDTO securityDTO){
        productService.delete(productId, securityDTO.getUserId());
        return ApiResponse.success();
    }
    

}
