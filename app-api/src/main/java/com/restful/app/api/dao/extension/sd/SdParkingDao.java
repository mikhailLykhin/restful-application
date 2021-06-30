package com.restful.app.api.dao.extension.sd;

import com.restful.app.extension_entity.Parking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface SdParkingDao extends JpaRepository<Parking, Long> {

    @EntityGraph(value = "parking-graph-with-vehicle-person-subgraph-engine")
    @Query(value = "SELECT p FROM Parking p WHERE p.square > ?1")
    List<Parking> findAllParkingJpql(float square, Sort sort);


    @Query(value = "SELECT * from parking p where p.person_id = ?1",
            countQuery = "SELECT count(*) FROM parking",
    nativeQuery = true)
    Page<Parking> findAllParkingWherePersonIdNative(long id, Pageable pageable);

    @Query(value = "SELECT * FROM parking ORDER BY square",
            countQuery = "SELECT count(*) FROM parking",
            nativeQuery = true)
    Page<Parking> findAllParkingWithPaginationNative(Pageable pageable);

    @EntityGraph(value = "parking-graph-with-vehicle")
    @Query("SELECT p FROM Parking p join p.vehicles v where v.manufacture = :manufacture")
    List<Parking> findAllParkingByVehicleManufactureJpql(@Param("manufacture") String manufacture);

    @EntityGraph(attributePaths = {"vehicles", "person"})
    @Query("SELECT p FROM Parking p join p.vehicles v join p.person per where per.age > 20")
    Page<Parking> findAllParkingWithVehicleAndPersonsJpql(Pageable pageable);

}
