package com.restful.app.api.row_mappers;

import com.restful.app.extension_entity.Parking;
import com.restful.app.extension_entity.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ParkingRowMapperColumnIndex implements RowMapper<Parking> {

    @Override
    public Parking mapRow(ResultSet resultSet, int i) throws SQLException {
        Parking parking = new Parking();
        parking.setId(resultSet.getLong(1));
        parking.setSquare(resultSet.getFloat(2));
        parking.setAddress(resultSet.getString(3));
        Person person = new Person();
        person.setId(resultSet.getLong(5));
        person.setName(resultSet.getString(6));
        person.setAge(resultSet.getInt(7));
        person.setEmail(resultSet.getString(8));
        parking.setPerson(person);
        return parking;
    }
}
