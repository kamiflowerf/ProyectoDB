package DAO;

import logico.Area;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AreaDAO implements DAO<Area> {
    @Override
    public Area get(String Id) throws SQLException {
        Connection con = ConexionDB.getConnection();
        Area area = null;

        String sql = "SELECT idArea, nombreArea FROM Area WHERE idArea = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1,Id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            area = new Area(Id, rs.getString("nombreArea"));
        }

        ConexionDB.closeResultSet(rs);
        ConexionDB.closePreparedStatement(ps);
        ConexionDB.closeConnection(con);
        return area;
    }

    @Override
    public ArrayList<Area> getAll() throws SQLException {
        Connection con = ConexionDB.getConnection();
        ArrayList<Area> lista = new ArrayList<Area>();

        String sql = "SELECT idArea, nombreArea FROM Area";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Area area = new Area(
              rs.getString("idArea"),
              rs.getString("nombreArea")
            );
            lista.add(area);
        }

        ConexionDB.closeResultSet(rs);
        ConexionDB.closePreparedStatement(ps);
        ConexionDB.closeConnection(con);
        return lista;
    }

    public List<String> getAllNames() throws SQLException{
        Connection con = ConexionDB.getConnection();
        List<String> nombres =  new ArrayList<>();

        String sql = "SELECT nombreArea FROM Area";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            nombres.add(rs.getString("nombreArea"));
        }
        ConexionDB.closeResultSet(rs);
        ConexionDB.closePreparedStatement(ps);
        ConexionDB.closeConnection(con);
        return nombres;
    }

    @Override
    public boolean insert(Area area) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Area area) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(Area area) throws SQLException {
        return false;
    }
}
