package com.restful.app.services.extension.sd_services;

import com.restful.app.api.dao.extension.sd.SdEngineDao;
import com.restful.app.api.dao.extension.sd.projections.EngineView;
import com.restful.app.api.dto.extension.EngineDto;
import com.restful.app.api.exceptions.IncorrectDataException;
import com.restful.app.api.services.CommonMapper;
import com.restful.app.api.services.extension.sd.SdEngineService;
import com.restful.app.extension_entity.Engine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SdEngineServiceImpl implements SdEngineService {

    private final SdEngineDao engineDao;
    private final CommonMapper commonMapper;

    public SdEngineServiceImpl(SdEngineDao engineDao, CommonMapper commonMapper) {
        this.engineDao = engineDao;
        this.commonMapper = commonMapper;
    }

    @Override
    @Transactional("extensionTransactionManager")
    public void createEngine(EngineDto engineDto) {
        engineDao.save(commonMapper.map(engineDto, Engine.class));
    }

    @Override
    @Transactional("extensionTransactionManager")
    public void updateEngine(long id, EngineDto engineDto) {
        Optional<Engine> entity = engineDao.findById(id);
        Engine engine = entity.orElse(null);
        if (engine == null) {
            throw new IncorrectDataException("Engine doesn't exist");
        }
        engine = commonMapper.map(engineDto, Engine.class);
        engineDao.save(engine);
    }

    @Override
    public List<EngineView> findEnginesByVolumeBetween(float first, float second) {
        return engineDao.findEnginesByVolumeBetween(first, second);
    }

    @Override
    @Transactional("extensionTransactionManager")
    public List<EngineDto> getAllEngines() {
        return commonMapper.mapAll(engineDao.findAll(), EngineDto.class);
    }

    @Override
    @Transactional("extensionTransactionManager")
    public List<EngineDto> getAllEnginesWithPagination(int page, int size) {
        Page<Engine> enginePage = engineDao.findAll(PageRequest.of(page, size, Sort.by("volume")));
        return commonMapper.mapAll(enginePage.getContent(), EngineDto.class);
    }

    @Override
    @Transactional("extensionTransactionManager")
    public EngineDto getEngine(long id) {
        Optional<Engine> entity = engineDao.findById(id);
        Engine engine = entity.orElse(null);
        if (engine == null) {
            throw new IncorrectDataException("Engine doesn't exist");
        }
        return commonMapper.map(engine, EngineDto.class);
    }

    @Override
    @Transactional("extensionTransactionManager")
    public void deleteEngine(long id) {
        Optional<Engine> entity = engineDao.findById(id);
        Engine engine = entity.orElse(null);
        if (engine == null) {
            throw new IncorrectDataException("Engine doesn't exist");
        }
        engineDao.delete(engine);
    }
}
