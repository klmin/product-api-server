package com.product.mapstruct.car.service;

import com.product.auth.service.AuthenticationService;
import com.product.jwt.service.JwtService;
import com.product.mapstruct.car.dto.CarDTO;
import com.product.mapstruct.car.dto.CompanyDTO;
import com.product.mapstruct.car.dto.ReservationDTO;
import com.product.mapstruct.car.entity.Car;
import com.product.mapstruct.car.enums.EnumCarColor;
import com.product.mapstruct.car.mapper.CarMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class CarServiceTest {

    @Autowired
    CarService carService;

    @Autowired
    CarMapper carMapper;

    CarDTO carDTO;
    CompanyDTO companyDTO;
    ReservationDTO reservationDTO;


    @BeforeEach
    void setUp() {

        Long id = 1L;
        String name = "소나타";
        Integer price = 3000;
        List<String> options = List.of("Bluetooth", "BlackBox", "Aircon");
        EnumCarColor color = EnumCarColor.White;
        LocalDateTime regDttm = LocalDateTime.now();

        carDTO = CarDTO.builder().id(id).name(name).price(price).options(options).color(color).regDttm(regDttm).build();
        companyDTO = CompanyDTO.builder().companyId(3L).name("현대").build();
        reservationDTO = ReservationDTO.builder().reservationName("홍길동").reservationMobile("01012345678").build();
    }

    @Test
    void mapStruct(){


        //Car car = carMapper.toEntity(carDTO, companyDTO, reservationDTO, "테스트메모");
        Car car = carMapper.toEntity(carDTO,  companyDTO, reservationDTO, "테스트메모", "테스트파라미터");
        System.out.println("car : "+car);

    }

    @Test
    void to(){
        Car car = carDTO.toEntity();
    }




}