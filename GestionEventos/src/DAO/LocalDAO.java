package DAO;

import logico.Local;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class LocalDAO implements DAO<Local> {
    @Override
    public Local get(String Id) throws SQLException {
        return null;
    }

    @Override
    public List<Local> getAll() throws SQLException {
        return Collections.emptyList();
    }

    @Override
    public int save(Local local) throws SQLException {
        return 0;
    }

    @Override
    public int insert(Local local) throws SQLException {
        return 0;
    }

    @Override
    public int update(Local local) throws SQLException {
        return 0;
    }

    @Override
    public int delete(Local local) throws SQLException {
        return 0;
    }
}
