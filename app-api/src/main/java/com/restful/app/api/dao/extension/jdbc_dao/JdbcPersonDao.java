package com.restful.app.api.dao.extension.jdbc_dao;

import com.restful.app.extension_entity.Person;

import java.util.List;


public interface JdbcPersonDao {

    void createPerson(Person person);

    void updatePerson(long id, Person person);

    List<Person> getAllPersons();

    Person getPersonByEmail(String email);

    void deletePerson(long id);
}
