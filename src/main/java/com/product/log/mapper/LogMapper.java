package com.product.log.mapper;

import com.product.log.dto.ApiLogCreateDTO;
import com.product.log.entity.ApiLog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LogMapper {
    ApiLog toCreateEntity(ApiLogCreateDTO dto);
}
