package com.labexam.task1and3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Component
@NoArgsConstructor
public class DBConnectionManager {
    private String databaseName = "BookstoreDB";
    private String booksTableName = "Books";
    private String loginTableName = "login";
    private String url = "jdbc:mysql://localhost:3306/" + databaseName;
    private String username = "root";
    private String password = "1234";

    /**
     * Opens a connection to the database, creates the database and tables if they do not exist.
     *
     * @return the established database connection
     * @throws SQLException if a database access error occurs
     */
    public Connection openConnection() throws SQLException {
        createDatabase();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println(e);
        }
        createBooksTable(connection);
        createLoginTable(connection);
        return connection;
    }

    /**
     * Closes the given database connection.
     *
     * @param connection the connection to close
     */
    public void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * Creates the database if it does not exist.
     */
    public void createDatabase() {
        String createDatabaseCommand = "CREATE DATABASE IF NOT EXISTS " + databaseName;
        String baseUrl = "jdbc:mysql://localhost:3306/";

        try (Connection connection = DriverManager.getConnection(baseUrl, username, password);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(createDatabaseCommand);
            System.out.println("Database " + databaseName + " created!");
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    /**
     * Creates the Books table if it does not exist.
     *
     * @param connection the database connection
     */
    public void createBooksTable(Connection connection) {
        String createTableCommand = "CREATE TABLE IF NOT EXISTS " + booksTableName + " ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "title VARCHAR(255),"
                + "author VARCHAR(255),"
                + "price DOUBLE)";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableCommand);
            System.out.println("Table " + booksTableName + " created!");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * Creates the Login table if it does not exist.
     *
     * @param connection the database connection
     */
    public void createLoginTable(Connection connection) {
        String createTableCommand = "CREATE TABLE IF NOT EXISTS " + loginTableName + " ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "username VARCHAR(255) UNIQUE,"
                + "password VARCHAR(255))";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableCommand);
            System.out.println("Table " + loginTableName + " created!");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}