package DAO;

import logico.User;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class UserDAO implements DAO<User> {
    @Override
    public User get(String Id) throws SQLException {
        return null;
    }

    @Override
    public List<User> getAll() throws SQLException {
        return Collections.emptyList();
    }

    @Override
    public int save(User user) throws SQLException {
        return 0;
    }

    @Override
    public int insert(User user) throws SQLException {
        return 0;
    }

    @Override
    public int update(User user) throws SQLException {
        return 0;
    }

    @Override
    public int delete(User user) throws SQLException {
        return 0;
    }
}
