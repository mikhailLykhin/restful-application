package com.restful.app.dao.extension.jdbc_template;

import com.restful.app.api.dao.extension.jdbc_template_dao.JdbcTemplatePersonDao;
import com.restful.app.api.row_mappers.PersonRowMapper;
import com.restful.app.extension_entity.Person;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcTemplatePersonDaoImpl implements JdbcTemplatePersonDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplatePersonDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void createPerson(Person person) {
        jdbcTemplate.update("INSERT INTO Person (name, age, email) VALUES(?, ?, ?)", person.getName(), person.getAge(),
                person.getEmail());
    }

    @Override
    public void updatePerson(long id, Person person) {
        jdbcTemplate.update("UPDATE Person SET name=?, age=?, email=? WHERE id=?", person.getName(),
                person.getAge(), person.getEmail(), id);
    }

    @Override
    public List<Person> getAllPersons() {
        return jdbcTemplate.query("SELECT * FROM Person", new PersonRowMapper());
    }

    @Override
    public Person getPersonByEmail(String email) {
        return jdbcTemplate.queryForObject("SELECT * FROM Person WHERE email = ?", new PersonRowMapper(), email);
    }

    @Override
    public void deletePerson(long id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }
}
