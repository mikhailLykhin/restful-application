package com.restful.app.api.dao.extension.jdbc_template_dao;

import com.restful.app.extension_entity.Parking;

import java.util.List;

public interface JdbcTemplateParkingDao {

    void createParking(Parking parking);

    void updateParking(long id, Parking parking);

    List<Parking> getAllParkings();

    Parking getParking(long id);

    void deleteParking(long id);
}
