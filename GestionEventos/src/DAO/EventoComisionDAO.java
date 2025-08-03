package DAO;

import logico.Comision;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EventoComisionDAO {

    public int deleteRelacionComision(Comision comision, Connection con) throws SQLException {
        String sql = "DELETE FROM EVENTO_COMISION WHERE idComision = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, comision.getCodComision());
            int result = ps.executeUpdate();
            ConexionDB.closePreparedStatement(ps);
            return result;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public void deleteByEvent(Connection con, String idEven) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(
                "DELETE FROM EVENTO_COMISION WHERE idEvento = ?")) {
            ps.setString(1, idEven);
            ps.executeUpdate();
        }
    }

    public ArrayList<Comision> getComisionPorEvento(String idEvento) {
        ArrayList<Comision> comisions = new ArrayList<Comision>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = ConexionDB.getConnection();
            String sql = "SELECT ec.idComision FROM EVENTO_COMISION ec " +
                    "JOIN Comision c ON c.codComision = ec.idComision " +
                    "WHERE ec.idEvento = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, idEvento);
            rs = ps.executeQuery();

            while (rs.next()) {
                Comision com = new ComisionDAO().get(rs.getString("idComision"));
                comisions.add(com);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return comisions;
    }

    public boolean insertRelation(Connection con, String idComision, String idEvento){
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO EVENTO_COMISION(idEvento,idComision) VALUES (?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1,idEvento);
            ps.setString(2, idComision);
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try{
                if(ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
