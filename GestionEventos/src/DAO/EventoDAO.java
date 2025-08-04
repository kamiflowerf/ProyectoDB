package DAO;

import logico.Comision;
import logico.Evento;
import logico.Recurso;
import logico.TipoEvento;

import java.sql.*;
import java.util.ArrayList;

public class EventoDAO implements DAO<Evento>{
    @Override
    public Evento get(String Id) throws SQLException {
        Connection con = ConexionDB.getConnection();
        Evento evento = null;

        String sql = "SELECT e.idEvento, e.titulo, e.fecha, e.estado, te.idTipEven, te.nombre AS Tipo FROM Evento e " +
                "JOIN TipoEvento te ON te.idTipEven = e.idTipEven " +
                "WHERE idEvento = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, Id);
        ResultSet rs = ps.executeQuery();

        if(rs.next()) {
            TipoEvento tipo = new TipoEventoDAO().get(rs.getString("idTipEven"));
            evento = new Evento(
                    Id,
                    rs.getString("titulo"),
                    tipo,
                    rs.getDate("fecha")
            );
            evento.setEstado(rs.getBoolean("estado"));
            EventoComisionDAO ecDAO = new EventoComisionDAO();
            ArrayList<Comision> comisions = ecDAO.getComisionPorEvento(rs.getString("idEvento"));
            evento.setComisiones(comisions);
            EventoRecursoDAO erDAO = new EventoRecursoDAO();
            ArrayList<Recurso> recursos = erDAO.getRecursoPorEvento(rs.getString("idEvento"));
            evento.setRecursos(recursos);

            ConexionDB.closeResultSet(rs);
            ConexionDB.closePreparedStatement(ps);
            ConexionDB.closeConnection(con);
        }

        return evento;
    }

    @Override
    public ArrayList<Evento> getAll() throws SQLException {
        ArrayList<Evento> eventos = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = ConexionDB.getConnection();
            String sql = "SELECT e.idEvento, e.titulo, e.fecha, e.estado, te.idTipEven, te.nombre AS Tipo FROM Evento e " +
                    "JOIN TipoEvento te ON te.idTipEven = e.idTipEven";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            EventoComisionDAO ecDAO = new EventoComisionDAO();
            EventoRecursoDAO erDAO = new EventoRecursoDAO();

            while (rs.next()) {
                String idEvento = rs.getString("idEvento");
                TipoEvento tipo = new TipoEventoDAO().get(rs.getString("idTipEven"));
                Evento evento = new Evento(
                        idEvento,
                        rs.getString("titulo"),
                        tipo,
                        rs.getDate("fecha")
                );
                evento.setEstado(rs.getBoolean("estado"));
                // Obtener jurados
                ArrayList<Comision> comisions = ecDAO.getComisionPorEvento(rs.getString("idEvento"));
                evento.setComisiones(comisions);
                // Obtener trabajos
                ArrayList<Recurso> recursos = erDAO.getRecursoPorEvento(rs.getString("idEvento"));
                evento.setRecursos(recursos);

                eventos.add(evento);
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

        return eventos;
    }

    @Override
    public boolean insert(Evento evento) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        try{
            con = ConexionDB.getConnection();
            con.setAutoCommit(false);

            String sql = "INSERT INTO Evento(idEvento,titulo,idTipEven,fecha) VALUES(?,?,?,?)";
            ps = con.prepareStatement(sql);
            TipoEventoDAO teDAO = new TipoEventoDAO();

            ps.setString(1, evento.getId());
            ps.setString(2, evento.getTitulo());
            ps.setString(3, evento.getTipo().getIdTipEven());
            ps.setDate(4, (Date) evento.getFecha());
            int filas = ps.executeUpdate();
            if(filas == 0) {
                con.rollback();
                return false;
            }

            //INSERT ASSOCIATED COMISIONS
            EventoComisionDAO ecDao = new EventoComisionDAO();
            for(Comision c : evento.getComisiones()){
                boolean ok = ecDao.insertRelation(con,c.getCodComision(),evento.getId());
                if(!ok){
                    con.rollback();
                    return false;
                }
            }

            //INSERT ASSOCIATED RESOURCES
            EventoRecursoDAO erDao = new EventoRecursoDAO();
            for(Recurso r : evento.getRecursos()) {
                boolean ok = erDao.insertRelation(con,r.getId(), evento.getId());
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
    public boolean update(Evento evento) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        try{
            con = ConexionDB.getConnection();
            con.setAutoCommit(false);

            String sql = "UPDATE Evento SET titulo = ?, estado = ?, fecha = ? WHERE idEvento = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, evento.getTitulo());
            ps.setBoolean(2, evento.getEstado());
            ps.setDate(3, (Date) evento.getFecha());
            ps.setString(4, evento.getId());
            int filas = ps.executeUpdate();
            ConexionDB.closePreparedStatement(ps);

            if(filas == 0) {
                con.rollback();
                return false;
            }

            EventoComisionDAO ecDao = new EventoComisionDAO();
            ecDao.deleteByEvent(con,evento.getId());
            EventoRecursoDAO erDao = new EventoRecursoDAO();
            erDao.deleteByEvent(con, evento.getId());

            for(Comision c : evento.getComisiones()){
                ecDao.insertRelation(con,c.getCodComision(),evento.getId());
            }
            for(Recurso r : evento.getRecursos()){
                erDao.insertRelation(con,r.getId(), evento.getId());
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
    public boolean delete(Evento evento) throws SQLException {
        String idEven = evento.getId();
        Connection con = null;
        PreparedStatement ps = null;

        try{
            con = ConexionDB.getConnection();
            con.setAutoCommit(false);

            EventoComisionDAO ecDao = new EventoComisionDAO();
            ecDao.deleteByEvent(con, idEven);

            EventoRecursoDAO erDao = new EventoRecursoDAO();
            erDao.deleteByEvent(con, idEven);

            String sql = "DELETE FROM Evento WHERE idEvento = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, idEven);
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
