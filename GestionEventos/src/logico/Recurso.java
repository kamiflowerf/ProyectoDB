package logico;

import java.io.Serializable;

public class Recurso implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String id;
	protected String nombre;
	protected String idTipo;
	protected Boolean disponibilidad;
	protected Boolean selected;
	protected Local local;
	
	public Recurso(String id, String nombre, String idTipo, Boolean disponibilidad, Local local) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.idTipo = idTipo;
		this.disponibilidad = disponibilidad;
		this.selected = selected;
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

	public String getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(String idTipo) {
		this.idTipo = idTipo;
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
