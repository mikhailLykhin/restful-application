package com.restful.app.dao.extension.jdbc_template;

import com.restful.app.api.dao.extension.jdbc_template_dao.JdbcTemplateEngineDao;
import com.restful.app.api.row_mappers.EngineRowMapper;
import com.restful.app.extension_entity.Engine;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcTemplateEngineDaoImpl implements JdbcTemplateEngineDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateEngineDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createEngine(Engine engine) {
        jdbcTemplate.update("INSERT INTO engine (number, volume, type) VALUES(?, ?, ?)",
                engine.getNumber(), engine.getVolume(), engine.getType().name());
    }

    @Override
    public void updateEngine(long id, Engine engine) {
        jdbcTemplate.update("UPDATE engine SET number=?, volume=?, type=? WHERE id=?",
                engine.getNumber(), engine.getVolume(), engine.getType().toString(), id);
    }

    @Override
    public List<Engine> getAllEngines() {
        return jdbcTemplate.query("SELECT * FROM engine", new EngineRowMapper());
    }

    @Override
    public Engine getEngine(long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM engine WHERE id = ?", new EngineRowMapper(), id);
    }

    @Override
    public void deleteEngine(long id) {
        jdbcTemplate.update("DELETE FROM engine WHERE id=?", id);

    }
}
