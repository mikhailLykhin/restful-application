package com.restful.app.api.dao.extension.sd;

import com.restful.app.api.dao.extension.sd.projections.EngineView;
import com.restful.app.extension_entity.Engine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SdEngineDao extends JpaRepository<Engine, Long> {

    List<EngineView> findEnginesByVolumeBetween(float first, float second);
}
