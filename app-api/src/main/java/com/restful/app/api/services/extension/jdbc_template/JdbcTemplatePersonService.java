package com.restful.app.api.services.extension.jdbc_template;

import com.restful.app.api.dto.extension.PersonDto;

import java.util.List;

public interface JdbcTemplatePersonService {

    void createPerson(PersonDto personDto);

    void updatePerson(long id, PersonDto personDto);

    List<PersonDto> getAllPersons();

    PersonDto getPersonByEmail(String email);

    void deletePerson(long id);
}
