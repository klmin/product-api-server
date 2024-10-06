package com.mapstruct.car.dto;

import com.mapstruct.car.entity.Car;
import com.mapstruct.car.enums.EnumCarColor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class CarDTO {
    private Long id;
    private String name;
    private Integer price;
    private List<String> options;
    private EnumCarColor color;
    private LocalDateTime regDttm;

    public Car toEntity(){
        return Car.builder()
                .id(id)
                .name(name)
                .price(price)
                .options(options)
                .color(color)
                .regDttm(regDttm)
                .build();
    }
}
