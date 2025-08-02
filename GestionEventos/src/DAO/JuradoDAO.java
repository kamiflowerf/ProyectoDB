package DAO;

import logico.Jurado;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

// LAS TABLAS AUXILIARES UTILIZAN UN JOIN

public class JuradoDAO implements DAO<Jurado> {
    @Override
    public Jurado get(String Id) throws SQLException {
        return null;
    }

    @Override
    public List<Jurado> getAll() throws SQLException {
        return Collections.emptyList();
    }

    @Override
    public int save(Jurado jurado) throws SQLException {
        return 0;
    }

    @Override
    public int insert(Jurado jurado) throws SQLException {
        return 0;
    }

    @Override
    public int update(Jurado jurado) throws SQLException {
        return 0;
    }

    @Override
    public int delete(Jurado jurado) throws SQLException {
        return 0;
    }
}
