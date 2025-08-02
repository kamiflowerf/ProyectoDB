package DAO;

import logico.Recurso;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class RecursoDAO implements DAO<Recurso>{
    @Override
    public Recurso get(String Id) throws SQLException {
        return null;
    }

    @Override
    public List<Recurso> getAll() throws SQLException {
        return Collections.emptyList();
    }

    @Override
    public int save(Recurso recurso) throws SQLException {
        return 0;
    }

    @Override
    public int insert(Recurso recurso) throws SQLException {
        return 0;
    }

    @Override
    public int update(Recurso recurso) throws SQLException {
        return 0;
    }

    @Override
    public int delete(Recurso recurso) throws SQLException {
        return 0;
    }
}
