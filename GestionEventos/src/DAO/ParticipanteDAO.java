package DAO;

import logico.Participante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ParticipanteDAO implements DAO<Participante> {
    @Override
    public Participante get(String Id) throws SQLException {
        Connection con = ConexionDB.getConnection();
        Participante participante = null;

        String sql = "SELECT par.idParticipante, p.dni, p.nombre, p.apellido, p.telefono FROM Participante par " +
                "JOIN Persona p ON p.dni = par.dni " +
                "WHERE idParticipante = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, Id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            participante = new Participante(Id,
                    rs.getString("dni"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("telefono"));
        }

        ConexionDB.closeResultSet(rs);
        ConexionDB.closePreparedStatement(ps);
        ConexionDB.closeConnection(con);
        return participante;
    }

    @Override
    public ArrayList<Participante> getAll() throws SQLException {
        Connection con = ConexionDB.getConnection();
        ArrayList<Participante> lista = new ArrayList<>();
        String sql = "SELECT par.idParticipante, p.dni, p.nombre, p.apellido, p.telefono FROM Participante par " +
                "JOIN Persona p ON p.dni = par.dni";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Participante p = new Participante(
                    rs.getString("idParticipante"),
                    rs.getString("dni"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("telefono")
            );
            lista.add(p);
        }

        ConexionDB.closeResultSet(rs);
        ConexionDB.closePreparedStatement(ps);
        ConexionDB.closeConnection(con);
        return lista;
    }

    @Override
    public boolean insert(Participante participante) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "INSERT INTO Participante(idParticipante,dni) VALUES (?,?)";
        try{
            con = ConexionDB.getConnection();
            con.setAutoCommit(false);

            boolean insertedPerson = new PersonaDAO().insertPersona(participante,con);
            if(!insertedPerson){
                con.rollback();
                return false;
            }
            ps = con.prepareStatement(sql);
            ps.setString(1,participante.getIdParticipante());
            ps.setString(2,participante.getCedula());

            int result = ps.executeUpdate();

            if(result > 0){
                con.commit();
                return true;
            } else {
                con.rollback();
                return false;
            }
        } catch (SQLException e) {
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public boolean update(Participante participante) throws SQLException {
        Connection con = ConexionDB.getConnection();
        con.setAutoCommit(false);
        boolean updatedPerson = new PersonaDAO().updatePersona(participante,con);
        if(!updatedPerson){
            con.rollback();
            ConexionDB.closeConnection(con);
            return false;
        } else {
            con.commit();
            con.setAutoCommit(true);
            ConexionDB.closeConnection(con);
            return true;
        }
    }

    @Override
    public boolean delete(Participante participante) throws SQLException {
        return false;
    }
}
