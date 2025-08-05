package logico;

import DAO.*;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

public class GestionEvento implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Persona> misPersonas;
	private ArrayList<TrabajoCientifico> misTrabajosCientificos;
	private ArrayList<Comision> misComisiones;
	private ArrayList<Evento> misEventos;
	private ArrayList<Recurso> misRecursos;
	private ArrayList<Jurado> misJurados;
	private ArrayList<Participante> misParticipantes;
	private TrabajoCientificoDAO trabajoDAO;
	private ComisionDAO comisionDAO;
	private EventoDAO eventoDAO;
	private PersonaDAO personaDAO;
	private RecursoDAO recursoDAO;
	private UserDAO usuarioDAO;
	private JuradoDAO juradoDAO;
	private ParticipanteDAO participanteDAO;

	private static User currentUser;
	
	public static GestionEvento gestion = null;
	
	public GestionEvento() throws SQLException {
		super();
		trabajoDAO = new TrabajoCientificoDAO();
		comisionDAO = new ComisionDAO();
		eventoDAO = new EventoDAO();
		recursoDAO = new RecursoDAO();
		usuarioDAO = new UserDAO();
		juradoDAO = new JuradoDAO();
		personaDAO = new PersonaDAO();
		participanteDAO = new ParticipanteDAO();

		misRecursos = new ArrayList<>(recursoDAO.getAll());
		misJurados = new ArrayList<>(juradoDAO.getAll());
		misParticipantes = new ArrayList<>(participanteDAO.getAll());
		misTrabajosCientificos = new ArrayList<>(trabajoDAO.getAll());
		misComisiones = new ArrayList<>(comisionDAO.getAll());
		misEventos = new ArrayList<>(eventoDAO.getAll());

		if (usuarioDAO.getAll().isEmpty()) {
			User admin = new User("admin", "Admin", "General", Seguridad.sha256("admin123"), "Administrador");
			usuarioDAO.insert(admin);
		}
	}
	
	public static GestionEvento getInstance() throws SQLException {
		if(gestion == null) {
			gestion = new GestionEvento();
		}return gestion;
	}
	public ArrayList<Persona> getMisPersonas() {
		return misPersonas;
	}

	public ArrayList<Jurado> getMisJurados(){
        try {
            return juradoDAO.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
	public ArrayList<TrabajoCientifico> getMisTrabajosCientificos() {
        try {
            return trabajoDAO.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
	public ArrayList<Comision> getMisComisiones() {
        try {
            return comisionDAO.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
	public ArrayList<Evento> getMisEventos() {
        try {
            return eventoDAO.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
	public ArrayList<Recurso> getMisRecursos() {
        try {
            return recursoDAO.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
	public ArrayList<User> getMisUsuarios() {
        try {
            return usuarioDAO.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
	public User getCurrentUser() {
		return currentUser;
	}

	public void insertarPersonas(Persona obj) throws SQLException {
		if(obj instanceof Jurado){
			juradoDAO.insert((Jurado) obj);
			misJurados.add((Jurado)obj);
		} else if(obj instanceof Participante) {
			participanteDAO.insert((Participante) obj);
			misParticipantes.add((Participante)obj);
		}
	}
	
	public void eliminarPersona(Persona obj) {
		if(obj instanceof Jurado){
            try {
                juradoDAO.delete((Jurado) obj);
				misJurados.remove((Jurado)obj);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
		} else if(obj instanceof Participante) {
            try {
                participanteDAO.delete((Participante) obj);
				misParticipantes.remove((Participante)obj);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
		}
	}
	
	public Persona buscarPersonasCedula(String cedula) {
		return personaDAO.getPersonByDni(cedula);
	}

	public Participante buscarParticipanteId(String Id){
        try {
            return participanteDAO.get(Id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
	
	public void insertarTrabajo(TrabajoCientifico obj) throws SQLException {
		trabajoDAO.insert(obj);
		misTrabajosCientificos.add(obj);
	}

	public void insertarUser(User user) throws SQLException{
		usuarioDAO.insert(user);
	}

	public TrabajoCientifico buscarTrabajoID(String cod) {
        try {
            return trabajoDAO.get(cod);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
	
	public void insertarComision(Comision obj) throws SQLException {
		comisionDAO.insert(obj);
		misComisiones.add(obj);
	}
	
	public Comision buscarComisionID(String cod) {
        try {
            return comisionDAO.get(cod);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
	
	public void insertarEvento(Evento obj) throws SQLException {
		eventoDAO.insert(obj);
		misEventos.add(obj);
	}
	
	public void eliminarEvento(Evento obj) {
        try {
            eventoDAO.delete(obj);
			misEventos.remove(obj);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}
	
	public Evento buscarEventoID(String cod) {
		for (Evento evento : misEventos) {
			if(evento.getId().equals(cod)) {
				return evento;
			}
		}return null;
	}
	
	public void insertarRecurso(Recurso obj) {
        try {
            recursoDAO.insert(obj);
			misRecursos.add(obj);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

	}
	
	public void eliminarRecurso(Recurso obj) {
        try {
            recursoDAO.delete(obj);
			misRecursos.remove(obj);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}
	
	public void eliminarUser(User obj) {
        try {
            usuarioDAO.delete(obj);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}
	
	public Recurso buscarRecursoID(String cod) {
        try {
            return recursoDAO.get(cod);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
	
	public User buscarUsuarioUsername(String username) {
        try {
            return usuarioDAO.get(username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

	public boolean confirmUser(String userName, String pass) {
		User usuario = usuarioDAO.autenticarUsuario(userName,pass);
		if (usuario != null) {
			currentUser = usuario;
			return true;
		}
		return false;
	}
	
	public boolean existeUserName(String userName) {
        User user = null;
        try {
            user = usuarioDAO.get(userName);
			if(user != null) {
				return true;
			}

		} catch (SQLException e) {
            throw new RuntimeException(e);
        }
		return false;
	}
}


