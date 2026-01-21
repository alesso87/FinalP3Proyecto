package taller;

import java.util.ArrayList;
import java.util.List;

/*
 * TALLER SERVICIO
 * Esta clase es el "servicio central" donde se ejecuta el CRUD y operaciones.
 */
public class TallerServicio {

    private final List<Cliente> clientes = new ArrayList<>();
    private final List<Bicicleta> bicicletas = new ArrayList<>();
    private final List<Repuesto> repuestos = new ArrayList<>();
    private final List<OrdenServicio> ordenes = new ArrayList<>();

    // AQUÍ LOS CONTADORES (cliente ya NO, porque el cliente se identifica por cédula)
    private int idBici = 1, idRepuesto = 1, idOrden = 1;

    // ============================
    // CRUD CLIENTE (ID = CÉDULA)
    // AQUÍ EMPIEZAN LAS OPCIONES DE CLIENTE
    // ============================

    // AQUÍ SE EJECUTA LA OPCIÓN: CREAR CLIENTE
    public Cliente crearCliente(String cedula, String nombre, String telefono) {
        if (buscarCliente(cedula) != null)
            throw new IllegalArgumentException("Ya existe un cliente con esa cédula.");

        Cliente c = new Cliente(cedula, nombre, telefono);
        clientes.add(c);
        return c;
    }

    // AQUÍ SE EJECUTA LA OPCIÓN: ACTUALIZAR CLIENTE
    public Cliente actualizarCliente(String cedula, String nombre, String telefono) {
        Cliente c = buscarCliente(cedula);
        if (c == null) throw new IllegalArgumentException("Cliente no existe.");

        if (nombre != null && !nombre.trim().isEmpty()) c.setNombre(nombre);
        if (telefono != null) c.setTelefono(telefono);

        return c;
    }

    // AQUÍ SE EJECUTA LA OPCIÓN: ELIMINAR CLIENTE
    public void eliminarCliente(String cedula) {
        Cliente c = buscarCliente(cedula);
        if (c == null) throw new IllegalArgumentException("Cliente no existe.");

        // Regla simple: no borrar si tiene bicicletas
        boolean tieneBici = bicicletas.stream().anyMatch(b -> b.getCliente().getId().equals(cedula));
        if (tieneBici)
            throw new IllegalArgumentException("No se puede eliminar: el cliente tiene bicicletas registradas.");

        clientes.remove(c);
    }

    // AQUÍ SE EJECUTA LA OPCIÓN: BUSCAR CLIENTE
    public Cliente buscarCliente(String cedula) {
        return clientes.stream()
                .filter(c -> c.getId().equals(cedula))
                .findFirst()
                .orElse(null);
    }

    // ============================
    // CRUD BICICLETA
    // AQUÍ EMPIEZAN LAS OPCIONES DE BICICLETA
    // ============================

    // AQUÍ SE EJECUTA LA OPCIÓN: CREAR BICICLETA
    public Bicicleta crearBicicleta(String cedulaCliente, String marca, String modelo) {
        Cliente c = buscarCliente(cedulaCliente);
        if (c == null) throw new IllegalArgumentException("Cliente no existe.");

        Bicicleta b = new Bicicleta(idBici++, c, marca, modelo);
        bicicletas.add(b);
        return b;
    }

    // AQUÍ SE EJECUTA LA OPCIÓN: ACTUALIZAR BICICLETA
    public Bicicleta actualizarBicicleta(int idBici, String nuevaCedulaCliente, String marca, String modelo) {
        Bicicleta b = buscarBicicleta(idBici);
        if (b == null) throw new IllegalArgumentException("Bicicleta no existe.");

        if (nuevaCedulaCliente != null && !nuevaCedulaCliente.trim().isEmpty()) {
            Cliente c = buscarCliente(nuevaCedulaCliente);
            if (c == null) throw new IllegalArgumentException("Nuevo cliente no existe.");
            b.setCliente(c);
        }

        if (marca != null && !marca.trim().isEmpty()) b.setMarca(marca);
        if (modelo != null && !modelo.trim().isEmpty()) b.setModelo(modelo);

        return b;
    }

