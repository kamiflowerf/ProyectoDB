package DAO;

import logico.Area;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class AreaDAO implements DAO<Area> {
    @Override
    public Area get(String Id) throws SQLException {
        return null;
    }

    @Override
    public List<Area> getAll() throws SQLException {
        return Collections.emptyList();
    }

    @Override
    public int save(Area area) throws SQLException {
        return 0;
    }

    @Override
    public int insert(Area area) throws SQLException {
        return 0;
    }

    @Override
    public int update(Area area) throws SQLException {
        return 0;
    }

    @Override
    public int delete(Area area) throws SQLException {
        return 0;
    }
}
