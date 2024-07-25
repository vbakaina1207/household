

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;
    private Properties properties;

    private DatabaseConnection(){
        try (InputStream configFileInput = DatabaseConnection.class.getClassLoader()
                .getResourceAsStream("resources/connection.properties")) {
            properties = new Properties();
            // load property file
            properties.load(configFileInput);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }


    public Connection getConnection() throws SQLException {

        if (connection == null || connection.isClosed()|| !connection.isValid(30)){
            try {
                // establish connection
                connection =  DriverManager.getConnection(properties.getProperty("db.url"),
                        properties.getProperty("db.user"), properties.getProperty("db.password"));

            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }


}
