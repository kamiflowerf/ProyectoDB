package DAO;

import logico.Recurso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EventoRecursoDAO {

    public int deleteRelacionRecurso(Recurso recurso, Connection con) throws SQLException {
        String sql = "DELETE FROM EVENTO_RECURSO WHERE idRecurso = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, recurso.getId());
            int result = ps.executeUpdate() ;
            ConexionDB.closePreparedStatement(ps);
            return result;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public void deleteByEvent(Connection con, String idEven) throws SQLException{
        try(PreparedStatement ps = con.prepareStatement(
                "DELETE FROM EVENTO_RECURSO WHERE idEvento = ?")){
            ps.setString(1, idEven);
            ps.executeUpdate();
        }
    }

    public ArrayList<Recurso> getRecursoPorEvento(String idEvento) {
        ArrayList<Recurso> recursos = new ArrayList<Recurso>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = ConexionDB.getConnection();
            String sql = "SELECT er.idRecurso FROM EVENTO_RECURSO er " +
                    "JOIN Recurso r ON r.idRecurso = er.idRecurso " +
                    "WHERE er.idEvento = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, idEvento);
            rs = ps.executeQuery();

            while (rs.next()) {
                Recurso rec = new RecursoDAO().get(rs.getString("idRecurso"));
                recursos.add(rec);
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
        return recursos;
    }

    public boolean insertRelation(Connection con, String idRecurso, String idEvento){
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO EVENTO_RECURSO(idEvento,idRecurso) VALUES (?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1,idEvento);
            ps.setString(2, idRecurso);
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
