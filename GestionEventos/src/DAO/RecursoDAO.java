package DAO;

import logico.Local;
import logico.Recurso;
import logico.TipoRecurso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RecursoDAO implements DAO<Recurso>{
    @Override
    public Recurso get(String Id) throws SQLException {
        Connection con = ConexionDB.getConnection();
        Recurso recurso = null;

        String sql = "SELECT nombre,disponibilidad,idTipRec,idLocal FROM Recurso WHERE idRecurso = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, Id);
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            Local local = new LocalDAO().get(rs.getString("idLocal"));
            TipoRecurso trec = new TipoRecursoDAO().get(rs.getString("idTipRec"));
            recurso = new Recurso(
                    Id,
                    rs.getString("nombre"),
                    trec,
                    rs.getBoolean("disponibilidad"),
                    local
            );
        }

        ConexionDB.closeResultSet(rs);
        ConexionDB.closePreparedStatement(ps);
        ConexionDB.closeConnection(con);
        return recurso;
    }

    @Override
    public ArrayList<Recurso> getAll() throws SQLException {
        Connection con = ConexionDB.getConnection();
        ArrayList<Recurso> recursos = new ArrayList<>();
        String sql = "SELECT idRecurso,nombre,disponibilidad,idTipRec,idLocal FROM Recurso";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            Local local = new LocalDAO().get(rs.getString("idLocal"));
            TipoRecurso trec = new TipoRecursoDAO().get(rs.getString("idTipRec"));
            Recurso recurso = new Recurso(
                    rs.getString("idRecurso"),
                    rs.getString("nombre"),
                    trec,
                    rs.getBoolean("disponibilidad"),
                    local
            );
            recursos.add(recurso);
        }

        ConexionDB.closeResultSet(rs);
        ConexionDB.closePreparedStatement(ps);
        ConexionDB.closeConnection(con);
        return recursos;
    }

    @Override
    public boolean insert(Recurso recurso) throws SQLException {
        Connection con = ConexionDB.getConnection();
        String sql = "INSERT INTO Recurso(idRecurso,nombre,disponibilidad,idTipRec,idLocal) VALUES(?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, recurso.getId());
        ps.setString(2, recurso.getNombre());
        ps.setBoolean(3, recurso.getDisponibilidad());
        ps.setString(4,recurso.getTipo().getIdTipRec());
        ps.setString(5, recurso.getLocal().getIdLocal());

        int result = ps.executeUpdate();
        ConexionDB.closePreparedStatement(ps);
        ConexionDB.closeConnection(con);
        return result > 0;
    }

    @Override
    public boolean update(Recurso recurso) throws SQLException {
        Connection con = ConexionDB.getConnection();
        String sql = "UPDATE Recurso SET nombre = ?, disponibilidad = ? WHERE idRecurso = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, recurso.getNombre());
        ps.setBoolean(2, recurso.getDisponibilidad());
        ps.setString(3, recurso.getId());
        int result = ps.executeUpdate();

        ConexionDB.closePreparedStatement(ps);
        ConexionDB.closeConnection(con);
        return result > 0;
    }

    @Override
    public boolean delete(Recurso recurso) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = ConexionDB.getConnection();
            con.setAutoCommit(false);

            EventoRecursoDAO erDAO = new EventoRecursoDAO();
            erDAO.deleteRelacionRecurso(recurso,con);

            String sql = "DELETE FROM Recurso WHERE idRecurso = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, recurso.getId());
            int result = ps.executeUpdate();
            if(result == 0){
                con.rollback();
                return false;
            }

            con.commit();
            return true;

        } catch (SQLException e){
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
}
