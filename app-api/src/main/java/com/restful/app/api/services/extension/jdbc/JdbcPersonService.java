package com.restful.app.api.services.extension.jdbc;

import com.restful.app.api.dto.extension.PersonDto;
import com.restful.app.extension_entity.Person;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JdbcPersonService {

    void createPerson(PersonDto personDto);

    void updatePerson(long id, PersonDto personDto);

    List<PersonDto> getAllPersons();

    PersonDto getPersonByEmail(String email);

    void deletePerson(long id);
}
