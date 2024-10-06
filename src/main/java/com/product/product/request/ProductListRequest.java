package com.product.product.request;

import com.product.product.dto.ProductListDTO;
import lombok.Builder;

@Builder
public record ProductListRequest(
        String searchKeyword,
        Long lastCursorId
) {

    public ProductListDTO toDTO(Long userId){
        return ProductListDTO.builder()
                .userId(userId)
                .searchKeyword(searchKeyword)
                .lastCursorId(lastCursorId)
                .build();
    }
}
