package com.restful.app.dao.extension.util;

import lombok.experimental.UtilityClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@UtilityClass
public class JdbcConnectionUtil {

    private  final String URL = "jdbc:postgresql://localhost:5432/extension_db?allowPublicKeyRetrieval=true&createDatabaseIfNotExist=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Minsk&useSSL=false";
    private  final String USER = "postgres";
    private  final String PASSWORD = "password";

    public Connection getConnection()  {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException exception) {
            throw new RuntimeException("Error connecting to database", exception);
        }
    }
}
