package logico;

import java.io.Serializable;

public class TrabajoCientifico implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String nombre;
	private Area area;
	private Participante autor;	
	private boolean seleccionado;
	
	public TrabajoCientifico(String id, String nombre, Area area, Participante autor) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.area = area;
		this.autor = autor;
		this.seleccionado = false;
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
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public Participante getAutor() {
		return autor;
	}
	public void setAutor(Participante autor) {
		this.autor = autor;
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}
	
	

}
