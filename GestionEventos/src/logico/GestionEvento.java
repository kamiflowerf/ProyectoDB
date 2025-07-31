package logico;

import java.io.Serializable;
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
	private ArrayList<User> misUsuarios;
	
	private static User currentUser;
	
	public static GestionEvento gestion = null;
	
	public static int codPersona = 1;
	public static int codTrabajos = 1;
	public static int codComision = 1;
	public static int codEvento = 1;
	public static int codRecursos = 1;
	
	public GestionEvento() {
		super();
		misUsuarios = new ArrayList<>();
		misRecursos = new ArrayList<>();
		misPersonas = new ArrayList<>();
		misTrabajosCientificos = new ArrayList<>();
		misComisiones = new ArrayList<>();
		misEventos = new ArrayList<>();
	}
	
	public static GestionEvento getInstance() {
		if(gestion == null) {
			gestion = new GestionEvento();
		}return gestion;
	}
	
	
	public ArrayList<Persona> getMisPersonas() {
		return misPersonas;
	}
	public void setMisPersonas(ArrayList<Persona> misPersonas) {
		this.misPersonas = misPersonas;
	}
	public ArrayList<TrabajoCientifico> getMisTrabajosCientificos() {
		return misTrabajosCientificos;
	}
	public void setMisTrabajosCientificos(ArrayList<TrabajoCientifico> misTrabajosCientificos) {
		this.misTrabajosCientificos = misTrabajosCientificos;
	}
	public ArrayList<Comision> getMisComisiones() {
		return misComisiones;
	}
	public void setMisComisiones(ArrayList<Comision> misComisiones) {
		this.misComisiones = misComisiones;
	}
	public ArrayList<Evento> getMisEventos() {
		return misEventos;
	}
	public void setMisEventos(ArrayList<Evento> misEventos) {
		this.misEventos = misEventos;
	}
	public ArrayList<Recurso> getMisRecursos() {
		return misRecursos;
	}
	public void setMisRecursos(ArrayList<Recurso> misRecursos) {
		this.misRecursos = misRecursos;
	}
	public ArrayList<User> getMisUsuarios() {
		return misUsuarios;
	}
	public void setMisUsuarios(ArrayList<User> misUsuarios) {
		this.misUsuarios = misUsuarios;
	}
	public User getCurrentUser() {
		return currentUser;
	}
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public void insertarPersonas(Persona obj) {
		misPersonas.add(obj);
		codPersona++;
	}
	
	public void eliminarPersona(Persona obj) {
		misPersonas.remove(obj);
	}
	
	public Persona buscarPersonasCedula(String cedula) {
		for (Persona persona : misPersonas) {
			if(persona.getCedula().equals(cedula)) {
				return persona;
			}
		}return null;
	}
	
	public void insertarTrabajo(TrabajoCientifico obj) {
		misTrabajosCientificos.add(obj);
		codTrabajos++;
	}
	
	public void eliminarTrabajo(TrabajoCientifico obj) {
		misTrabajosCientificos.remove(obj);
	}
	
	public TrabajoCientifico buscarTrabajoID(String cod) {
		for (TrabajoCientifico trabajo : misTrabajosCientificos) {
			if(trabajo.getId().equals(cod)) {
				return trabajo;
			}
		}return null;
	}
	
	public void insertarComision(Comision obj) {
		misComisiones.add(obj);
		codComision++;
	}
	
	public void eliminarComision(Comision obj) {
		misComisiones.remove(obj);
	}
	
	public Comision buscarComisionID(String cod) {
		for (Comision comision : misComisiones) {
			if(comision.getCodComision().equals(cod)) {
				return comision;
			}
		}return null;
	}
	
	public void insertarEvento(Evento obj) {
		misEventos.add(obj);
		codEvento++;
	}
	
	public void eliminarEvento(Evento obj) {
		misEventos.remove(obj);
	}
	
	public Evento buscarEventoID(String cod) {
		for (Evento evento : misEventos) {
			if(evento.getId().equals(cod)) {
				return evento;
			}
		}return null;
	}
	
	public void insertarRecurso(Recurso obj) {
		misRecursos.add(obj);
		codRecursos++;
	}
	
	public void eliminarRecurso(Recurso obj) {
		misRecursos.remove(obj);
	}
	
	public void eliminarUser(User obj) {
		misUsuarios.remove(obj);
	}
	
	public Recurso buscarRecursoID(String cod) {
		for (Recurso recurso : misRecursos) {
			if(recurso.getId().equals(cod)) {
				return recurso;
			}
		}return null;
	}
	
	public User buscarUsuarioUsername(String username) {
		for (User usuario : misUsuarios) {
			if(usuario.getUserName().equals(username)) {
				return usuario;
			}
		}return null;
	}
	
	

	public ArrayList<TrabajoCientifico> getTrabajosPorComision(String codComision) {
		
	    ArrayList<TrabajoCientifico> trabajosEnComision = new ArrayList<>();
	    for (Comision comision : misComisiones) {
	        if (comision.getCodComision().equals(codComision)) {
	            trabajosEnComision.addAll(comision.getTrabajos());
	            break;
	        }
	    }
	    return trabajosEnComision;
	}

	public void asignarTrabajoAComision(TrabajoCientifico trabajo, String codComision) {
	    for (Comision comision : misComisiones) {
	        if (comision.getCodComision().equals(codComision)) {
	            comision.getTrabajos().add(trabajo);
	            break;
	        }
	    }
	}

	public void removerTrabajoDeComision(TrabajoCientifico trabajo, String codComision) {
	    for (Comision comision : misComisiones) {
	        if (comision.getCodComision().equals(codComision)) {
	            comision.getTrabajos().remove(trabajo);
	            break;
	        }
	    }
	}

	public boolean confirmUser(String userName, String pass) {
		for (User usuario : misUsuarios) {
			if(usuario.getUserName().equals(userName) && usuario.getPassword().equals(pass)) {
				currentUser = usuario;
				return true;
			}
		}
		return false;
	}

	public static void setGestion(GestionEvento temp) {
		gestion = temp;
	}
	
	public boolean existeUserName(String userName) {
	    for (User usuario : misUsuarios) {
	        if(usuario.getUserName().equals(userName)) {
	            return true;
	        }
	    }
	    return false;
	}
}


