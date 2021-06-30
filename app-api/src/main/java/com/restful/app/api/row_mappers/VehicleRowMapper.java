package com.restful.app.api.row_mappers;

import com.restful.app.extension_entity.Engine;
import com.restful.app.extension_entity.Parking;
import com.restful.app.extension_entity.Vehicle;
import com.restful.app.extension_enum.EngineType;
import com.restful.app.extension_enum.VehicleType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class VehicleRowMapper implements RowMapper<List<Vehicle>> {
    @Override
    public List<Vehicle> mapRow(ResultSet resultSet, int i) throws SQLException {
        Map<Long, Vehicle> vehicles = new HashMap<>();
        Map<Long, Engine> engines = new HashMap<>();
        do {
            Long vehId = resultSet.getLong("vehId");
            String vehType = resultSet.getString("vehType");
            String vehManufacture = resultSet.getString("vehManufacture");
            String vehModel = resultSet.getString("vehModel");
            Long engId = resultSet.getLong("engId");
            String engNumber = resultSet.getString("engNumber");
            String engType = resultSet.getString("engType");
            float engVolume = resultSet.getFloat("engVolume");
            Long pId = resultSet.getLong("pId");
            float pSquare = resultSet.getFloat("pSquare");
            String pAddress = resultSet.getString("pAddress");
            Vehicle vehicle = vehicles.get(vehId);
            Engine engine = engines.get(engId);
            if (vehicle == null && engine == null) {
                engine = Engine.builder().id(engId).number(engNumber).type(EngineType.valueOf(engType)).volume(engVolume).build();
                vehicle = Vehicle.builder().id(vehId).type(VehicleType.valueOf(vehType)).manufacture(vehManufacture).model(vehModel).engine(engine).parkings(new HashSet<>()).build();
                vehicles.put(vehicle.getId(), vehicle);

            }
            Parking parking = Parking.builder().id(pId).square(pSquare).address(pAddress).build();
            assert vehicle != null;
            vehicle.getParkings().add(parking);

        } while (resultSet.next());
        Collection<Vehicle> vehicleList = vehicles.values();
        return new ArrayList<>(vehicleList);
    }
}
