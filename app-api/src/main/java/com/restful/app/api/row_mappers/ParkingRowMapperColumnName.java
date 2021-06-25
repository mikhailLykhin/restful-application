package com.restful.app.api.row_mappers;

import com.restful.app.extension_entity.Parking;
import com.restful.app.extension_entity.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ParkingRowMapperColumnName implements RowMapper<Parking> {
    @Override
    public Parking mapRow(ResultSet resultSet, int i) throws SQLException {
        Person person = new Person();
        person.setId(resultSet.getLong("persId"));
        person.setName(resultSet.getString("persName"));
        person.setAge(resultSet.getInt("persAge"));
        person.setEmail(resultSet.getString("persEmail"));
        Parking parking = new Parking();
        parking.setId(resultSet.getLong("parId"));
        parking.setSquare(resultSet.getFloat("parSquare"));
        parking.setAddress(resultSet.getString("parAddress"));
        parking.setPerson(person);
        return parking;
    }
}
