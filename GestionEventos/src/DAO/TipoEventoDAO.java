package DAO;

import logico.TipoEvento;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class TipoEventoDAO implements DAO<TipoEvento>{
    @Override
    public TipoEvento get(String Id) throws SQLException {
        return null;
    }

    @Override
    public List<TipoEvento> getAll() throws SQLException {
        return Collections.emptyList();
    }

    @Override
    public int save(TipoEvento tipoEvento) throws SQLException {
        return 0;
    }

    @Override
    public int insert(TipoEvento tipoEvento) throws SQLException {
        return 0;
    }

    @Override
    public int update(TipoEvento tipoEvento) throws SQLException {
        return 0;
    }

    @Override
    public int delete(TipoEvento tipoEvento) throws SQLException {
        return 0;
    }
}
