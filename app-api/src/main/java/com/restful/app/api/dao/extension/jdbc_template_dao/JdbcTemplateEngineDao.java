package com.restful.app.api.dao.extension.jdbc_template_dao;

import com.restful.app.extension_entity.Engine;

import java.util.List;

public interface JdbcTemplateEngineDao {

    void createEngine(Engine engine);

    void updateEngine(long id, Engine engine);

    List<Engine> getAllEngines();

    Engine getEngine(long id);

    void deleteEngine(long id);
}
