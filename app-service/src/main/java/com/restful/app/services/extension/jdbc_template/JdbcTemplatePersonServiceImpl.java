package com.restful.app.services.extension.jdbc_template;

import com.restful.app.api.dao.extension.jdbc_template_dao.JdbcTemplatePersonDao;
import com.restful.app.api.dto.extension.PersonDto;
import com.restful.app.api.services.CommonMapper;
import com.restful.app.api.services.extension.jdbc_template.JdbcTemplatePersonService;
import com.restful.app.extension_entity.Person;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JdbcTemplatePersonServiceImpl implements JdbcTemplatePersonService {

    private final JdbcTemplatePersonDao jdbcTemplatePersonDao;
    private final CommonMapper commonMapper;

    public JdbcTemplatePersonServiceImpl(JdbcTemplatePersonDao jdbcTemplatePersonDao, CommonMapper commonMapper) {
        this.jdbcTemplatePersonDao = jdbcTemplatePersonDao;
        this.commonMapper = commonMapper;
    }

    @Override
    @Transactional("extensionTransactionManager")
    public void createPerson(PersonDto personDto) {
        jdbcTemplatePersonDao.createPerson(commonMapper.map(personDto, Person.class));
    }

    @Override
    @Transactional("extensionTransactionManager")
    public void updatePerson(long id, PersonDto personDto) {
        jdbcTemplatePersonDao.updatePerson(id, commonMapper.map(personDto, Person.class));
    }

    @Override
    @Transactional("extensionTransactionManager")
    public List<PersonDto> getAllPersons() {
        return commonMapper.mapAll(jdbcTemplatePersonDao.getAllPersons(), PersonDto.class);
    }

    @Override
    @Transactional("extensionTransactionManager")
    public PersonDto getPersonByEmail(String email) {
        return commonMapper.map(jdbcTemplatePersonDao.getPersonByEmail(email), PersonDto.class);
    }

    @Override
    @Transactional("extensionTransactionManager")
    public void deletePerson(long id) {
        jdbcTemplatePersonDao.deletePerson(id);
    }
}
