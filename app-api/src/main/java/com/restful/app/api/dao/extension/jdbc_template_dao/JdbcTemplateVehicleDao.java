package com.restful.app.api.dao.extension.jdbc_template_dao;

import com.restful.app.extension_entity.Vehicle;

import java.util.List;

public interface JdbcTemplateVehicleDao {

    void createVehicle(Vehicle vehicle);

    void updateVehicle(long id, Vehicle vehicle);

    List<Vehicle> getAllVehicles();

    Vehicle getVehicle(long id);

    void deleteVehicle(long id);
}
