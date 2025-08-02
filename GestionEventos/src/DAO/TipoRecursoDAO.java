package DAO;

import logico.TipoRecurso;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class TipoRecursoDAO implements DAO<TipoRecurso>{
    @Override
    public TipoRecurso get(String Id) throws SQLException {
        return null;
    }

    @Override
    public List<TipoRecurso> getAll() throws SQLException {
        return Collections.emptyList();
    }

    @Override
    public int save(TipoRecurso tipoRecurso) throws SQLException {
        return 0;
    }

    @Override
    public int insert(TipoRecurso tipoRecurso) throws SQLException {
        return 0;
    }

    @Override
    public int update(TipoRecurso tipoRecurso) throws SQLException {
        return 0;
    }

    @Override
    public int delete(TipoRecurso tipoRecurso) throws SQLException {
        return 0;
    }
}
