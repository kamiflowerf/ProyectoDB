package DAO;

import logico.Persona;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class PersonaDAO  {

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
