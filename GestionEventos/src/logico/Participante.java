package logico;

public class Participante extends Persona {
	private String idParticipante;

	public Participante(String id,String cedula, String nombre, String apellidos, String telefono) {
		super( cedula, nombre, apellidos, telefono);
		this.idParticipante = id;
	}

	public void setIdParticipante(String idParticipante) {
	}

	public String getIdParticipante() {
		return idParticipante;
	}
}


