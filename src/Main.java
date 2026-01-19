package taller;

import javax.swing.*;
import java.awt.*;

public class Main {

    private final TallerServicio servicio = new TallerServicio();

    private JFrame frame;
    private JTextArea area;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().iniciar());
    }

    private void iniciar() {
        frame = new JFrame("Taller de Bicicletas - Sistema");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(980, 640);
        frame.setLocationRelativeTo(null);

        area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Consolas", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(area);

        JPanel panelBotones = new JPanel(new GridLayout(3, 5, 8, 8));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // CLIENTE
        JButton bCrearCliente = new JButton("Crear Cliente");
        JButton bUpdCliente = new JButton("Editar Cliente");
        JButton bDelCliente = new JButton("Eliminar Cliente");

        // BICI
        JButton bCrearBici = new JButton("Crear Bicicleta");
        JButton bUpdBici = new JButton("Editar Bicicleta");
        JButton bDelBici = new JButton("Eliminar Bicicleta");

        // REPUESTO
        JButton bCrearRep = new JButton("Crear Repuesto");
        JButton bUpdRep = new JButton("Editar Repuesto");
        JButton bDelRep = new JButton("Eliminar Repuesto");

        // ORDEN / SEGUIMIENTO
        JButton bCrearOrden = new JButton("Crear Orden");
        JButton bAddRepOrden = new JButton("+ Repuesto a Orden");
        JButton bDelRepOrden = new JButton("- Repuesto de Orden");
        JButton bModCant = new JButton("Modificar Cantidad");
        JButton bSeguimiento = new JButton("Seguimiento/Estado");
        JButton bCerrarOrden = new JButton("Cerrar Orden");

        // REPORTES
        JButton bReporteOrden = new JButton("Reporte Orden");
        JButton bListarTodo = new JButton("Listar Todo");
        JButton bLimpiar = new JButton("Limpiar Log");

        // Layout botones (15)
        panelBotones.add(bCrearCliente);
        panelBotones.add(bUpdCliente);
        panelBotones.add(bDelCliente);
        panelBotones.add(bCrearBici);
        panelBotones.add(bUpdBici);

        panelBotones.add(bDelBici);
        panelBotones.add(bCrearRep);
        panelBotones.add(bUpdRep);
        panelBotones.add(bDelRep);
        panelBotones.add(bCrearOrden);

        panelBotones.add(bAddRepOrden);
        panelBotones.add(bDelRepOrden);
        panelBotones.add(bModCant);
        panelBotones.add(bSeguimiento);
        panelBotones.add(bCerrarOrden);

        // Abajo: reportes
        JPanel panelAbajo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        panelAbajo.add(bReporteOrden);
        panelAbajo.add(bListarTodo);
        panelAbajo.add(bLimpiar);

        // Acciones
        bCrearCliente.addActionListener(e -> crearClienteGUI());
        bUpdCliente.addActionListener(e -> editarClienteGUI());
        bDelCliente.addActionListener(e -> eliminarClienteGUI());

        bCrearBici.addActionListener(e -> crearBicicletaGUI());
        bUpdBici.addActionListener(e -> editarBicicletaGUI());
        bDelBici.addActionListener(e -> eliminarBicicletaGUI());

        bCrearRep.addActionListener(e -> crearRepuestoGUI());
        bUpdRep.addActionListener(e -> editarRepuestoGUI());
        bDelRep.addActionListener(e -> eliminarRepuestoGUI());

        bCrearOrden.addActionListener(e -> crearOrdenGUI());
        bAddRepOrden.addActionListener(e -> agregarRepuestoOrdenGUI());
        bDelRepOrden.addActionListener(e -> eliminarRepuestoOrdenGUI());
        bModCant.addActionListener(e -> modificarCantidadOrdenGUI());
        bSeguimiento.addActionListener(e -> seguimientoOrdenGUI());
        bCerrarOrden.addActionListener(e -> cerrarOrdenGUI());

        bReporteOrden.addActionListener(e -> reporteOrdenGUI());
        bListarTodo.addActionListener(e -> log(ReporteServicio.listadoGeneral(servicio)));
        bLimpiar.addActionListener(e -> area.setText(""));

        frame.setLayout(new BorderLayout());
        frame.add(panelBotones, BorderLayout.NORTH);
        frame.add(scroll, BorderLayout.CENTER);
        frame.add(panelAbajo, BorderLayout.SOUTH);

        log("=== Sistema iniciado ===");
        frame.setVisible(true);
    }

    // -----------------------------
    // Helpers entrada / salida
    // -----------------------------
    private void log(String msg) {
        area.append(msg + "\n");
    }

    private String pedirTexto(String label) {
        String x = JOptionPane.showInputDialog(frame, label);
        if (x == null) return null;
        x = x.trim();
        if (x.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No puede estar vacío.");
            return null;
        }
        return x;
    }

    private Integer pedirEnteroPositivo(String label) {
        String x = JOptionPane.showInputDialog(frame, label);
        if (x == null) return null;
        try {
            int v = Integer.parseInt(x.trim());
            if (v <= 0) throw new NumberFormatException();
            return v;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Ingrese un número entero positivo.");
            return null;
        }
    }

    private Double pedirDoubleNoNegativo(String label) {
        String x = JOptionPane.showInputDialog(frame, label);
        if (x == null) return null;
        try {
            double v = Double.parseDouble(x.trim());
            if (v < 0) throw new NumberFormatException();
            return v;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Ingrese un número válido (no negativo).");
            return null;
        }
    }

    // -----------------------------
    // CLIENTE CRUD
    // -----------------------------
    private void crearClienteGUI() {
        try {
            String nombre = pedirTexto("Nombre del cliente:");
            if (nombre == null) return;
            String tel = pedirTexto("Teléfono:");
            if (tel == null) return;

            Cliente c = servicio.crearCliente(nombre, tel);
            log("[OK] Cliente creado: " + c);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }

    private void editarClienteGUI() {
        try {
            Integer id = pedirEnteroPositivo("ID Cliente a editar:");
            if (id == null) return;

            String nombre = JOptionPane.showInputDialog(frame, "Nuevo nombre (ENTER para mantener):");
            String tel = JOptionPane.showInputDialog(frame, "Nuevo teléfono (ENTER para mantener):");

            Cliente c = servicio.actualizarCliente(id, nombre, tel);
            log("[OK] Cliente actualizado: " + c);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }

    private void eliminarClienteGUI() {
        try {
            Integer id = pedirEnteroPositivo("ID Cliente a eliminar:");
            if (id == null) return;

            servicio.eliminarCliente(id);
            log("[OK] Cliente eliminado: ID " + id);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }

    // -----------------------------
    // BICICLETA CRUD
    // -----------------------------
    private void crearBicicletaGUI() {
        try {
            Integer idCli = pedirEnteroPositivo("ID del cliente (debe existir):");
            if (idCli == null) return;

            String marca = pedirTexto("Marca de la bicicleta:");
            if (marca == null) return;

            String modelo = pedirTexto("Modelo de la bicicleta:");
            if (modelo == null) return;

            Bicicleta b = servicio.crearBicicleta(idCli, marca, modelo);
            log("[OK] Bicicleta creada: " + b);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }

    private void editarBicicletaGUI() {
        try {
            Integer idBici = pedirEnteroPositivo("ID Bicicleta a editar:");
            if (idBici == null) return;

            String sNuevoIdCli = JOptionPane.showInputDialog(frame, "Nuevo ID Cliente (vacío para mantener):");
            Integer nuevoIdCli = null;
            if (sNuevoIdCli != null && !sNuevoIdCli.trim().isEmpty()) {
                nuevoIdCli = Integer.parseInt(sNuevoIdCli.trim());
                if (nuevoIdCli <= 0) throw new IllegalArgumentException("ID Cliente inválido.");
            }

            String marca = JOptionPane.showInputDialog(frame, "Nueva marca (vacío para mantener):");
            String modelo = JOptionPane.showInputDialog(frame, "Nuevo modelo (vacío para mantener):");

            Bicicleta b = servicio.actualizarBicicleta(idBici, nuevoIdCli, marca, modelo);
            log("[OK] Bicicleta actualizada: " + b);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Error: ID Cliente inválido.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }

    private void eliminarBicicletaGUI() {
        try {
            Integer id = pedirEnteroPositivo("ID Bicicleta a eliminar:");
            if (id == null) return;

            servicio.eliminarBicicleta(id);
            log("[OK] Bicicleta eliminada: ID " + id);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }

    // -----------------------------
    // REPUESTO CRUD
    // -----------------------------
    private void crearRepuestoGUI() {
        try {
            String nombre = pedirTexto("Nombre del repuesto:");
            if (nombre == null) return;

            Double precio = pedirDoubleNoNegativo("Precio:");
            if (precio == null) return;

            Integer stock = pedirEnteroPositivo("Stock inicial:");
            if (stock == null) return;

            Repuesto r = servicio.crearRepuesto(nombre, precio, stock);
            log("[OK] Repuesto creado: " + r);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }

    private void editarRepuestoGUI() {
        try {
            Integer id = pedirEnteroPositivo("ID Repuesto a editar:");
            if (id == null) return;

            String nombre = JOptionPane.showInputDialog(frame, "Nuevo nombre (vacío para mantener):");

            String sPrecio = JOptionPane.showInputDialog(frame, "Nuevo precio (vacío para mantener):");
            Double precio = null;
            if (sPrecio != null && !sPrecio.trim().isEmpty()) precio = Double.parseDouble(sPrecio.trim());

            String sStock = JOptionPane.showInputDialog(frame, "Nuevo stock (vacío para mantener):");
            Integer stock = null;
            if (sStock != null && !sStock.trim().isEmpty()) stock = Integer.parseInt(sStock.trim());

            Repuesto r = servicio.actualizarRepuesto(id, nombre, precio, stock);
            log("[OK] Repuesto actualizado: " + r);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Error: número inválido.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }

    private void eliminarRepuestoGUI() {
        try {
            Integer id = pedirEnteroPositivo("ID Repuesto a eliminar:");
            if (id == null) return;

            servicio.eliminarRepuesto(id);
            log("[OK] Repuesto eliminado: ID " + id);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }

    // -----------------------------
    // ORDEN + DETALLES + SEGUIMIENTO
    // -----------------------------
    private void crearOrdenGUI() {
        try {
            Integer idCli = pedirEnteroPositivo("ID Cliente:");
            if (idCli == null) return;

            Integer idBici = pedirEnteroPositivo("ID Bicicleta:");
            if (idBici == null) return;

            String desc = pedirTexto("Descripción del servicio:");
            if (desc == null) return;

            Double mano = pedirDoubleNoNegativo("Mano de obra (valor):");
            if (mano == null) return;

            OrdenServicio o = servicio.crearOrden(idCli, idBici, desc, mano);
            log("[OK] Orden creada: " + o);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }

    private void agregarRepuestoOrdenGUI() {
        try {
            Integer idOrden = pedirEnteroPositivo("ID Orden:");
            if (idOrden == null) return;

            Integer idRep = pedirEnteroPositivo("ID Repuesto:");
            if (idRep == null) return;

            Integer cant = pedirEnteroPositivo("Cantidad:");
            if (cant == null) return;

            servicio.agregarRepuestoAOrden(idOrden, idRep, cant);
            log("[OK] Repuesto agregado a orden. Orden=" + idOrden + ", Repuesto=" + idRep + ", Cant=" + cant);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }

    private void eliminarRepuestoOrdenGUI() {
        try {
            Integer idOrden = pedirEnteroPositivo("ID Orden:");
            if (idOrden == null) return;

            Integer idRep = pedirEnteroPositivo("ID Repuesto a quitar:");
            if (idRep == null) return;

            servicio.eliminarRepuestoDeOrden(idOrden, idRep);
            log("[OK] Repuesto eliminado de orden. Orden=" + idOrden + ", Repuesto=" + idRep);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }

    private void modificarCantidadOrdenGUI() {
        try {
            Integer idOrden = pedirEnteroPositivo("ID Orden:");
            if (idOrden == null) return;

            Integer idRep = pedirEnteroPositivo("ID Repuesto:");
            if (idRep == null) return;

            Integer nueva = pedirEnteroPositivo("Nueva cantidad:");
            if (nueva == null) return;

            servicio.modificarCantidadRepuestoOrden(idOrden, idRep, nueva);
            log("[OK] Cantidad modificada. Orden=" + idOrden + ", Repuesto=" + idRep + ", Nueva=" + nueva);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }

    private void seguimientoOrdenGUI() {
        try {
            Integer idOrden = pedirEnteroPositivo("ID Orden:");
            if (idOrden == null) return;

            String[] opciones = {"Agregar Nota", "Cambiar Estado"};
            int op = JOptionPane.showOptionDialog(frame, "¿Qué quieres hacer?",
                    "Seguimiento", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, opciones, opciones[0]);

            if (op == 0) {
                String nota = pedirTexto("Escribe la nota de seguimiento:");
                if (nota == null) return;
                servicio.agregarNotaSeguimiento(idOrden, nota);
                log("[OK] Nota agregada a orden " + idOrden);
            } else if (op == 1) {
                EstadoOrden estado = (EstadoOrden) JOptionPane.showInputDialog(
                        frame,
                        "Selecciona estado:",
                        "Estado",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        EstadoOrden.values(),
                        EstadoOrden.EN_PROCESO
                );
                if (estado == null) return;
                servicio.cambiarEstadoOrden(idOrden, estado);
                log("[OK] Estado actualizado en orden " + idOrden + " -> " + estado);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }

    private void cerrarOrdenGUI() {
        try {
            Integer idOrden = pedirEnteroPositivo("ID Orden a cerrar:");
            if (idOrden == null) return;

            servicio.cerrarOrden(idOrden);
            log("[OK] Orden cerrada: " + idOrden);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }

    private void reporteOrdenGUI() {
        Integer idOrden = pedirEnteroPositivo("ID Orden para reporte:");
        if (idOrden == null) return;

        String rep = ReporteServicio.reporteOrden(servicio, idOrden);
        JTextArea t = new JTextArea(rep, 22, 60);
        t.setFont(new Font("Consolas", Font.PLAIN, 12));
        t.setEditable(false);
        JOptionPane.showMessageDialog(frame, new JScrollPane(t), "Reporte Orden " + idOrden, JOptionPane.INFORMATION_MESSAGE);
        log("[INFO] Reporte generado para orden " + idOrden);
    }
}
