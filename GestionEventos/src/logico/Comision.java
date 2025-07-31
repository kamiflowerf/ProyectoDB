package logico;

import java.io.Serializable;
import java.util.ArrayList;

public class Comision implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codComision;
	private String nombre;
	private String area;
	private ArrayList<Jurado> jurado;
	private ArrayList<TrabajoCientifico> trabajos;
	private Boolean selected;
	
	public Comision(String codComision, String nombre, String area) {
		super();
		this.codComision = codComision;
		this.nombre = nombre;
		this.area = area;
		jurado = new ArrayList<>();
		trabajos = new ArrayList<>();
		this.selected = false;
	}
	
	public String getCodComision() {
		return codComision;
	}
	public void setCodComision(String codComision) {
		this.codComision = codComision;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public ArrayList<Jurado> getJurado() {
		return jurado;
	}
	public void setJurado(ArrayList<Jurado> jurado) {
		this.jurado = jurado;
	}
	public ArrayList<TrabajoCientifico> getTrabajos() {
		return trabajos;
	}
	public void setTrabajos(ArrayList<TrabajoCientifico> trabajos) {
		this.trabajos = trabajos;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
}
