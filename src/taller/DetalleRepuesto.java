package taller;

public class DetalleRepuesto {
    private final Repuesto repuesto;
    private int cantidad;

    public DetalleRepuesto(Repuesto repuesto, int cantidad) {
        if (repuesto == null) throw new IllegalArgumentException("Repuesto inválido.");
        this.repuesto = repuesto;
        setCantidad(cantidad);
    }

    public Repuesto getRepuesto() { return repuesto; }
    public int getCantidad() { return cantidad; }

    public void setCantidad(int cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("Cantidad debe ser positiva.");
        this.cantidad = cantidad;
    }

    public double subtotal() {
        return repuesto.getPrecio() * cantidad;
    }

    @Override
    public String toString() {
        return repuesto.getNombre() + " (ID " + repuesto.getId() + ") x" + cantidad + " = $" + subtotal();
    }
}
