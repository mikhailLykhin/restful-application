package com.restful.app.dao.extension.jdbc_template;

import com.restful.app.api.dao.extension.jdbc_template_dao.JdbcTemplateVehicleDao;
import com.restful.app.api.row_mappers.VehicleExtractData;
import com.restful.app.extension_entity.Vehicle;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

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

        vehicle.getParkings().forEach(p -> {
            jdbcTemplate.update("INSERT INTO parking_vehicle (parking_id, vehicle_id) VALUES(?,?)",
                    p.getId(), keyHolder.getKeys().get("id"));
        });

    }

    @Override
    public void updateVehicle(long id, Vehicle vehicle) {
        jdbcTemplate.update("UPDATE vehicle SET type=?, manufacture=?, model=? WHERE id=?",
                vehicle.getType().name(), vehicle.getManufacture(), vehicle.getModel(), id);
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return jdbcTemplate.query("select veh.id as vehId, veh.type as vehType,\n" +
                "veh.manufacture as vehManufacture, veh.model as vehModel,\n" +
                "eng.id as engId, eng.number as engNumber, eng.type as engType, \n" +
                "eng.volume as engVolume,\n" +
                "p.id as pId, p.square as pSquare, p.address as pAddress\n" +
                "from vehicle as veh\n" +
                "inner join engine as eng on veh.engine_id = eng .id \n" +
                "inner join parking_vehicle pv on veh.id = pv.vehicle_id \n" +
                "inner join parking p on p.id = pv.parking_id;", new VehicleExtractData());
    }

    @Override
    public Vehicle getVehicle(long id) {
        return Objects.requireNonNull(jdbcTemplate.query("select veh.id as vehId, veh.type as vehType,\n" +
                "veh.manufacture as vehManufacture, veh.model as vehModel,\n" +
                "eng.id as engId, eng.number as engNumber, eng.type as engType, \n" +
                "eng.volume as engVolume,\n" +
                "p.id as pId, p.square as pSquare, p.address as pAddress\n" +
                "from vehicle as veh\n" +
                "inner join engine as eng on veh.engine_id = eng .id \n" +
                "inner join parking_vehicle pv on veh.id = pv.vehicle_id \n" +
                "inner join parking p on p.id = pv.parking_id where veh.id = ?;", new VehicleExtractData(), id)).stream().findFirst().get();
    }

    @Override
    public void deleteVehicle(long id) {
        jdbcTemplate.update("DELETE FROM vehicle WHERE id=?", id);
    }
}
