package logico;

public class RecursoLocal extends Recurso {

	private String ciudad;
	
	public RecursoLocal(String id, String nombre, String tipo, String ciudad) {
		super(id, nombre, tipo);
		this.ciudad = ciudad;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	
}
