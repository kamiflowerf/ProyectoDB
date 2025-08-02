package logico;

public class TipoRecurso {
    private String idTipRec;
    private String nombre;

    public TipoRecurso(String idTipRec, String nombre) {
        this.idTipRec = idTipRec;
        this.nombre = nombre;
    }

    public String getIdTipRec() {
        return idTipRec;
    }
    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}
}
