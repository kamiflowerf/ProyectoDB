package DAO;

import logico.Persona;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class PersonaDAO implements DAO<Persona> {
    @Override
    public Persona get(String dni) throws SQLException {
        return null;
    }

    @Override
    public List<Persona> getAll() throws SQLException {
        return Collections.emptyList();
    }

    @Override
    public int save(Persona persona) throws SQLException {
        return 0;
    }

    @Override
    public int insert(Persona persona) throws SQLException {
        return 0;
    }

    @Override
    public int update(Persona persona) throws SQLException {
        return 0;
    }

    @Override
    public int delete(Persona persona) throws SQLException {
        return 0;
    }
}
