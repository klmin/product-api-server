package com.product.product.mapper;

import com.product.mapstruct.mapper.RequestMapper;
import com.product.product.dto.ProductCreateDTO;
import com.product.product.request.ProductCreateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductCreateRequestMapper extends RequestMapper<ProductCreateRequest, ProductCreateDTO> {
}
