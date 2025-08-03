package DAO;

import logico.TipoEvento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipoEventoDAO implements DAO<TipoEvento>{
    @Override
    public TipoEvento get(String Id) throws SQLException {
        Connection con = ConexionDB.getConnection();
        TipoEvento tipoEvento = null;

        String sql = "SELECT idTipEven, nombre FROM TipoEvento WHERE idTipEven = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1,Id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            tipoEvento = new TipoEvento(Id, rs.getString("nombre"));
        }

        ConexionDB.closeResultSet(rs);
        ConexionDB.closePreparedStatement(ps);
        ConexionDB.closeConnection(con);
        return tipoEvento;
    }

    @Override
    public ArrayList<TipoEvento> getAll() throws SQLException {
       Connection con = ConexionDB.getConnection();
        ArrayList<TipoEvento> lista = new ArrayList<TipoEvento>();

        String sql = "SELECT idTipEven, nombre FROM TipoEvento";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            TipoEvento t = new TipoEvento(
              rs.getString("idTipEven"),
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
    public boolean insert(TipoEvento tipoEvento) throws SQLException {
        return false;
    }

    @Override
    public boolean update(TipoEvento tipoEvento) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(TipoEvento tipoEvento) throws SQLException {
        return false;
    }
}
