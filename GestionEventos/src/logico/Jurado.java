package logico;

public class Jurado extends Persona {
	
	private Area area;
	private boolean seleccionado;
	private String idJurado;

	public Jurado(String id, String cedula, String nombre, String apellidos, String telefono, Area area) {
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

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}
	
	
}
