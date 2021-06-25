package com.restful.app.api.row_mappers;

import com.restful.app.extension_entity.Engine;
import com.restful.app.extension_enum.EngineType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EngineRowMapper implements RowMapper<Engine> {
    @Override
    public Engine mapRow(ResultSet resultSet, int i) throws SQLException {
        Engine engine = new Engine();
        engine.setId(resultSet.getLong("id"));
        engine.setNumber(resultSet.getString("number"));
        engine.setVolume(resultSet.getFloat("volume"));
        engine.setType(EngineType.valueOf(resultSet.getString("type")));
        return engine;
    }
}
