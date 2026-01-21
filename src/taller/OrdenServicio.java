package taller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
 * ORDEN DE SERVICIO
 * Maneja: cliente, bicicleta, detalles, seguimiento, estado.
 */
public class OrdenServicio {

    private final int id;
    private Cliente cliente;           // objeto
    private Bicicleta bicicleta;       // objeto
    private String descripcion;
    private double manoObra;
    private EstadoOrden estado;

    private final List<DetalleRepuesto> detalles = new ArrayList<>();
    private final List<String> seguimiento = new ArrayList<>();

    // AQUÍ SE EJECUTA LA CREACIÓN DE LA ORDEN
    public OrdenServicio(int id, Cliente cliente, Bicicleta bicicleta, String descripcion, double manoObra) {
        this.id = id;
        setCliente(cliente);
        setBicicleta(bicicleta);
        setDescripcion(descripcion);
        setManoObra(manoObra);
        this.estado = EstadoOrden.ABIERTA;

        agregarSeguimiento("Orden creada.");
    }

    public int getId() { return id; }

    public String getIdFormateado() { return String.format("%02d", id); }

    public Cliente getCliente() { return cliente; }

    public Bicicleta getBicicleta() { return bicicleta; }

    public String getDescripcion() { return descripcion; }

    public double getManoObra() { return manoObra; }

    public EstadoOrden getEstado() { return estado; }

    public List<DetalleRepuesto> getDetalles() { return detalles; }

    public List<String> getSeguimiento() { return seguimiento; }

    // -------------------------
    // SETTERS + VALIDACIONES
    // -------------------------

    public void setCliente(Cliente cliente) {
        if (cliente == null) throw new IllegalArgumentException("Cliente inválido.");
        this.cliente = cliente;
    }

    public void setBicicleta(Bicicleta bicicleta) {
        if (bicicleta == null) throw new IllegalArgumentException("Bicicleta inválida.");
        this.bicicleta = bicicleta;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty())
            throw new IllegalArgumentException("Descripción vacía.");
        this.descripcion = descripcion.trim();
    }

    public void setManoObra(double manoObra) {
        if (manoObra < 0) throw new IllegalArgumentException("Mano de obra inválida (no negativa).");
        this.manoObra = manoObra;
    }

    // -------------------------
    // ACCIONES DE TALLER (DETALLES)
    // -------------------------

    // AQUÍ SE EJECUTA LA OPCIÓN: AGREGAR REPUESTO A LA ORDEN
    public void adicionarDetalle(Repuesto repuesto, int cantidad) {
        validarEditable();
        if (repuesto == null) throw new IllegalArgumentException("Repuesto inválido.");
        if (cantidad <= 0) throw new IllegalArgumentException("Cantidad debe ser positiva.");

        DetalleRepuesto existente = buscarDetalle(repuesto.getId());
        if (existente == null) {
            detalles.add(new DetalleRepuesto(repuesto, cantidad));
        } else {
            existente.setCantidad(existente.getCantidad() + cantidad);
        }
        agregarSeguimiento("Se agregó repuesto ID " + repuesto.getIdFormateado() + " x" + cantidad + ".");
    }

    // AQUÍ SE EJECUTA LA OPCIÓN: ELIMINAR REPUESTO DE LA ORDEN
    public void eliminarDetallePorRepuestoId(int idRepuesto) {
        validarEditable();
        DetalleRepuesto d = buscarDetalle(idRepuesto);
        if (d == null) throw new IllegalArgumentException("No existe ese repuesto en la orden.");
        detalles.remove(d);
        agregarSeguimiento("Se eliminó repuesto ID " + String.format("%02d", idRepuesto) + " del detalle.");
    }

    // AQUÍ SE EJECUTA LA OPCIÓN: MODIFICAR CANTIDAD DE REPUESTO
    public void modificarCantidadDetalle(int idRepuesto, int nuevaCantidad) {
        validarEditable();
        if (nuevaCantidad <= 0) throw new IllegalArgumentException("Cantidad debe ser positiva.");
        DetalleRepuesto d = buscarDetalle(idRepuesto);
        if (d == null) throw new IllegalArgumentException("No existe ese repuesto en la orden.");
        d.setCantidad(nuevaCantidad);
        agregarSeguimiento("Se modificó cantidad de repuesto ID " + String.format("%02d", idRepuesto) + " a x" + nuevaCantidad + ".");
    }

    // AQUÍ SE EJECUTA LA BÚSQUEDA DE UN DETALLE DENTRO DE LA ORDEN
    public DetalleRepuesto buscarDetalle(int idRepuesto) {
        for (DetalleRepuesto d : detalles) {
            if (d.getRepuesto().getId() == idRepuesto) return d;
        }
        return null;
    }

    // -------------------------
    // SEGUIMIENTO / MANTENIMIENTO
    // -------------------------

    // AQUÍ SE EJECUTA LA OPCIÓN: CAMBIAR ESTADO
    public void cambiarEstado(EstadoOrden nuevoEstado) {
        if (nuevoEstado == null) throw new IllegalArgumentException("Estado inválido.");
        this.estado = nuevoEstado;
        agregarSeguimiento("Estado cambiado a: " + nuevoEstado);
    }

    // AQUÍ SE EJECUTA LA OPCIÓN: CERRAR ORDEN
    public void cerrar() {
        this.estado = EstadoOrden.CERRADA;
        agregarSeguimiento("Orden cerrada.");
    }

    // AQUÍ SE AGREGA UNA NOTA AL SEGUIMIENTO
    public void agregarSeguimiento(String nota) {
        if (nota == null || nota.trim().isEmpty()) return;
        seguimiento.add(LocalDateTime.now() + " - " + nota.trim());
    }

    // AQUÍ SE CALCULA EL TOTAL
    public double total() {
        double suma = manoObra;
        for (DetalleRepuesto d : detalles) suma += d.subtotal();
        return suma;
    }

    private void validarEditable() {
        if (estado == EstadoOrden.CERRADA) throw new IllegalArgumentException("La orden está cerrada.");
    }

    @Override
    public String toString() {
        return "Orden #" + getIdFormateado() +
                " | Cliente: " + cliente.getNombre() +
                " | Bici: " + bicicleta.getMarca() + " " + bicicleta.getModelo() +
                " | Estado: " + estado +
                " | Total: $" + total();
    }
}
