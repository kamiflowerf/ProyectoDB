package DAO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class ConexionDB {
    private static String URL;
    private static String USUARIO;
    private static String CLAVE;

    private ConexionDB() {}

    static{
        try{
            Properties prop = new Properties();
            prop.load(new FileInputStream("config.properties"));
            URL = prop.getProperty("db.url");
            USUARIO = prop.getProperty("db.user");
            CLAVE = prop.getProperty("db.password");
        } catch (IOException e) {
            System.err.println("No se pudo leer el archivo de configuracion");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }

    public static void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }

    public static void closePreparedStatement(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.close();
    }

    public static void closeResultSet(ResultSet resultSet) throws SQLException {
        resultSet.close();
    }
}
