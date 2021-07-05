package com.restful.app.api.row_mappers;

import com.restful.app.extension_entity.Engine;
import com.restful.app.extension_entity.Parking;
import com.restful.app.extension_entity.Vehicle;
import com.restful.app.extension_enum.EngineType;
import com.restful.app.extension_enum.VehicleType;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class VehicleExtractData implements ResultSetExtractor<List<Vehicle>> {

    @Override
    public List<Vehicle> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Long, Vehicle> vehicles = new HashMap<>();
        Map<Long, Engine> engines = new HashMap<>();
        Map<Long, Parking> parkings = new HashMap<>();
        while (resultSet.next()) {
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
            Engine engine = engines.get(engId);
            Parking parking = parkings.get(pId);
            Vehicle vehicle = vehicles.get(vehId);
            if (engine == null) {
                engine = Engine.builder().id(engId).number(engNumber).type(EngineType.valueOf(engType)).volume(engVolume).build();
                engines.put(engine.getId(), engine);
            }
            if(parking == null) {
                parking = Parking.builder().id(pId).square(pSquare).address(pAddress).build();
                parkings.put(parking.getId(), parking);
            }
            if (vehicle == null) {
                vehicle = Vehicle.builder().id(vehId).type(VehicleType.valueOf(vehType)).manufacture(vehManufacture).model(vehModel).engine(engine).parkings(new HashSet<>()).build();
                vehicles.put(vehicle.getId(), vehicle);
            }
            vehicle.getParkings().add(parking);
        }
        Collection<Vehicle> vehicleList = vehicles.values();
        return new ArrayList<>(vehicleList);
    }
}
