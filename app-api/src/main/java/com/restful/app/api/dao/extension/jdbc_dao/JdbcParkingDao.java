package com.restful.app.api.dao.extension.jdbc_dao;

import com.restful.app.extension_entity.Parking;
import com.restful.app.extension_entity.Person;

import java.util.List;

public interface JdbcParkingDao {

    void createParking(Parking parking);

    void updateParking(long id, Parking parking);

    List<Parking> getAllParkings();

    Parking getParking(long id);

    void deleteParking(long id);
}
