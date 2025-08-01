package logico;

public class Jurado extends Persona {
	
	private String area;
	private boolean seleccionado;
	private String idJurado;

	public Jurado(String id, String cedula, String nombre, String apellidos, String telefono, String area) {
		super(cedula, nombre, apellidos, telefono);
		this.area = area;
		this.seleccionado = false;
		this.idJurado = id;
	}

	public void setIdJurado(String idJurado) {
		this.idJurado = idJurado;
	}

	public String getIdJurado() {
		return idJurado;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}
	
	
}
