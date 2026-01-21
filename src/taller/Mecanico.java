package taller;

/**
 * MECÁNICO
 * - Identificado por cédula (10 dígitos).
 * - Nombre.
 */
public class Mecanico {
    private final String cedula;
    private String nombre;

    public Mecanico(String cedula, String nombre) {
        this.cedula = cedula;
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre + " | CI: " + cedula;
    }
}
