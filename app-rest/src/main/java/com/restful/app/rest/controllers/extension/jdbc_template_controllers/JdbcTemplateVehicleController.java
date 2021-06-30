package com.restful.app.rest.controllers.extension.jdbc_template_controllers;

import com.restful.app.api.dto.extension.VehicleDto;
import com.restful.app.api.services.extension.jdbc_template.JdbcTemplateVehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/template")
public class JdbcTemplateVehicleController {

    private final JdbcTemplateVehicleService vehicleService;

    public JdbcTemplateVehicleController(JdbcTemplateVehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/create/vehicle")
    public ResponseEntity<HttpStatus> createEngine(@RequestBody VehicleDto vehicleDto) {
        vehicleService.createVehicle(vehicleDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update/vehicles/{id}")
    public ResponseEntity<HttpStatus> updateEngine(@PathVariable("id") int id, VehicleDto vehicleDto) {
        vehicleService.updateVehicle(id, vehicleDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/vehicles")
    public List<VehicleDto> getAllEngines() {
        return vehicleService.getAllVehicle();
    }

    @GetMapping("/vehicles/{id}")
    public VehicleDto getEngine(@PathVariable("id") long id) {
        return vehicleService.getVehicle(id);
    }

    @DeleteMapping("/vehicles/{id}")
    public ResponseEntity<HttpStatus> deleteEngine(@PathVariable("id") int id) {
        vehicleService.deleteVehicle(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
