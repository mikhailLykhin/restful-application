package com.restful.app.api.dao.extension.jdbc_dao;

import com.restful.app.extension_entity.Parking;

import java.sql.SQLException;
import java.util.List;

public interface JdbcParkingDao {

    void createParking(Parking parking) throws SQLException;

    void updateParking(long id, Parking parking) throws SQLException;

    List<Parking> getAllParkings() throws SQLException;

    Parking getParking(long id) throws SQLException;

    void deleteParking(long id) throws SQLException;
}
