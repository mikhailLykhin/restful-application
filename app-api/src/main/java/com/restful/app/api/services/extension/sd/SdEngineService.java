package com.restful.app.api.services.extension.sd;

import com.restful.app.api.dao.extension.sd.projections.EngineView;
import com.restful.app.api.dto.extension.EngineDto;

import java.util.List;

public interface SdEngineService {

    void createEngine(EngineDto engineDto);

    void updateEngine(long id, EngineDto engineDto);

    List<EngineView> findEnginesByVolumeBetween(float first, float second);

    List<EngineDto> getAllEngines();

    List<EngineDto> getAllEnginesWithPagination(int page, int size);

    EngineDto getEngine(long id);

    void deleteEngine(long id);
}
