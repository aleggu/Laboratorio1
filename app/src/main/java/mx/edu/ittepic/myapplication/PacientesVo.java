package mx.edu.ittepic.myapplication;

public class PacientesVo {
    private String id;
    private String nombre;
    private String rfc;
    private String celular;
    private String email;
    private String fecha;

    public PacientesVo()
    {

    }

    public PacientesVo(String id, String nombre, String rfc, String celular, String email, String fecha) {
        this.id = id;
        this.nombre = nombre;
        this.rfc = rfc;
        this.celular = celular;
        this.email = email;
        this.fecha = fecha;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRfc() {
        return rfc;
    }

    public String getCelular() {
        return celular;
    }

    public String getEmail() {
        return email;
    }

    public String getFecha() {
        return fecha;
    }
}
