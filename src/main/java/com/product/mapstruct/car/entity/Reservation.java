package com.product.mapstruct.car.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class Reservation {
    private String name;
    private String mobile;
}
