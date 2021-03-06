package com.restful.app.rest.controllers.extension.jdbc_controllers;

import com.restful.app.api.dto.extension.PersonDto;
import com.restful.app.api.services.extension.jdbc.JdbcPersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/jdbc")
public class JdbcPersonController {

    private final JdbcPersonService personService;

    public JdbcPersonController(JdbcPersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/create/person")
    public ResponseEntity<HttpStatus> createPerson(@RequestBody PersonDto personDto) throws SQLException {
        personService.createPerson(personDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update/person/{id}")
    public ResponseEntity<HttpStatus> updatePerson(@PathVariable("id") int id, PersonDto personDto) throws SQLException {
        personService.updatePerson(id, personDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/persons/p")
    public PersonDto getPersonByEmail(@RequestParam("email") String email) throws SQLException {
       return personService.getPersonByEmail(email);
    }

    @GetMapping("/persons")
    public List<PersonDto> getAllPersons() throws SQLException {
        return personService.getAllPersons();
    }

    @DeleteMapping("/persons/{id}")
    public ResponseEntity<HttpStatus> deletePerson(@PathVariable("id") int id) throws SQLException {
       personService.deletePerson(id);
       return new ResponseEntity<>(HttpStatus.OK);
    }
}
