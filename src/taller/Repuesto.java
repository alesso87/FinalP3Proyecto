package taller;

public class Repuesto {
    private final int id;
    private String nombre;
    private double precio;
    private int stock;

    public Repuesto(int id, String nombre, double precio, int stock) {
        this.id = id;
        setNombre(nombre);
        setPrecio(precio);
        setStock(stock);
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) throw new IllegalArgumentException("Nombre vacío.");
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

    public void aumentarStock(int cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("Cantidad debe ser positiva.");
        stock += cantidad;
    }

    public void reducirStock(int cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("Cantidad debe ser positiva.");
        if (stock < cantidad) throw new IllegalArgumentException("Stock insuficiente.");
        stock -= cantidad;
    }

    @Override
    public String toString() {
        return "Repuesto #" + id + " | " + nombre + " | $" + precio + " | Stock: " + stock;
    }
}
