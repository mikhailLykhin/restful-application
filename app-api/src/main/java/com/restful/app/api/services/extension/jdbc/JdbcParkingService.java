package com.restful.app.api.services.extension.jdbc;

import com.restful.app.api.dto.extension.ParkingDto;

import java.sql.SQLException;
import java.util.List;

public interface JdbcParkingService {

    void createParking(ParkingDto parkingDto) throws SQLException;

    void updateParking(long id, ParkingDto parkingDto) throws SQLException;

    List<ParkingDto> getAllParking() throws SQLException;

    ParkingDto getParking(long id) throws SQLException;

    void deleteParking(long id) throws SQLException;
}
