package com.restful.app.services.extension.sd_services;

import com.restful.app.api.dao.extension.sd.SdPersonDao;
import com.restful.app.api.dao.extension.sd.projections.PersonView;
import com.restful.app.api.dto.extension.PersonDto;
import com.restful.app.api.exceptions.IncorrectDataException;
import com.restful.app.api.services.CommonMapper;
import com.restful.app.api.services.extension.sd.SdPersonService;
import com.restful.app.extension_entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SdPersonServiceImpl implements SdPersonService {

    private final SdPersonDao personDao;
    private final CommonMapper commonMapper;

    public SdPersonServiceImpl(SdPersonDao personDao, CommonMapper commonMapper) {
        this.personDao = personDao;
        this.commonMapper = commonMapper;
    }


    @Override
    @Transactional("extensionTransactionManager")
    public void createPerson(PersonDto personDto) {
        personDao.save(commonMapper.map(personDto, Person.class));

    }

    @Override
    @Transactional("extensionTransactionManager")
    public void updatePerson(long id, PersonDto personDto) {
        Optional<Person> entity = personDao.findById(id);
        Person person = entity.orElse(null);
        if (person == null) {
            throw new IncorrectDataException("Person doesn't exist");
        }
        person = commonMapper.map(personDto, Person.class);
        personDao.save(person);
    }

    @Override
    public List<PersonView> getAllByAgeAfter(int age) {
        return personDao.getAllByAgeAfter(age);
    }

    @Override
    @Transactional("extensionTransactionManager")
    public List<PersonDto> getAllPersons() {
        return commonMapper.mapAll(personDao.findAll(), PersonDto.class);
    }

    @Override
    @Transactional("extensionTransactionManager")
    public List<PersonDto> getAllPersonsWithPagination(int page, int size) {
        Page<Person> personPage = personDao.findAll(PageRequest.of(page, size, Sort.by("age")));
        return commonMapper.mapAll(personPage.getContent(), PersonDto.class);
    }

    @Override
    @Transactional("extensionTransactionManager")
    public PersonDto getPersonByEmail(String email) {
        Person entity = personDao.findByEmail(email);
        if (entity == null) {
            throw new IncorrectDataException("Person doesn't exist");
        }
        return commonMapper.map(entity, PersonDto.class);
    }

    @Override
    @Transactional("extensionTransactionManager")
    public void deletePerson(String email) {
        Person entity = personDao.findByEmail(email);
        if (entity == null) {
            throw new IncorrectDataException("Person doesn't exist");
        }
        personDao.delete(entity);
    }
}
