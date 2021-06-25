package com.restful.app.rest.controllers.extension.jdbc_template_controllers;

import com.restful.app.api.dto.extension.PersonDto;
import com.restful.app.api.services.extension.jdbc_template.JdbcTemplatePersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/template")
public class JdbcTemplatePersonController {

    private final JdbcTemplatePersonService jdbcTemplatePersonService;

    public JdbcTemplatePersonController(JdbcTemplatePersonService jdbcTemplatePersonService) {
        this.jdbcTemplatePersonService = jdbcTemplatePersonService;
    }


    @PostMapping("/create/person")
    public ResponseEntity<HttpStatus> createPerson(@RequestBody PersonDto personDto) {
        jdbcTemplatePersonService.createPerson(personDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update/person/{id}")
    public ResponseEntity<HttpStatus> updatePerson(@PathVariable("id") int id, PersonDto personDto) {
        jdbcTemplatePersonService.updatePerson(id, personDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/persons/p")
    public PersonDto getPersonByEmail(@RequestParam("email") String email) {
        return jdbcTemplatePersonService.getPersonByEmail(email);
    }

    @GetMapping("/persons")
    public List<PersonDto> getAllPersons() {
        return jdbcTemplatePersonService.getAllPersons();
    }

    @DeleteMapping("/persons/{id}")
    public ResponseEntity<HttpStatus> deletePerson(@PathVariable("id") int id) {
        jdbcTemplatePersonService.deletePerson(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
