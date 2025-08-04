package DAO;

import logico.*;

import java.sql.*;
import java.util.ArrayList;

public class ComisionDAO implements DAO<Comision>{
    @Override
    public Comision get(String Id) throws SQLException {
        Connection con = ConexionDB.getConnection();
        Comision comision = null;
        String sql = "SELECT c.codComision, c.nombre AS nameCom, a.idArea, a.nombreArea, e.titulo FROM Comision c " +
                "JOIN Area a ON a.idArea = c.idArea " +
                "JOIN EVENTO_COMISION ec ON ec.idComision = c.codComision " +
                "JOIN Evento e ON e.idEvento = ec.idEvento " +
                "WHERE codComision = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, Id);
        ResultSet rs = ps.executeQuery();

        if(rs.next()) {
            Area area = new AreaDAO().get(rs.getString("idArea"));
            comision = new Comision(Id,
                    rs.getString("nameCom"),
                    area
                    );

            ComisionJuradoDAO cjDao = new ComisionJuradoDAO();
            ArrayList<Jurado> jurados = cjDao.getJuradosPorComision(Id);
            comision.setJurado(jurados);
            ComisionTrabajoDAO ctDao = new ComisionTrabajoDAO();
            ArrayList<TrabajoCientifico> trabajos = ctDao.getTrabajoPorComision(Id);
            comision.setTrabajos(trabajos);

            ConexionDB.closeResultSet(rs);
            ConexionDB.closePreparedStatement(ps);
            ConexionDB.closeConnection(con);
        }
        return comision;
    }

    @Override
    public ArrayList<Comision> getAll() throws SQLException {
        ArrayList<Comision> comisiones = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = ConexionDB.getConnection();
            String sql = "SELECT c.codComision, c.nombre AS nameCom, a.idArea,a.nombreArea, e.titulo FROM Comision c " +
                                    "JOIN Area a ON a.idArea = c.idArea " +
                                    "JOIN EVENTO_COMISION ec ON ec.idComision = c.codComision " +
                                    "JOIN Evento e ON e.idEvento = ec.idEvento";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ComisionJuradoDAO cjDAO = new ComisionJuradoDAO();
            ComisionTrabajoDAO ctDAO = new ComisionTrabajoDAO();

            while (rs.next()) {
                Area area = new AreaDAO().get(rs.getString("idArea"));
                String idComision = rs.getString("codComision");
                Comision comision = new Comision(
                        idComision,
                        rs.getString("nameCom"),
                        area
                );
                // Obtener jurados
                ArrayList<Jurado> jurados = cjDAO.getJuradosPorComision(idComision);
                comision.setJurado(jurados);
                // Obtener trabajos
                ArrayList<TrabajoCientifico> trabajos = ctDAO.getTrabajoPorComision(idComision);
                comision.setTrabajos(trabajos);

                comisiones.add(comision);
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

        return comisiones;
    }

    @Override
    public boolean insert(Comision comision) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        try{
            con = ConexionDB.getConnection();
            con.setAutoCommit(false);
            //INSERT COMISSION
            String sql = "INSERT INTO Comision(codComision,nombre,idArea) VALUES(?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, comision.getCodComision());
            ps.setString(2, comision.getNombre());
            ps.setString(3, comision.getArea().getIdArea());
            int filas = ps.executeUpdate();
            if(filas == 0) {
                con.rollback();
                return false;
            }

            //INSERT ASSOCIATED JURYS
            ComisionJuradoDAO cjDao = new ComisionJuradoDAO();
            for(Jurado j : comision.getJurado()){
                boolean ok = cjDao.insertRelation(con,j.getIdJurado(),comision.getCodComision());
                if(!ok){
                    con.rollback();
                    return false;
                }
            }

            //INSERT ASSOCIATED WORKS
            ComisionTrabajoDAO ctDao = new ComisionTrabajoDAO();
            for(TrabajoCientifico t : comision.getTrabajos()) {
                boolean ok = ctDao.insertRelation(con,t.getId(), comision.getCodComision());
                if(!ok){
                    con.rollback();
                    return false;
                }
            }

            con.commit();
            return true;
        } catch (SQLException e){
            try{
                if(con != null){ con.rollback(); }
            } catch (SQLException e2){
                e2.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try{
                if(ps != null) ps.close();
                if(con != null){
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean update(Comision comision) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        try{
            con = ConexionDB.getConnection();
            con.setAutoCommit(false);

            String sql = "UPDATE Comision SET nombre = ? WHERE codComision = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, comision.getNombre());
            ps.setString(2, comision.getCodComision());
            int filas = ps.executeUpdate();
            ConexionDB.closePreparedStatement(ps);

            if(filas == 0) {
                con.rollback();
                return false;
            }

            ComisionJuradoDAO cjDao = new ComisionJuradoDAO();
            cjDao.deleteByComission(con,comision.getCodComision());
            ComisionTrabajoDAO ctDao = new ComisionTrabajoDAO();
            ctDao.deleteByComission(con,comision.getCodComision());

            for(Jurado j : comision.getJurado()){
                cjDao.insertRelation(con,j.getIdJurado(),comision.getCodComision());
            }
            for(TrabajoCientifico t : comision.getTrabajos()){
                ctDao.insertRelation(con,t.getId(), comision.getCodComision());
            }

            con.commit();
            return true;
        } catch (SQLException e){
            try{
                if(con != null){ con.rollback(); }
            } catch (SQLException e2){
                e2.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try{
                if(con != null){
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean delete(Comision comision) throws SQLException {
        String idCom = comision.getCodComision();
        Connection con = null;
        PreparedStatement ps = null;

        try{
            con = ConexionDB.getConnection();
            con.setAutoCommit(false);

            ComisionJuradoDAO cjDao = new ComisionJuradoDAO();
            cjDao.deleteByComission(con, idCom);

            ComisionTrabajoDAO ctDao = new ComisionTrabajoDAO();
            ctDao.deleteByComission(con, idCom);

            String sql = "DELETE FROM Comision WHERE codComision = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, idCom);
            int filas = ps.executeUpdate();

            if(filas == 0) {
                con.rollback();
                return false;
            }
            con.commit();
            return true;
        } catch (SQLException e){
            try{
                if(con != null){ con.rollback(); }
            } catch (SQLException e2){
                e2.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try{
                if(ps != null) ps.close();
                if(con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
