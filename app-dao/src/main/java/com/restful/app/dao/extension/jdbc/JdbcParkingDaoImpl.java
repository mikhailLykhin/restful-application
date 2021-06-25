package com.restful.app.dao.extension.jdbc;

import com.restful.app.api.dao.extension.jdbc_dao.JdbcParkingDao;
import com.restful.app.dao.extension.util.JdbcConnectionUtil;
import com.restful.app.extension_entity.Parking;
import com.restful.app.extension_entity.Person;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcParkingDaoImpl implements JdbcParkingDao {

    @Override
    public void createParking(Parking parking) {
        try {
            PreparedStatement preparedStatement = JdbcConnectionUtil.getConnection().prepareStatement("INSERT INTO Parking (square, address, person_id) VALUES(?, ?, ?)");
            preparedStatement.setFloat(1, parking.getSquare());
            preparedStatement.setString(2, parking.getAddress());
            preparedStatement.setLong(3, parking.getPerson().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void updateParking(long id, Parking parking) {
        try {
            PreparedStatement preparedStatement = JdbcConnectionUtil.getConnection().prepareStatement("UPDATE Parking SET square=?, address=?, person_id=? WHERE id=?");
            preparedStatement.setFloat(1, parking.getSquare());
            preparedStatement.setString(2, parking.getAddress());
            preparedStatement.setLong(3, parking.getPerson().getId());
            preparedStatement.setLong(4, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Parking> getAllParkings() {
        List<Parking> parkings = new ArrayList<>();
        try {
            Statement statement = JdbcConnectionUtil.getConnection().createStatement();
            String SQL = "select * from parking par inner join person pers on par.person_id = pers.id";
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                Parking parking = new Parking();
                parking.setId(resultSet.getLong(1));
                parking.setSquare(resultSet.getFloat(2));
                parking.setAddress(resultSet.getString(3));
                Person person = new Person();
                person.setId(resultSet.getLong(5));
                person.setName(resultSet.getString(6));
                person.setAge(resultSet.getInt(7));
                person.setEmail(resultSet.getString(8));
                parking.setPerson(person);
                parkings.add(parking);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return parkings;
    }

    @Override
    public Parking getParking(long id) {
        Parking parking = null;
        Person person = null;
        try {
            PreparedStatement preparedStatement = JdbcConnectionUtil.getConnection()
                    .prepareStatement("select pers.id as persId, pers.name as persName, pers.age as persAge, pers.email as persEmail," +
                            "par.id as parId, par.square as parSquare, par.address as parAddress" +
                            " from parking as par inner join person as pers on par.person_id = pers.id where par.id=?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            person = new Person();
            person.setId(resultSet.getLong("persId"));
            person.setName(resultSet.getString("persName"));
            person.setAge(resultSet.getInt("persAge"));
            person.setEmail(resultSet.getString("persEmail"));
            parking = new Parking();
            parking.setId(resultSet.getLong("parId"));
            parking.setSquare(resultSet.getFloat("parSquare"));
            parking.setAddress(resultSet.getString("parAddress"));
            parking.setPerson(person);
            person.getParkings().add(parking);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return parking;
    }

    @Override
    public void deleteParking(long id) {
        try {
            PreparedStatement preparedStatement = JdbcConnectionUtil.getConnection().prepareStatement("DELETE FROM Parking WHERE id=?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
