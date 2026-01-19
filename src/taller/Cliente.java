package taller;

public class Cliente {
    private final int id;
    private String nombre;
    private String telefono;

    public Cliente(int id, String nombre, String telefono) {
        this.id = id;
        setNombre(nombre);
        setTelefono(telefono);
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getTelefono() { return telefono; }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) throw new IllegalArgumentException("Nombre vacío.");
        this.nombre = nombre.trim();
    }

    public void setTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) throw new IllegalArgumentException("Teléfono vacío.");
        this.telefono = telefono.trim();
    }

    @Override
    public String toString() {
        return "Cliente #" + id + " | " + nombre + " | " + telefono;
    }
}
