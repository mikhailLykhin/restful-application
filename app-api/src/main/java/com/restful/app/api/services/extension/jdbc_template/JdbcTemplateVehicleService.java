package com.restful.app.api.services.extension.jdbc_template;

import com.restful.app.api.dto.extension.VehicleDto;

import java.util.List;

public interface JdbcTemplateVehicleService {

    void createVehicle(VehicleDto vehicleDto);

    void updateVehicle(long id, VehicleDto vehicleDto);

    List<VehicleDto> getAllVehicle();

    VehicleDto getVehicle(long id);

    void deleteVehicle(long id);
}
