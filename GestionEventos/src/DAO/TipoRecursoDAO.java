package DAO;

import logico.TipoRecurso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TipoRecursoDAO implements DAO<TipoRecurso>{
    @Override
    public TipoRecurso get(String Id) throws SQLException {
        Connection con = ConexionDB.getConnection();
        TipoRecurso tipoRecurso = null;

        String sql = "SELECT idTipRec, nombre FROM TipoRecurso WHERE idTipRec = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1,Id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            tipoRecurso = new TipoRecurso(Id, rs.getString("nombre"));
        }

        ConexionDB.closeResultSet(rs);
        ConexionDB.closePreparedStatement(ps);
        ConexionDB.closeConnection(con);
        return tipoRecurso;
    }

    @Override
    public ArrayList<TipoRecurso> getAll() throws SQLException {
        Connection con = ConexionDB.getConnection();
        ArrayList<TipoRecurso> lista = new ArrayList<TipoRecurso>();

        String sql = "SELECT idTipRec, nombre FROM TipoRec";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            TipoRecurso t = new TipoRecurso(
              rs.getString("idTipRec"),
              rs.getString("nombre")
            );
            lista.add(t);
        }

        ConexionDB.closeResultSet(rs);
        ConexionDB.closePreparedStatement(ps);
        ConexionDB.closeConnection(con);
        return lista;
    }

    @Override
    public boolean insert(TipoRecurso tipoRecurso) throws SQLException {
        return false;
    }

    @Override
    public boolean update(TipoRecurso tipoRecurso) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(TipoRecurso tipoRecurso) throws SQLException {
        return false;
    }
}
