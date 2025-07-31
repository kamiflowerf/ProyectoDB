package logico;

public class Jurado extends Persona {
	
	private String area;
	private boolean seleccionado;

	public Jurado(String id, String cedula, String nombre, String apellidos, String telefono, String area) {
		super(id, cedula, nombre, apellidos, telefono);
		this.area = area;
		this.seleccionado = false;
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
