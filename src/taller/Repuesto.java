package taller;

/*
 * CLASE REPUESTO
 * Mantiene ID int, pero se muestra como 01, 02, 03...
 */
public class Repuesto {

    private final int id;
    private String nombre;
    private double precio;
    private int stock;

    // AQUÍ SE EJECUTA LA CREACIÓN DEL REPUESTO
    public Repuesto(int id, String nombre, double precio, int stock) {
        this.id = id;
        setNombre(nombre);
        setPrecio(precio);
        setStock(stock);
    }

    public int getId() { return id; }

    public String getIdFormateado() {
        return String.format("%02d", id);
    }

    public String getNombre() { return nombre; }

    public double getPrecio() { return precio; }

    public int getStock() { return stock; }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty())
            throw new IllegalArgumentException("Nombre inválido.");
        this.nombre = nombre.trim();
    }

    public void setPrecio(double precio) {
        if (precio < 0) throw new IllegalArgumentException("Precio inválido (no negativo).");
        this.precio = precio;
    }

    public void setStock(int stock) {
        if (stock < 0) throw new IllegalArgumentException("Stock inválido (no negativo).");
        this.stock = stock;
    }

    // AQUÍ SE DESCUENTA STOCK (USADO AL AGREGAR A ORDEN)
    public void reducirStock(int cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("Cantidad debe ser positiva.");
        if (stock < cantidad) throw new IllegalArgumentException("Stock insuficiente.");
        stock -= cantidad;
    }

    // AQUÍ SE DEVUELVE STOCK (USADO AL ELIMINAR DE ORDEN)
    public void aumentarStock(int cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("Cantidad debe ser positiva.");
        stock += cantidad;
    }

    @Override
    public String toString() {
        return "Rep " + getIdFormateado() + " | " + nombre + " | $" + precio + " | Stock: " + stock;
    }
}
