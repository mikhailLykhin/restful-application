package com.restful.app.dao.extension.jdbc_template;

import com.restful.app.api.dao.extension.jdbc_template_dao.JdbcTemplateVehicleDao;
import com.restful.app.extension_entity.Vehicle;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class JdbcTemplateVehicleDaoImpl implements JdbcTemplateVehicleDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateVehicleDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createVehicle(Vehicle vehicle) {
        String INSERT_MESSAGE_SQL
                = "INSERT INTO vehicle (type, manufacture, model, engine_id) VALUES(?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(INSERT_MESSAGE_SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, vehicle.getType().name());
            preparedStatement.setString(2, vehicle.getManufacture());
            preparedStatement.setString(3, vehicle.getModel());
            preparedStatement.setLong(4, vehicle.getEngine().getId());
            return preparedStatement;
        }, keyHolder);

        jdbcTemplate.update("INSERT INTO parking_vehicle (parking_id, vehicle_id) VALUES(?,?)",
                vehicle.getParkings().stream().findFirst().get().getId(), keyHolder.getKeys().get("id"));
    }

    @Override
    public void updateVehicle(long id, Vehicle vehicle) {

    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return null;
    }

    @Override
    public Vehicle getVehicle(long id) {
        return null;
    }

    @Override
    public void deleteVehicle(long id) {
        jdbcTemplate.update("DELETE FROM vehicle WHERE id=?", id);
    }
}
