package DAO;

import logico.Area;
import logico.Jurado;
import logico.Participante;
import logico.Persona;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonaDAO  {

    public Persona getPersonByDni(String dni) {
        try (Connection conn = ConexionDB.getConnection()) {

            // 1. Intentar cargar como Jurado
            String sqlJurado = "SELECT j.idJurado, a.idArea, a.nombreArea, p.dni, p.nombre, p.apellido, p.telefono " +
                               "FROM Jurado j " +
                               "JOIN Persona p ON j.dni = p.dni " +
                               "JOIN Area a ON j.idArea = a.idArea " +
                               "WHERE p.dni = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlJurado)) {
                stmt.setString(1, dni);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    Area a = new AreaDAO().get(rs.getString("idArea"));
                    Jurado j = new Jurado(
                            rs.getString("idJurado"),
                            rs.getString("dni"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("telefono"),
                            a
                    );
                    return j;
                }
            }

            // 2. Intentar cargar como Participante
            String sqlParticipante = "SELECT pa.idParticipante, p.dni, p.nombre, p.apellido, p.telefono " +
                                     "FROM Participante pa " +
                                     "JOIN Persona p ON pa.dni = p.dni " +
                                     "WHERE p.dni = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlParticipante)) {
                stmt.setString(1, dni);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    Participante pa = new Participante(
                            rs.getString("idParticipante"),
                            rs.getString("dni"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("telefono")
                    );
                    return pa;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean insertPersona(Persona persona, Connection con) throws SQLException {

        String sql = "INSERT INTO Persona(dni,nombre,apellido,telefono) VALUES(?,?,?,?)";
        try(PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1,persona.getCedula());
            ps.setString(2,persona.getNombre());
            ps.setString(3, persona.getApellidos());
            ps.setString(4, persona.getTelefono());

            int result = ps.executeUpdate();
            ConexionDB.closePreparedStatement(ps);

            return result > 0;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public boolean updatePersona(Persona persona, Connection con) throws SQLException {

        String sql = "UPDATE Persona SET nombre = ?, apellido = ?, telefono = ? WHERE dni = ?";
        try(PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, persona.getNombre());
            ps.setString(2, persona.getApellidos());
            ps.setString(3, persona.getTelefono());
            ps.setString(4, persona.getCedula());
            int result = ps.executeUpdate();
            ConexionDB.closePreparedStatement(ps);
            return result > 0;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public boolean deletePersona(String dni, Connection con) throws SQLException {

       String sql = "DELETE FROM Persona WHERE dni = ?";
       try(PreparedStatement ps = con.prepareStatement(sql)) {
           ps.setString(1, dni);
           int result = ps.executeUpdate();
           ConexionDB.closePreparedStatement(ps);
           return result > 0;
       } catch (SQLException e) {
           throw new SQLException(e);
       }
    }
}
