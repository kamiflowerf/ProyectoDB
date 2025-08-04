package DAO;

import logico.Seguridad;
import logico.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class UserDAO implements DAO<User> {
    @Override
    public User get(String Id) throws SQLException {
        Connection con = ConexionDB.getConnection();
        User user = null;
        String sql = "SELECT nombre, apellido, contrasena, tipo FROM Usuario WHERE nombreUsuario = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, Id);
        ResultSet rs = ps.executeQuery();

        if(rs.next()) {
            user = new User(
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    Id,
                    rs.getString("contrasena"),
                    rs.getString("tipo")
            );
        }

        ConexionDB.closeResultSet(rs);
        ConexionDB.closePreparedStatement(ps);
        ConexionDB.closeConnection(con);
        return user;
    }

    @Override
    public ArrayList<User> getAll() throws SQLException {
        Connection con = ConexionDB.getConnection();
        ArrayList<User> users = new ArrayList<>();

        String sql = "SELECT nombreUsuario, nombre, apellido, contrasena, tipo FROM Usuario";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            users.add(new User(
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("nombreUsuario"),
                    rs.getString("contrasena"),
                    rs.getString("tipo")
            ));
        }

        ConexionDB.closeResultSet(rs);
        ConexionDB.closePreparedStatement(ps);
        ConexionDB.closeConnection(con);
        return users;
    }

    @Override
    public boolean insert(User user) throws SQLException {
        Connection con = ConexionDB.getConnection();
        String sql = "INSERT INTO Usuario(nombreUsuario,nombre,apellido,contrasena,tipo) VALUES(?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, user.getUserName());
        ps.setString(2, user.getNombre());
        ps.setString(3, user.getApellido());
        ps.setString(4, user.getPassword());
        ps.setString(5, user.getTipo());
        int result = ps.executeUpdate();

        ConexionDB.closePreparedStatement(ps);
        ConexionDB.closeConnection(con);
        return result > 0;
    }

    //NO APLICA
    @Override
    public boolean update(User user) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(User user) throws SQLException {
        Connection con = ConexionDB.getConnection();
        String sql = "DELETE FROM Usuario WHERE nombreUsuario = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, user.getUserName());
        int result = ps.executeUpdate();
        ConexionDB.closePreparedStatement(ps);
        ConexionDB.closeConnection(con);
        return result > 0;
    }

    public User autenticarUsuario(String usuario, String contrasenaPlano) {
        String hash = Seguridad.sha256(contrasenaPlano); // hasheamos antes
        String sql = "SELECT * FROM Usuario WHERE nombreUsuario = ? AND contrasena = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            stmt.setString(2, hash); // comparamos contra el hash
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return new User(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("nombreUsuario"),
                        rs.getString("contrasena"), // ← hash
                        rs.getString("tipo")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
