package com.mapstruct.car.entity;

import com.mapstruct.car.enums.EnumCarColor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class Car {
    private Long id;
    private String name;
    private Integer price;
    private List<String> options;
    private EnumCarColor color;
    private LocalDateTime regDttm;
}
