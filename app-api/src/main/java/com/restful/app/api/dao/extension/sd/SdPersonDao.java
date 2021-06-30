package com.restful.app.api.dao.extension.sd;

import com.restful.app.api.dao.extension.sd.projections.PersonView;
import com.restful.app.extension_entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SdPersonDao extends JpaRepository<Person, Long> {

    Person findByEmail(String email);

    List<PersonView> getAllByAgeAfter(int age);
}
