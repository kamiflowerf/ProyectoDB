package logico;

import java.io.Serializable;

public class Recurso implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String id;
	protected String nombre;
	protected TipoRecurso tipo;
	protected Boolean disponibilidad;
	protected Boolean selected;
	protected Local local;
	
	public Recurso(String id, String nombre, TipoRecurso tipo, Boolean disponibilidad, Local local) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.tipo = tipo;
		this.disponibilidad = disponibilidad;
		this.selected = false;
		this.local = local;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public TipoRecurso getTipo() {
		return tipo;
	}

	public void setTipo(TipoRecurso tipo) {
		this.tipo = tipo;
	}

	public Boolean getDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(Boolean disponibilidad) {
		this.disponibilidad = disponibilidad;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}
}
