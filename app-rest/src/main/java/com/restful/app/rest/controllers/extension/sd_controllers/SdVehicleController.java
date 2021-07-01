package com.restful.app.rest.controllers.extension.sd_controllers;

import com.restful.app.api.dto.extension.VehicleDto;
import com.restful.app.api.services.extension.sd.SdVehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sd")
public class SdVehicleController {

    private final SdVehicleService vehicleService;

    public SdVehicleController(SdVehicleService vehicleService) {
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

    @GetMapping("/vehicles/s")
    public List<VehicleDto> getAllEnginesWithPagination(@RequestParam("page") int page,
                                                       @RequestParam("size") int size) {
        return vehicleService.getAllVehiclesWithPagination(page, size);
    }

    @GetMapping("/vehicles/spec")
    public List<VehicleDto> getAllWithSpecificationSearch(@RequestParam("search") String search) {
        return vehicleService.getAllWithSpecificationSearch(search);
    }

    @GetMapping("/vehicles/simple")
    public List<VehicleDto> getWithSimpleSpecification() {
        return vehicleService.getWithSimpleSpecification();
    }

    @DeleteMapping("/vehicles/{id}")
    public ResponseEntity<HttpStatus> deleteEngine(@PathVariable("id") int id) {
        vehicleService.deleteVehicle(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