    // AQUÍ SE EJECUTA LA OPCIÓN: ELIMINAR BICICLETA
    public void eliminarBicicleta(int idBici) {
        Bicicleta b = buscarBicicleta(idBici);
        if (b == null) throw new IllegalArgumentException("Bicicleta no existe.");

        boolean tieneOrden = ordenes.stream().anyMatch(o -> o.getBicicleta().getId() == idBici);
        if (tieneOrden)
            throw new IllegalArgumentException("No se puede eliminar: la bicicleta tiene órdenes asociadas.");

        bicicletas.remove(b);
    }

    // AQUÍ SE EJECUTA LA OPCIÓN: BUSCAR BICICLETA
    public Bicicleta buscarBicicleta(int id) {
        return bicicletas.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // ============================
    // CRUD REPUESTO
    // AQUÍ EMPIEZAN LAS OPCIONES DE REPUESTO
    // ============================

    // AQUÍ SE EJECUTA LA OPCIÓN: CREAR REPUESTO
    public Repuesto crearRepuesto(String nombre, double precio, int stock) {
        Repuesto r = new Repuesto(idRepuesto++, nombre, precio, stock);
        repuestos.add(r);
        return r;
    }

    // AQUÍ SE EJECUTA LA OPCIÓN: ACTUALIZAR REPUESTO
    public Repuesto actualizarRepuesto(int id, String nombre, Double precio, Integer stock) {
        Repuesto r = buscarRepuesto(id);
        if (r == null) throw new IllegalArgumentException("Repuesto no existe.");

        if (nombre != null && !nombre.trim().isEmpty()) r.setNombre(nombre);
        if (precio != null) r.setPrecio(precio);
        if (stock != null) r.setStock(stock);

        return r;
    }

    // AQUÍ SE EJECUTA LA OPCIÓN: ELIMINAR REPUESTO
    public void eliminarRepuesto(int id) {
        Repuesto r = buscarRepuesto(id);
        if (r == null) throw new IllegalArgumentException("Repuesto no existe.");

        boolean usadoEnOrden = ordenes.stream().anyMatch(o -> o.buscarDetalle(id) != null);
        if (usadoEnOrden)
            throw new IllegalArgumentException("No se puede eliminar: repuesto usado en una orden.");

        repuestos.remove(r);
    }

    // AQUÍ SE EJECUTA LA OPCIÓN: BUSCAR REPUESTO
    public Repuesto buscarRepuesto(int id) {
        return repuestos.stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // ============================
    // ORDEN SERVICIO / SEGUIMIENTO
    // AQUÍ EMPIEZAN LAS OPCIONES DE ORDEN
    // ============================

    // AQUÍ SE EJECUTA LA OPCIÓN: CREAR ORDEN
    public OrdenServicio crearOrden(String cedulaCliente, int idBici, String descripcion, double manoObra) {

        Cliente c = buscarCliente(cedulaCliente);
        if (c == null) throw new IllegalArgumentException("Cliente no existe.");

        Bicicleta b = buscarBicicleta(idBici);
        if (b == null) throw new IllegalArgumentException("Bicicleta no existe.");

        if (!b.getCliente().getId().equals(c.getId()))
            throw new IllegalArgumentException("La bicicleta no pertenece a ese cliente.");

        OrdenServicio o = new OrdenServicio(idOrden++, c, b, descripcion, manoObra);
        ordenes.add(o);
        return o;
    }

    // AQUÍ SE EJECUTA LA OPCIÓN: AGREGAR REPUESTO A ORDEN (DESCUENTA STOCK)
    public void agregarRepuestoAOrden(int idOrden, int idRepuesto, int cantidad) {
        OrdenServicio o = buscarOrden(idOrden);
        if (o == null) throw new IllegalArgumentException("Orden no existe.");
        if (o.getEstado() == EstadoOrden.CERRADA) throw new IllegalArgumentException("Orden cerrada.");

        Repuesto r = buscarRepuesto(idRepuesto);
        if (r == null) throw new IllegalArgumentException("Repuesto no existe.");

        if (cantidad <= 0) throw new IllegalArgumentException("Cantidad debe ser positiva.");
        if (r.getStock() < cantidad) throw new IllegalArgumentException("Stock insuficiente.");

        // descontar stock y agregar
        r.reducirStock(cantidad);
        o.adicionarDetalle(r, cantidad);
    }

    // AQUÍ SE EJECUTA LA OPCIÓN: ELIMINAR REPUESTO DE ORDEN (DEVUELVE STOCK)
    public void eliminarRepuestoDeOrden(int idOrden, int idRepuesto) {
        OrdenServicio o = buscarOrden(idOrden);
        if (o == null) throw new IllegalArgumentException("Orden no existe.");
        if (o.getEstado() == EstadoOrden.CERRADA) throw new IllegalArgumentException("Orden cerrada.");

        DetalleRepuesto d = o.buscarDetalle(idRepuesto);
        if (d == null) throw new IllegalArgumentException("Ese repuesto no está en la orden.");

        // devolver stock
        d.getRepuesto().aumentarStock(d.getCantidad());
        o.eliminarDetallePorRepuestoId(idRepuesto);
    }

    // AQUÍ SE EJECUTA LA OPCIÓN: MODIFICAR CANTIDAD (AJUSTA STOCK SEGÚN DIFERENCIA)
    public void modificarCantidadRepuestoOrden(int idOrden, int idRepuesto, int nuevaCantidad) {
        OrdenServicio o = buscarOrden(idOrden);
        if (o == null) throw new IllegalArgumentException("Orden no existe.");
        if (o.getEstado() == EstadoOrden.CERRADA) throw new IllegalArgumentException("Orden cerrada.");

        DetalleRepuesto d = o.buscarDetalle(idRepuesto);
        if (d == null) throw new IllegalArgumentException("Ese repuesto no está en la orden.");

        int actual = d.getCantidad();
        if (nuevaCantidad <= 0) throw new IllegalArgumentException("Cantidad debe ser positiva.");

        int diff = nuevaCantidad - actual;

        if (diff > 0) {
            // necesito más stock
            if (d.getRepuesto().getStock() < diff)
                throw new IllegalArgumentException("Stock insuficiente para aumentar cantidad.");
            d.getRepuesto().reducirStock(diff);
        } else if (diff < 0) {
            // devuelvo stock
            d.getRepuesto().aumentarStock(-diff);
        }

        o.modificarCantidadDetalle(idRepuesto, nuevaCantidad);
    }

    // AQUÍ SE EJECUTA LA OPCIÓN: ACTUALIZAR ORDEN (DESC/MANOOBRA)
    public void actualizarOrden(int idOrden, String nuevaDesc, Double nuevaMano) {
        OrdenServicio o = buscarOrden(idOrden);
        if (o == null) throw new IllegalArgumentException("Orden no existe.");
        if (o.getEstado() == EstadoOrden.CERRADA) throw new IllegalArgumentException("Orden cerrada.");

        if (nuevaDesc != null && !nuevaDesc.trim().isEmpty()) o.setDescripcion(nuevaDesc);
        if (nuevaMano != null) o.setManoObra(nuevaMano);

        o.agregarSeguimiento("Orden actualizada (desc/manoObra).");
    }

    // AQUÍ SE EJECUTA LA OPCIÓN: CAMBIAR ESTADO DE ORDEN
    public void cambiarEstadoOrden(int idOrden, EstadoOrden estado) {
        OrdenServicio o = buscarOrden(idOrden);
        if (o == null) throw new IllegalArgumentException("Orden no existe.");
        o.cambiarEstado(estado);
    }

    // AQUÍ SE EJECUTA LA OPCIÓN: AGREGAR NOTA DE SEGUIMIENTO
    public void agregarNotaSeguimiento(int idOrden, String nota) {
        OrdenServicio o = buscarOrden(idOrden);
        if (o == null) throw new IllegalArgumentException("Orden no existe.");
        o.agregarSeguimiento(nota);
    }

    // AQUÍ SE EJECUTA LA OPCIÓN: CERRAR ORDEN
    public void cerrarOrden(int idOrden) {
        OrdenServicio o = buscarOrden(idOrden);
        if (o == null) throw new IllegalArgumentException("Orden no existe.");
        o.cerrar();
    }

    // AQUÍ SE EJECUTA LA OPCIÓN: BUSCAR ORDEN
    public OrdenServicio buscarOrden(int id) {
        return ordenes.stream()
                .filter(o -> o.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // ============================
    // GETTERS DE LISTAS (para GUI)
    // ============================

    public List<Cliente> clientes() { return clientes; }

    public List<Bicicleta> bicicletas() { return bicicletas; }

    public List<Repuesto> repuestos() { return repuestos; }

    public List<OrdenServicio> ordenes() { return ordenes; }
}
