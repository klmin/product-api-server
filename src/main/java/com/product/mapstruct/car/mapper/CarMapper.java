package com.product.mapstruct.car.mapper;

import com.product.mapstruct.car.dto.CarDTO;
import com.product.mapstruct.car.dto.CompanyDTO;
import com.product.mapstruct.car.dto.ReservationDTO;
import com.product.mapstruct.car.entity.Car;
import com.product.mapstruct.car.enums.EnumCarColor;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


@Mapper(componentModel = "spring", imports = {UUID.class, EnumCarColor.class})
public interface CarMapper {

    @Mapping(target = "tempVehicleId", defaultExpression = "java(UUID.randomUUID().toString())")
    @Mapping(target = "description", expression = "java(buildDescription(carDTO))")
    @Mapping(target = "status", defaultValue = "NORMAL")
    @Mapping(target = "name", source="carDTO.name")
    @Mapping(target = "color", defaultExpression = "java(EnumCarColor.BLUE)")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "stringDate", source="carDTO.regDttm", qualifiedByName = "localDateTimeToString")
    @Mapping(target = "companyName", source="companyDTO.name")
    @Mapping(target = "notes", source="note")
    @Mapping(target = "reservation.name", source="reservationDTO.reservationName")
    @Mapping(target = "reservation.mobile", source="reservationDTO.reservationMobile")
    Car toEntity(CarDTO carDTO, CompanyDTO companyDTO, ReservationDTO reservationDTO, String note, String testParameter);
   // Car toEntity(CarDTO carDTO, CompanyDTO companyDTO, ReservationDTO reservationDTO, String note);
    CarDTO toDTO(Car car);

    default String buildDescription(CarDTO carDTO) {
        return carDTO.getName() + ", "+ carDTO.getPrice() +"원";
    }

    @Named("localDateTimeToString")
    default String localDateTimeToString(LocalDateTime localDateTime) {
        return localDateTime.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
