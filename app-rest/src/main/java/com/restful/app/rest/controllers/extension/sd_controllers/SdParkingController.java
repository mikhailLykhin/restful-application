package com.restful.app.rest.controllers.extension.sd_controllers;

import com.restful.app.api.dto.extension.ParkingDto;
import com.restful.app.api.services.extension.sd.SdParkingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sd")
public class SdParkingController {

    private final SdParkingService parkingService;

    public SdParkingController(SdParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @PostMapping("/create/parking")
    public ResponseEntity<HttpStatus> createParking(@RequestBody ParkingDto parkingDto) {
        parkingService.createParking(parkingDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update/parking/{id}")
    public ResponseEntity<HttpStatus> updateParking(@PathVariable("id") int id, ParkingDto parkingDto) {
        parkingService.updateParking(id, parkingDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/parking/{id}")
    public ParkingDto getParking(@PathVariable("id") long id) {
        return parkingService.getParking(id);
    }

    @GetMapping("/parking")
    public List<ParkingDto> getAllParking() {
        return parkingService.getAllParking();
    }

    @GetMapping("/parking/s")
    public List<ParkingDto> getAllParkingWithPagination(@RequestParam("page") int page,
                                                        @RequestParam("size") int size) {
        return parkingService.getAllParkingWithPagination(page, size);
    }

    @GetMapping("/parking/query/alljpql")
    public List<ParkingDto> findAllParkingJpql(@RequestParam("square") float square) {
        return parkingService.findAllParkingJpql(square);
    }

    @GetMapping("/parking/query/alljpql/{id}")
    public List<ParkingDto> findAllParkingWherePersonIdNative(@PathVariable("id") long id,
                                                              @RequestParam("page") int page,
                                                              @RequestParam("size") int size) {
        return parkingService.findAllParkingWherePersonIdNative(id, page, size);
    }

    @GetMapping("/parking/query/allnative")
    public List<ParkingDto> findAllParkingWithPaginationNative( @RequestParam("page") int page,
                                                                @RequestParam("size") int size) {
        return parkingService.findAllParkingWithPaginationNative(page, size);
    }

    @GetMapping("/parking/query/alljpql/order/graph")
    public List<ParkingDto> findAllParkingByVehicleManufactureJpql(@RequestParam("manufacture") String manufacture) {
        return parkingService.findAllParkingByVehicleManufactureJpql(manufacture);
    }

    @GetMapping("/parking/query/alljpql/join/graph")
    public List<ParkingDto> findAllParkingWithVehicleAndPersonsJpql( @RequestParam("page") int page,
                                                                     @RequestParam("size") int size) {
        return parkingService.findAllParkingWithVehicleAndPersonsJpql(page, size);
    }


    @DeleteMapping("/parking/{id}")
    public ResponseEntity<HttpStatus> deleteParking(@PathVariable("id") int id) {
        parkingService.deleteParking(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
