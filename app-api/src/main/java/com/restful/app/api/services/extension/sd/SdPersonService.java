package com.restful.app.api.services.extension.sd;

import com.restful.app.api.dao.extension.sd.projections.PersonView;
import com.restful.app.api.dto.extension.PersonDto;

import java.util.List;

public interface SdPersonService {

    void createPerson(PersonDto personDto);

    void updatePerson(long id, PersonDto personDto);

    List<PersonView> getAllByAgeAfter(int age);

    List<PersonDto> getAllPersons();

    List<PersonDto> getAllPersonsWithPagination(int page, int size);

    PersonDto getPersonByEmail(String email);

    void deletePerson(String email);
}
