package com.product.product.mapper;

import com.product.product.dto.ProductCreateDTO;
import com.product.product.dto.ProductListDTO;
import com.product.product.dto.ProductUpdateDTO;
import com.product.product.entity.Product;
import com.product.product.query.ProductListQuery;
import com.product.product.request.ProductCreateRequest;
import com.product.product.request.ProductListRequest;
import com.product.product.request.ProductUpdateRequest;
import com.product.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductListDTO toListDto(ProductListRequest request, Long userId);
    ProductListQuery toListQuery(ProductListDTO dto);

    ProductCreateDTO toCreateDto(ProductCreateRequest request, Long userId);
    @Mapping(target="description", source="dto.description")
    Product toCreateEntity(ProductCreateDTO dto, User user, String productNameInitials);

    ProductUpdateDTO toUpdateDto(ProductUpdateRequest request, Long productId, Long userId);


}
