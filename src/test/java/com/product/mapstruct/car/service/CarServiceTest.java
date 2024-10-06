package com.product.mapstruct.car.service;

import com.product.mapstruct.car.dto.CarDTO;
import com.product.mapstruct.car.enums.EnumCarColor;
import com.product.auth.service.AuthenticationService;
import com.product.jwt.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class CarServiceTest {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private JwtService jwtService;

    @Test
    void test(){
        Long id = 1L;
        String name = "소나타";
        Integer price = 3000;
        List<String> options = List.of("Bluetooth", "BlackBox", "Aircon");
        EnumCarColor color = EnumCarColor.White;
        LocalDateTime regDttm = LocalDateTime.now();

        CarDTO carDTO = CarDTO.builder()
                .id(id)
                .name(name)
                .price(price)
                .options(options)
                .color(color)
                .regDttm(regDttm)
                .build();

        carDTO.toEntity();
    }

}