package DAO;

import logico.Evento;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class EventoDAO implements DAO<Evento>{
    @Override
    public Evento get(String Id) throws SQLException {
        return null;
    }

    @Override
    public List<Evento> getAll() throws SQLException {
        return Collections.emptyList();
    }

    @Override
    public int save(Evento evento) throws SQLException {
        return 0;
    }

    @Override
    public int insert(Evento evento) throws SQLException {
        return 0;
    }

    @Override
    public int update(Evento evento) throws SQLException {
        return 0;
    }

    @Override
    public int delete(Evento evento) throws SQLException {
        return 0;
    }
}
