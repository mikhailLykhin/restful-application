package com.restful.app.rest.controllers.extension.sd_controllers;

import com.restful.app.api.dao.extension.sd.projections.PersonView;
import com.restful.app.api.dto.extension.EngineDto;
import com.restful.app.api.dto.extension.PersonDto;
import com.restful.app.api.services.extension.sd.SdPersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sd")
public class SdPersonController {

    private final SdPersonService personService;

    public SdPersonController(SdPersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/create/person")
    public ResponseEntity<HttpStatus> createPerson(@RequestBody PersonDto personDto) {
        personService.createPerson(personDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update/person/{id}")
    public ResponseEntity<HttpStatus> updatePerson(@PathVariable("id") int id, PersonDto personDto) {
        personService.updatePerson(id, personDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/person/projection")
    public List<PersonView> getAllByAgeAfter(@RequestParam("age") int age) {
        return personService.getAllByAgeAfter(age);
    }

    @GetMapping("/persons/p")
    public PersonDto getPersonByEmail(@RequestParam("email") String email) {
        return personService.getPersonByEmail(email);
    }

    @GetMapping("/persons")
    public List<PersonDto> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/persons/s")
    public List<PersonDto> getAllPersonsWithPagination(@RequestParam("page") int page,
                                                       @RequestParam("size") int size) {
        return personService.getAllPersonsWithPagination(page, size);
    }

    @DeleteMapping("/persons/{id}")
    public ResponseEntity<HttpStatus> deletePerson(@PathVariable("id") int id) {
        personService.deletePerson(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
