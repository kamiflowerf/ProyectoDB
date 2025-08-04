package DAO;

import logico.Area;
import logico.Jurado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JuradoDAO implements DAO<Jurado> {
    @Override
    public Jurado get(String Id) throws SQLException {
        Connection con = ConexionDB.getConnection();
        Jurado jurado = null;

        String sql = "SELECT j.idJurado, p.dni, p.nombre, p.apellido, j.idArea, p.telefono, a.nombreArea FROM Jurado j " +
                "JOIN Persona p ON p.dni = j.dni " +
                "JOIN Area a ON a.idArea = j.idArea " +
                "WHERE idJurado = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, Id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Area area = new AreaDAO().get(rs.getString("idArea"));
            jurado = new Jurado(Id,
                    rs.getString("dni"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("telefono"),
                    area);
        }

        ConexionDB.closeResultSet(rs);
        ConexionDB.closePreparedStatement(ps);
        ConexionDB.closeConnection(con);
        return jurado;
    }

    @Override
    public ArrayList<Jurado> getAll() throws SQLException {
        Connection con = ConexionDB.getConnection();
        ArrayList<Jurado> jurados = new ArrayList<>();
        String sql = "SELECT j.idJurado, p.dni, p.nombre, p.apellido, j.idArea, p.telefono, a.nombreArea FROM Jurado j " +
                "JOIN Persona p ON p.dni = j.dni " +
                "JOIN Area a ON a.idArea = j.idArea";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Area area = new AreaDAO().get(rs.getString("idArea"));
            Jurado j = new Jurado(
                    rs.getString("idJurado"),
                    rs.getString("dni"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("telefono"),
                    area
            );
            jurados.add(j);
        }

        ConexionDB.closeResultSet(rs);
        ConexionDB.closePreparedStatement(ps);
        ConexionDB.closeConnection(con);
        return jurados;
    }

    @Override
    public boolean insert(Jurado jurado) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "INSERT INTO Jurado(idJurado,dni,idArea) VALUES (?,?,?)";
        try{
            con = ConexionDB.getConnection();
            con.setAutoCommit(false);

            boolean insertedPerson = new PersonaDAO().insertPersona(jurado,con);
            if(!insertedPerson){
                con.rollback();
                return false;
            }
            ps = con.prepareStatement(sql);
            ps.setString(1,jurado.getIdJurado());
            ps.setString(2,jurado.getCedula());
            ps.setString(3,jurado.getArea().getIdArea());
            int result = ps.executeUpdate();

            if(result > 0){
                con.commit();
                return true;
            } else {
                con.rollback();
                return false;
            }
        } catch (SQLException e) {
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

    //CON TRANSACCION
    @Override
    public boolean update(Jurado jurado) throws SQLException {
        Connection con = null;
        PreparedStatement psJurado = null;
        String sqlJurado = "UPDATE Jurado SET idArea = ? WHERE idJurado = ?";

        try {
            con = ConexionDB.getConnection();
            con.setAutoCommit(false); //Iniciar transaccion
            //UPDATE Persona
            boolean updatedPerson = new PersonaDAO().updatePersona(jurado, con);
            if (!updatedPerson) {
                con.rollback();
                return false;
            }
            //UPDATE JURADO
            psJurado = con.prepareStatement(sqlJurado);
            psJurado.setString(1, jurado.getArea().getIdArea());
            psJurado.setString(2, jurado.getIdJurado());
            int result = psJurado.executeUpdate();
            if (result > 0) {
                con.commit();
                return true;
            } else {
                con.rollback();
                return false;
            }
        } catch (SQLException e) {
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (psJurado != null) psJurado.close();
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public boolean delete(Jurado jurado) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = ConexionDB.getConnection();
            con.setAutoCommit(false);

            //eliminar relaciones COMISION_JURADO
            ComisionJuradoDAO cjdao = new ComisionJuradoDAO();
            cjdao.deleteRelacionJurado(jurado,con);

            String dniJurado = jurado.getCedula();

            //eliminar jurado
            String sql = "DELETE FROM Jurado WHERE idJurado = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, jurado.getIdJurado());
            int filasJurado = ps.executeUpdate();

            if(filasJurado == 0){
                con.rollback();
                return false;
            }

            //eliminar persona
            boolean deletedPerson = new PersonaDAO().deletePersona(dniJurado, con);
            if(!deletedPerson){
                con.rollback();
                return false;
            }

            con.commit();
            return true;
        }catch (SQLException e) {
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
