package com.restful.app.api.services.extension.sd;

import com.restful.app.api.dto.extension.VehicleDto;

import java.util.List;

public interface SdVehicleService {

    void createVehicle(VehicleDto vehicleDto);

    void updateVehicle(long id, VehicleDto vehicleDto);

    List<VehicleDto> getAllVehicle();

    List<VehicleDto> getAllVehiclesWithPagination(int page, int size);

    List<VehicleDto> getAllWithSpecificationSearch(String search);

    List<VehicleDto> getWithSimpleSpecification();


    VehicleDto getVehicle(long id);

    void deleteVehicle(long id);
}
