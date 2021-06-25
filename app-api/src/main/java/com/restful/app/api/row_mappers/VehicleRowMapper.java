package com.restful.app.api.row_mappers;

import com.restful.app.extension_entity.Vehicle;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VehicleRowMapper implements RowMapper<Vehicle> {
    @Override
    public Vehicle mapRow(ResultSet resultSet, int i) throws SQLException {
        return null;
    }
}
