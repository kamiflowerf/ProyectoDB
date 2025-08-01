package logico;

import java.io.Serializable;

public abstract class Persona implements Serializable {
	
	/**
	 * 
	 */


	private static final long serialVersionUID = 1L;
	protected String dni;
	protected String nombre;
	protected String apellidos;
	protected String telefono;
	
	public Persona( String cedula, String nombre, String apellidos, String telefono) {
		super();
		this.dni = cedula;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.telefono = telefono;
	}

	public String getCedula() {
		return dni;
	}
	public void setCedula(String cedula) {
		this.dni = cedula;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
}
	
	


