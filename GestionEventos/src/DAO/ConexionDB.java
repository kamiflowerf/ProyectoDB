package DAO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Enumeration;
import java.util.Properties;

public class ConexionDB {
    static String url = "jdbc:sqlserver://localhost:1433;"
            + "databaseName=GestionEventos;"
            + "user=sa;"
            + "password=kami1234;"
            + "encrypt=true;"
            + "trustServerCertificate=true;";

    private ConexionDB() {}

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //System.out.println("Conexion exitosa a la base de datos");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return DriverManager.getConnection(url);
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
