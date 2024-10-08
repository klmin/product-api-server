package com.product.product.request;

import com.product.product.dto.ProductListDto;
import lombok.Builder;

@Builder
public record ProductListRequest(
        String searchKeyword,
        Long lastCursorId
) {

    public ProductListDto toDTO(Long userId){
        return ProductListDto.builder()
                .userId(userId)
                .searchKeyword(searchKeyword)
                .lastCursorId(lastCursorId)
                .build();
    }
}
