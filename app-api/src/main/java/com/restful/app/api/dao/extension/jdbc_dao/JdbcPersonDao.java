package com.restful.app.api.dao.extension.jdbc_dao;

import com.restful.app.extension_entity.Person;

import java.sql.SQLException;
import java.util.List;


public interface JdbcPersonDao {

    void createPerson(Person person) throws SQLException;

    void updatePerson(long id, Person person) throws SQLException;

    List<Person> getAllPersons() throws SQLException;

    Person getPersonByEmail(String email) throws SQLException;

    void deletePerson(long id) throws SQLException;
}
