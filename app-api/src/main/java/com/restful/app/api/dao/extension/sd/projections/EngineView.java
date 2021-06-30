package com.restful.app.api.dao.extension.sd.projections;

import org.springframework.beans.factory.annotation.Value;

public interface EngineView {

    @Value("#{target.type + ' ' + target.number}")
    String getTypePlusNumber();
}
