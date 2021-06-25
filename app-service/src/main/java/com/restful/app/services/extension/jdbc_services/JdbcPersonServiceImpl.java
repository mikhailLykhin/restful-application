package com.restful.app.services.extension.jdbc_services;

import com.restful.app.api.dao.extension.jdbc_dao.JdbcPersonDao;
import com.restful.app.api.dto.extension.PersonDto;
import com.restful.app.api.services.CommonMapper;
import com.restful.app.api.services.extension.jdbc.JdbcPersonService;
import com.restful.app.extension_entity.Person;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JdbcPersonServiceImpl implements JdbcPersonService {

    private final JdbcPersonDao personDao;
    private final CommonMapper commonMapper;

    public JdbcPersonServiceImpl(JdbcPersonDao personDao, CommonMapper commonMapper) {
        this.personDao = personDao;
        this.commonMapper = commonMapper;
    }

    @Override
    public void createPerson(PersonDto personDto) {
        personDao.createPerson(commonMapper.map(personDto, Person.class));
    }

    @Override
    public void updatePerson(long id, PersonDto personDto) {
        personDao.updatePerson(id, commonMapper.map(personDto, Person.class));
    }

    public List<PersonDto> getAllPersons() {
        return commonMapper.mapAll(personDao.getAllPersons(), PersonDto.class);
    }

    public PersonDto getPersonByEmail(String email) {
        return commonMapper.map(personDao.getPersonByEmail(email), PersonDto.class);
    }

    @Override
    public void deletePerson(long id) {
        personDao.deletePerson(id);
    }


}
