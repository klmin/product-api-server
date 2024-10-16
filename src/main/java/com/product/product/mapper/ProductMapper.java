package com.product.product.mapper;

import com.product.product.data.ProductListData;
import com.product.product.dto.ProductCreateDto;
import com.product.product.dto.ProductListDto;
import com.product.product.dto.ProductUpdateDto;
import com.product.product.entity.Product;
import com.product.product.query.ProductListQuery;
import com.product.product.request.ProductCreateRequest;
import com.product.product.request.ProductListRequest;
import com.product.product.request.ProductUpdateRequest;
import com.product.product.response.ProductResponse;
import com.product.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target="limit", defaultValue = "10")
    ProductListDto toListDto(ProductListRequest request);
    ProductListQuery toListQuery(ProductListDto dto);
    List<ProductResponse> toListResponse(List<ProductListData> dataList);

    ProductCreateDto toCreateDto(ProductCreateRequest request, Long userId);
    @Mapping(target="description", source="dto.description")
    Product toCreateEntity(ProductCreateDto dto, User user, String productNameInitials);

    ProductUpdateDto toUpdateDto(ProductUpdateRequest request, Long productId, Long userId);


}
