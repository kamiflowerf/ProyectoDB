package DAO;

import logico.Area;
import logico.Participante;
import logico.TrabajoCientifico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TrabajoCientificoDAO implements DAO<TrabajoCientifico>{
    @Override
    public TrabajoCientifico get(String Id) throws SQLException {
        Connection con = ConexionDB.getConnection();
        TrabajoCientifico tc = null;

        String sql = "SELECT tc.idTrabajo, tc.nombre, a.idArea, a.nombreArea, par.idParticipante," +
                "par.dni, p.nombre, p.apellido, p.telefono FROM TrabajoCientifico tc" +
                "JOIN Area a ON a.idArea = tc.idArea" +
                "JOIN Participante par ON par.idParticipante = tc.idParticipante" +
                "JOIN Persona p ON p.dni = par.dni" +
                "WHERE tc.idTrabajo = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, Id);
        ResultSet rs = ps.executeQuery();

        if(rs.next()) {
            Area area = new AreaDAO().get(rs.getString("idArea"));
            Participante participante = new ParticipanteDAO().get(rs.getString("idParticipante"));
            tc = new TrabajoCientifico(
                    Id,
                    rs.getString("nombre"),
                    area,
                    participante
            );
        }

        ConexionDB.closeResultSet(rs);
        ConexionDB.closePreparedStatement(ps);
        ConexionDB.closeConnection(con);
        return tc;
    }

    @Override
    public ArrayList<TrabajoCientifico> getAll() throws SQLException {
        Connection con = ConexionDB.getConnection();
        ArrayList<TrabajoCientifico> lista = null;
        String sql = "SELECT tc.idTrabajo, tc.nombre, a.idArea, a.nombreArea, par.idParticipante AS Autor," +
                "par.dni, p.nombre, p.apellido, p.telefono FROM TrabajoCientifico tc" +
                "JOIN Area a ON a.idArea = tc.idArea" +
                "JOIN Participante par ON par.idParticipante = tc.idParticipante" +
                "JOIN Persona p ON p.dni = par.dni";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            Area area = new AreaDAO().get(rs.getString("idArea"));
            Participante participante = new ParticipanteDAO().get(rs.getString("idParticipante"));
            TrabajoCientifico tc = new TrabajoCientifico(
                    rs.getString("idTrabajo"),
                    rs.getString("nombre"),
                    area,
                    participante
            );
            lista.add(tc);
        }
        return lista;
    }


    @Override
    public boolean insert(TrabajoCientifico trabajoCientifico) throws SQLException {
        Connection con = ConexionDB.getConnection();
        PreparedStatement ps = null;
        String sql = "INSERT INTO TrabajoCientifico(idTrabajo,nombre,idArea,idParticipante) VALUES(?,?,?,?)";

        con.setAutoCommit(false);
        ps = con.prepareStatement(sql);
        ps.setString(1, trabajoCientifico.getId());
        ps.setString(2, trabajoCientifico.getNombre());
        ps.setString(3, trabajoCientifico.getArea().getIdArea());
        ps.setString(4, trabajoCientifico.getAutor().getIdParticipante());

        return makeTransaction(con, ps);
    }

    @Override
    public boolean update(TrabajoCientifico trabajoCientifico) throws SQLException {
        Connection con = ConexionDB.getConnection();
        PreparedStatement ps = null;
        String sql = "UPDATE TrabajoCientifico SET nombre = ?, idArea = ? WHERE idTrabajo = ?";

        con.setAutoCommit(false);
        ps = con.prepareStatement(sql);
        ps.setString(1, trabajoCientifico.getNombre());
        ps.setString(2, trabajoCientifico.getArea().getIdArea());
        ps.setString(3, trabajoCientifico.getId());
        return makeTransaction(con, ps);
    }

    @Override
    public boolean delete(TrabajoCientifico trabajoCientifico) throws SQLException {
        Connection con = ConexionDB.getConnection();
        PreparedStatement ps = null;
        String sql = "DELETE FROM TrabajoCientifico WHERE idTrabajo = ?";
        con.setAutoCommit(false);
        ps = con.prepareStatement(sql);
        ps.setString(1, trabajoCientifico.getId());
        return makeTransaction(con, ps);
    }

    private boolean makeTransaction(Connection con, PreparedStatement ps) throws SQLException {
        int result = ps.executeUpdate();
        if(result > 0) {
            con.commit();
            con.setAutoCommit(true);
            ConexionDB.closePreparedStatement(ps);
            ConexionDB.closeConnection(con);
            return true;
        } else {
            con.rollback();
            ConexionDB.closePreparedStatement(ps);
            ConexionDB.closeConnection(con);
            return false;
        }
    }

}
