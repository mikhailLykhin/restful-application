package com.restful.app.rest.controllers.extension.sd_controllers;

import com.restful.app.api.dao.extension.sd.projections.EngineView;
import com.restful.app.api.dto.extension.EngineDto;
import com.restful.app.api.services.extension.sd.SdEngineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sd")
public class SdEngineController {

    private final SdEngineService engineService;

    public SdEngineController(SdEngineService engineService) {
        this.engineService = engineService;
    }

    @PostMapping("/create/engine")
    public ResponseEntity<HttpStatus> createEngine(@RequestBody EngineDto engineDto) {
        engineService.createEngine(engineDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update/engines/{id}")
    public ResponseEntity<HttpStatus> updateEngine(@PathVariable("id") int id, EngineDto engineDto) {
        engineService.updateEngine(id, engineDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("engine/projection")
    public List<EngineView> findEnginesByVolumeBetween(@RequestParam("first") float first,
                                                       @RequestParam("second") float second) {
        return engineService.findEnginesByVolumeBetween(first, second);
    }

    @GetMapping("/engines/{id}")
    public EngineDto getEngine(@PathVariable("id") long id) {
        return engineService.getEngine(id);
    }

    @GetMapping("/engines")
    public List<EngineDto> getAllEngines() {
        return engineService.getAllEngines();
    }

    @GetMapping("/engines/s")
    public List<EngineDto> getAllEnginesWithPagination(@RequestParam("page") int page,
                                                       @RequestParam("size") int size) {
        return engineService.getAllEnginesWithPagination(page, size);
    }

    @DeleteMapping("/engines/{id}")
    public ResponseEntity<HttpStatus> deleteEngine(@PathVariable("id") int id) {
        engineService.deleteEngine(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
