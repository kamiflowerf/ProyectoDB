package logico;

import java.io.Serializable;

public class Recurso implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String id;
	protected String nombre;
	protected String tipo;
	protected Boolean disponibilidad;
	protected Boolean selected;
	
	public Recurso(String id, String nombre, String tipo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.tipo = tipo;
		this.disponibilidad = true;
		this.selected = false;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
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
}
