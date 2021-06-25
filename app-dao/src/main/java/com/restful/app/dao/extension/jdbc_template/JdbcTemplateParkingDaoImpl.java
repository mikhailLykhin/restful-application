package com.restful.app.dao.extension.jdbc_template;

import com.restful.app.api.dao.extension.jdbc_template_dao.JdbcTemplateParkingDao;
import com.restful.app.api.row_mappers.ParkingRowMapperColumnIndex;
import com.restful.app.api.row_mappers.ParkingRowMapperColumnName;
import com.restful.app.extension_entity.Parking;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcTemplateParkingDaoImpl implements JdbcTemplateParkingDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateParkingDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createParking(Parking parking) {
        jdbcTemplate.update("INSERT INTO Parking (square, address, person_id) VALUES(?, ?, ?)",
                parking.getSquare(), parking.getAddress(), parking.getPerson().getId());
    }

    @Override
    public void updateParking(long id, Parking parking) {
        jdbcTemplate.update("UPDATE Parking SET square=?, address=?, person_id=? WHERE id=?",
                parking.getSquare(), parking.getAddress(), parking.getPerson().getId(), id);
    }

    @Override
    public List<Parking> getAllParkings() {
     return jdbcTemplate.query("select * from parking as par inner join person as pers on par.person_id = pers.id", new ParkingRowMapperColumnIndex());
    }

    @Override
    public Parking getParking(long id) {
        return jdbcTemplate.queryForObject("select pers.id as persId, pers.name as persName, pers.age as persAge, pers.email as persEmail," +
                "par.id as parId, par.square as parSquare, par.address as parAddress" +
                " from parking as par inner join person as pers on par.person_id = pers.id where par.id=?", new ParkingRowMapperColumnName(), id);
    }

    @Override
    public void deleteParking(long id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }
}
