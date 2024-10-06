package com.product.mapstruct.car.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CompanyDTO {

    private Long companyId;
    private String name;
}
