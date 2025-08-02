package logico;

public class Local {
    private String idLocal;
    private String ciudad;

    public Local(String idLocal, String ciudad) {
        this.idLocal = idLocal;
        this.ciudad = ciudad;
    }

    public String getIdLocal() { return this.idLocal; }
    public String getCiudad() { return this.ciudad; }
    public void setIdLocal(String idLocal) { this.idLocal = idLocal; }
}
