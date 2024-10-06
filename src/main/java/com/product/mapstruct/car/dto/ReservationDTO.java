package com.product.mapstruct.car.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReservationDTO {
    private String reservationName;
    private String reservationMobile;
}
