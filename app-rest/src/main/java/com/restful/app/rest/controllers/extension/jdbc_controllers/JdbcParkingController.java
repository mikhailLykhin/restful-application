package com.restful.app.rest.controllers.extension.jdbc_controllers;

import com.restful.app.api.dto.extension.ParkingDto;
import com.restful.app.api.services.extension.jdbc.JdbcParkingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/jdbc")
public class JdbcParkingController {

    private final JdbcParkingService parkingService;

    public JdbcParkingController(JdbcParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @PostMapping("/create/parking")
    public ResponseEntity<HttpStatus> createParking(@RequestBody ParkingDto parkingDto) throws SQLException {
        parkingService.createParking(parkingDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update/parking/{id}")
    public ResponseEntity<HttpStatus> updateParking(@PathVariable("id") int id, ParkingDto parkingDto) throws SQLException {
        parkingService.updateParking(id, parkingDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/parking/{id}")
    public ParkingDto getParking(@PathVariable("id") long id) throws SQLException {
        return parkingService.getParking(id);
    }

    @GetMapping("/parking")
    public List<ParkingDto> getAllParking() throws SQLException {
        return parkingService.getAllParking();
    }

    @DeleteMapping("/parking/{id}")
    public ResponseEntity<HttpStatus> deleteParking(@PathVariable("id") int id) throws SQLException {
        parkingService.deleteParking(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
