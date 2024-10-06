package com.product.mapstruct.car.dto;

import com.product.mapstruct.car.entity.Car;
import com.product.mapstruct.car.enums.EnumCarColor;
import lombok.*;

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
    private String status;
    private String tempVehicleId;

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
