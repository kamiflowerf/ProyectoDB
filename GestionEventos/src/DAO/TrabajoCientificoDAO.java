package DAO;

import logico.TrabajoCientifico;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class TrabajoCientificoDAO implements DAO<TrabajoCientifico>{
    @Override
    public TrabajoCientifico get(String Id) throws SQLException {
        return null;
    }

    @Override
    public List<TrabajoCientifico> getAll() throws SQLException {
        return Collections.emptyList();
    }

    @Override
    public int save(TrabajoCientifico trabajoCientifico) throws SQLException {
        return 0;
    }

    @Override
    public int insert(TrabajoCientifico trabajoCientifico) throws SQLException {
        return 0;
    }

    @Override
    public int update(TrabajoCientifico trabajoCientifico) throws SQLException {
        return 0;
    }

    @Override
    public int delete(TrabajoCientifico trabajoCientifico) throws SQLException {
        return 0;
    }
}
