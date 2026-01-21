package taller;

/*
 * CLASE CLIENTE
 * CAMBIO MÍNIMO: el ID ahora es la CÉDULA (String).
 */
public class Cliente {

    private final String id; // AQUÍ el "ID" será la cédula
    private String nombre;
    private String telefono;

    // AQUÍ SE EJECUTA LA CREACIÓN DEL CLIENTE
    public Cliente(String cedula, String nombre, String telefono) {
        if (cedula == null || cedula.trim().isEmpty())
            throw new IllegalArgumentException("Cédula inválida.");

        this.id = cedula.trim();
        setNombre(nombre);
        setTelefono(telefono);
    }

    // AQUÍ SE OBTIENE EL ID (CÉDULA)
    public String getId() { return id; }

    public String getNombre() { return nombre; }

    public String getTelefono() { return telefono; }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty())
            throw new IllegalArgumentException("Nombre inválido.");
        this.nombre = nombre.trim();
    }

    public void setTelefono(String telefono) {
        if (telefono == null) telefono = "";
        this.telefono = telefono.trim();
    }

    @Override
    public String toString() {
        return nombre + " | CI: " + id + " | Tel: " + telefono;
    }
}
