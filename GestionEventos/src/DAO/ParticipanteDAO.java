package DAO;

import logico.Participante;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class ParticipanteDAO implements DAO<Participante> {
    @Override
    public Participante get(String Id) throws SQLException {
        return null;
    }

    @Override
    public List<Participante> getAll() throws SQLException {
        return Collections.emptyList();
    }

    @Override
    public int save(Participante participante) throws SQLException {
        return 0;
    }

    @Override
    public int insert(Participante participante) throws SQLException {
        return 0;
    }

    @Override
    public int update(Participante participante) throws SQLException {
        return 0;
    }

    @Override
    public int delete(Participante participante) throws SQLException {
        return 0;
    }
}
