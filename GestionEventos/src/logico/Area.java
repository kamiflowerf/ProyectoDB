package logico;

public class Area {
    private String idArea;
    private String nombre;

    public Area(String idArea, String nombre) {
        this.idArea = idArea;
        this.nombre = nombre;
    }

    public String getIdArea() {
        return idArea;
    }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
