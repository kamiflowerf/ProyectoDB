package logico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Evento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String titulo;
	private String tipo;
	private Date fecha;
	private Boolean estado;
	private ArrayList<Comision> comisiones;
	private ArrayList<Recurso> recursos;
	
	public Evento(String id, String titulo, String tipo, Date fecha) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.tipo = tipo;
		this.fecha = fecha;
		this.estado = true;
		comisiones = new ArrayList<>();
		recursos = new ArrayList<>();
		this.estado = true;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public ArrayList<Comision> getComisiones() {
		return comisiones;
	}
	public void setComisiones(ArrayList<Comision> comisiones) {
		this.comisiones = comisiones;
	}
	public ArrayList<Recurso> getRecursos() {
		return recursos;
	}
	public void setRecursos(ArrayList<Recurso> recursos) {
		this.recursos = recursos;
	}
	public Boolean getEstado() {
		return estado;
	}
	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
}
