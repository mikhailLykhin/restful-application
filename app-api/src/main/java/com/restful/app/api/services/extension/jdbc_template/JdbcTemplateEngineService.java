package com.restful.app.api.services.extension.jdbc_template;

import com.restful.app.api.dto.extension.EngineDto;

import java.util.List;

public interface JdbcTemplateEngineService {

    void createEngine(EngineDto engineDto);

    void updateEngine(long id, EngineDto engineDto);

    List<EngineDto> getAllEngines();

    EngineDto getEngine(long id);

    void deleteEngine(long id);
}
