package com.restful.app.api.services.extension.jdbc;

import com.restful.app.api.dto.extension.ParkingDto;

import java.util.List;

public interface JdbcParkingService {

    void createParking(ParkingDto parkingDto);

    void updateParking(long id, ParkingDto parkingDto);

    List<ParkingDto> getAllParking();

    ParkingDto getParking(long id);

    void deleteParking(long id);
}
