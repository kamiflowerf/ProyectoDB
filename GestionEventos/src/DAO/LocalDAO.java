package DAO;

import logico.Local;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocalDAO implements DAO<Local> {
    @Override
    public Local get(String Id) throws SQLException {
        Connection con = ConexionDB.getConnection();
        Local local = null;

        String sql = "SELECT idLocal, ciudad FROM Local WHERE idLocal = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1,Id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            local = new Local(Id, rs.getString("ciudad"));
        }

        ConexionDB.closeResultSet(rs);
        ConexionDB.closePreparedStatement(ps);
        ConexionDB.closeConnection(con);
        return local;
    }

    @Override
    public ArrayList<Local> getAll() throws SQLException {
        Connection con = ConexionDB.getConnection();
        ArrayList<Local> lista = new ArrayList<Local>();

        String sql = "SELECT idLocal, ciudad FROM Local";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Local l = new Local(
              rs.getString("idLocal"),
              rs.getString("ciudad")
            );
            lista.add(l);
        }

        ConexionDB.closeResultSet(rs);
        ConexionDB.closePreparedStatement(ps);
        ConexionDB.closeConnection(con);
        return lista;
    }

    @Override
    public boolean insert(Local local) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Local local) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(Local local) throws SQLException {
        return false;
    }
}
