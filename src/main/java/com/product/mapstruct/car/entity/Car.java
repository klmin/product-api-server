package com.product.mapstruct.car.entity;

import com.product.mapstruct.car.enums.EnumCarColor;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter

@ToString
public class Car {
    private Long id;
    private String name;
    private Integer price;
    private String description;
    private List<String> options;
    private EnumCarColor color;
    private LocalDateTime regDttm;
    private String status;
    private String tempVehicleId;
    private String stringDate;
    private Long companyId;
    private String companyName;
    private Reservation reservation;
    private String notes;
    private String testParameter;
}
