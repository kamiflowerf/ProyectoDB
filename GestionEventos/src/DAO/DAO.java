package DAO;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {
    public T get(String Id) throws SQLException;
    public List<T> getAll() throws SQLException;
    public int save(T t) throws SQLException;
    public int insert(T t) throws SQLException;
    public int update(T t) throws SQLException;
    public int delete(T t) throws SQLException;
}
