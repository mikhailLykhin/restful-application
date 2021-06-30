package com.restful.app.api.services.extension.jdbc;

import com.restful.app.api.dto.extension.PersonDto;

import java.sql.SQLException;
import java.util.List;

public interface JdbcPersonService {

    void createPerson(PersonDto personDto) throws SQLException;

    void updatePerson(long id, PersonDto personDto) throws SQLException;

    List<PersonDto> getAllPersons() throws SQLException;

    PersonDto getPersonByEmail(String email) throws SQLException;

    void deletePerson(long id) throws SQLException;
}
