package com.restful.app.rest.controllers.extension.jdbc_template_controllers;

import com.restful.app.api.dto.extension.EngineDto;
import com.restful.app.api.services.extension.jdbc_template.JdbcTemplateEngineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/template")
public class JdbcTemplateEngineController {

    private final JdbcTemplateEngineService engineService;

    public JdbcTemplateEngineController(JdbcTemplateEngineService engineService) {
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

    @GetMapping("/engines/{id}")
    public EngineDto getEngine(@PathVariable("id") long id) {
        return engineService.getEngine(id);
    }

    @GetMapping("/engines")
    public List<EngineDto> getAllEngines() {
        return engineService.getAllEngines();
    }

    @DeleteMapping("/engines/{id}")
    public ResponseEntity<HttpStatus> deleteEngine(@PathVariable("id") int id) {
        engineService.deleteEngine(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
