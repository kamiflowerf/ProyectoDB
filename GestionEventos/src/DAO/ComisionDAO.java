package DAO;

import logico.Comision;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class ComisionDAO implements DAO<Comision>{
    @Override
    public Comision get(String Id) throws SQLException {
        return null;
    }

    @Override
    public List<Comision> getAll() throws SQLException {
        return Collections.emptyList();
    }

    @Override
    public int save(Comision comision) throws SQLException {
        return 0;
    }

    @Override
    public int insert(Comision comision) throws SQLException {
        return 0;
    }

    @Override
    public int update(Comision comision) throws SQLException {
        return 0;
    }

    @Override
    public int delete(Comision comision) throws SQLException {
        return 0;
    }
}
