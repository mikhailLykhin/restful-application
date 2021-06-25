package com.restful.app.services.extension.jdbc_template;

import com.restful.app.api.dao.extension.jdbc_template_dao.JdbcTemplateEngineDao;
import com.restful.app.api.dto.extension.EngineDto;
import com.restful.app.api.dto.extension.PersonDto;
import com.restful.app.api.services.CommonMapper;
import com.restful.app.api.services.extension.jdbc_template.JdbcTemplateEngineService;
import com.restful.app.extension_entity.Engine;
import com.restful.app.extension_entity.Person;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JdbcTemplateEngineServiceImpl implements JdbcTemplateEngineService {

    private final JdbcTemplateEngineDao engineDao;
    private final CommonMapper commonMapper;

    public JdbcTemplateEngineServiceImpl(JdbcTemplateEngineDao engineDao, CommonMapper commonMapper) {
        this.engineDao = engineDao;
        this.commonMapper = commonMapper;
    }

    @Override
    public void createEngine(EngineDto personDto) {
        engineDao.createEngine(commonMapper.map(personDto, Engine.class));
    }

    @Override
    public void updateEngine(long id, EngineDto personDto) {
        engineDao.updateEngine(id, commonMapper.map(personDto, Engine.class));
    }

    @Override
    public List<EngineDto> getAllEngines() {
        return commonMapper.mapAll(engineDao.getAllEngines(), EngineDto.class);
    }

    @Override
    public EngineDto getEngine(long id) {
        return commonMapper.map(engineDao.getEngine(id), EngineDto.class);
    }

    @Override
    public void deleteEngine(long id) {
        engineDao.deleteEngine(id);
    }
}
