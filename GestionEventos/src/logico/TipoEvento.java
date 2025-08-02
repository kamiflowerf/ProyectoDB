package logico;

public class TipoEvento {
    private String idTipEven;
    private String nombre;

    public TipoEvento(String idTipEven, String nombre) {
        this.idTipEven = idTipEven;
        this.nombre = nombre;
    }

    public String getIdTipEven() {
        return idTipEven;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
