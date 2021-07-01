package com.restful.app.services.extension.jdbc_template;

import com.restful.app.api.dao.extension.jdbc_template_dao.JdbcTemplateEngineDao;
import com.restful.app.api.dto.extension.EngineDto;
import com.restful.app.api.services.CommonMapper;
import com.restful.app.api.services.extension.jdbc_template.JdbcTemplateEngineService;
import com.restful.app.extension_entity.Engine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void createEngine(EngineDto personDto) {
        engineDao.createEngine(commonMapper.map(personDto, Engine.class));
    }

    @Override
    @Transactional
    public void updateEngine(long id, EngineDto personDto) {
        engineDao.updateEngine(id, commonMapper.map(personDto, Engine.class));
    }

    @Override
    @Transactional
    public List<EngineDto> getAllEngines() {
        return commonMapper.mapAll(engineDao.getAllEngines(), EngineDto.class);
    }

    @Override
    @Transactional
    public EngineDto getEngine(long id) {
        return commonMapper.map(engineDao.getEngine(id), EngineDto.class);
    }

    @Override
    @Transactional
    public void deleteEngine(long id) {
        engineDao.deleteEngine(id);
    }
}
