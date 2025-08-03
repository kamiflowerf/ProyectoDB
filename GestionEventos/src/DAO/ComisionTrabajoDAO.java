package DAO;

import logico.TrabajoCientifico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ComisionTrabajoDAO {
    public boolean insertRelation(Connection con, String idTrabajo, String idComision) {
        PreparedStatement ps = null;
        try{
            String sql = "INSERT INTO COMISION_TRABAJO(idComision,idTrabajo) VALUES(?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1,idComision);
            ps.setString(2, idTrabajo);
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

    public ArrayList<TrabajoCientifico> getTrabajoPorComision(String idCom) {
        ArrayList<TrabajoCientifico> trabajos = new ArrayList<TrabajoCientifico>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = ConexionDB.getConnection();
            String sql = "SELECT t.idTrabajo FROM TrabajoCientifico t " +
                         "JOIN COMISION_TRABAJO ct ON t.idTrabajo = ct.idTrabajo " +
                         "WHERE ct.idComision = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, idCom);
            rs = ps.executeQuery();

            while (rs.next()) {
                TrabajoCientifico trabajo = new TrabajoCientificoDAO().get(rs.getString("idTrabajo"));
                trabajos.add(trabajo);
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

        return trabajos;
    }

    public void deleteByComission(Connection con, String idComision) throws SQLException {
        try(PreparedStatement ps = con.prepareStatement(
                "DELETE FROM COMISION_TRABAJO WHERE idComision = ?")){
            ps.setString(1, idComision);
            ps.executeUpdate();
        }
    }

    public int deleteRelacionTrabajo(TrabajoCientifico tc, Connection con) throws SQLException {
        String sql = "DELETE FROM COMISION_TRABAJO WHERE idTrabajo = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tc.getId());
            int result = ps.executeUpdate() ;
            ConexionDB.closePreparedStatement(ps);
            return result;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
