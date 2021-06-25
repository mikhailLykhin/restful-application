package com.restful.app.dao.extension.jdbc;

import com.restful.app.api.dao.extension.jdbc_dao.JdbcPersonDao;
import com.restful.app.dao.extension.util.JdbcConnectionUtil;
import com.restful.app.extension_entity.Person;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcPersonDaoImpl implements JdbcPersonDao {

    @Override
    public void createPerson(Person person) {
        try {
            PreparedStatement preparedStatement = JdbcConnectionUtil.getConnection().prepareStatement("INSERT INTO Person (name, age, email) VALUES(?, ?, ?)");
            preparedStatement.setString(1, person.getName());
            preparedStatement.setInt(2, person.getAge());
            preparedStatement.setString(3, person.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void updatePerson(long id, Person person) {
        try {
            PreparedStatement preparedStatement = JdbcConnectionUtil.getConnection().prepareStatement("UPDATE Person SET name=?, age=?, email=? WHERE id=?");
            preparedStatement.setString(1, person.getName());
            preparedStatement.setInt(2, person.getAge());
            preparedStatement.setString(3, person.getEmail());
            preparedStatement.setLong(4, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Person> getAllPersons() {
        List<Person> people = new ArrayList<>();
        try {
            Statement statement = JdbcConnectionUtil.getConnection().createStatement();
            String SQL = "select * from person";
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getLong("id"));
                person.setName(resultSet.getString("name"));
                person.setAge(resultSet.getInt("age"));
                person.setEmail(resultSet.getString("email"));
                people.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return people;
    }

    @Override
    public Person getPersonByEmail(String email) {
        Person person = null;
        try {
            PreparedStatement preparedStatement = JdbcConnectionUtil.getConnection().prepareStatement("SELECT * FROM Person WHERE email=?");
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            person = new Person();
            person.setId(resultSet.getLong("id"));
            person.setName(resultSet.getString("name"));
            person.setAge(resultSet.getInt("age"));
            person.setEmail(resultSet.getString("email"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return person;
    }

    @Override
    public void deletePerson(long id) {
        try {
            PreparedStatement preparedStatement = JdbcConnectionUtil.getConnection().prepareStatement("DELETE FROM Person WHERE id=?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
