package com.restful.app.api.services.extension.sd;

import com.restful.app.api.dto.extension.ParkingDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SdParkingService {

    void createParking(ParkingDto parkingDto);

    void updateParking(long id, ParkingDto parkingDto);

    List<ParkingDto> getAllParking();

    List<ParkingDto> getAllParkingWithPagination(int page, int size);

    List<ParkingDto> findAllParkingJpql(float square);

    List<ParkingDto> findAllParkingWherePersonIdNative(long id, int page, int size);

    List<ParkingDto> findAllParkingWithPaginationNative(int page, int size);

    List<ParkingDto> findAllParkingByVehicleManufactureJpql(String manufacture);

    List<ParkingDto> findAllParkingWithVehicleAndPersonsJpql(int page, int size);

    ParkingDto getParking(long id);

    void deleteParking(long id);
}
