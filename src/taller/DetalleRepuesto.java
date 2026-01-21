package taller;

/*
 * DETALLE REPUESTO
 * Item dentro de una orden: repuesto + cantidad.
 */
public class DetalleRepuesto {

    private final Repuesto repuesto;
    private int cantidad;

    // AQUÍ SE EJECUTA LA CREACIÓN DEL DETALLE
    public DetalleRepuesto(Repuesto repuesto, int cantidad) {
        if (repuesto == null) throw new IllegalArgumentException("Repuesto inválido.");
        if (cantidad <= 0) throw new IllegalArgumentException("Cantidad debe ser positiva.");
        this.repuesto = repuesto;
        this.cantidad = cantidad;
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
        return repuesto.getNombre() + " x" + cantidad + " = $" + subtotal();
    }
}
