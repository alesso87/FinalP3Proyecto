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

        JButton bCrearCliente = new JButton("Crear Cliente");
        JButton bUpdCliente = new JButton("Editar Cliente");
        JButton bDelCliente = new JButton("Eliminar Cliente");

        JButton bCrearBici = new JButton("Crear Bicicleta");
        JButton bUpdBici = new JButton("Editar Bicicleta");
        JButton bDelBici = new JButton("Eliminar Bicicleta");

        JButton bCrearRep = new JButton("Crear Repuesto");
        JButton bUpdRep = new JButton("Editar Repuesto");
        JButton bDelRep = new JButton("Eliminar Repuesto");

        JButton bCrearOrden = new JButton("Crear Orden");
        JButton bAddRepOrden = new JButton("+ Repuesto a Orden");
        JButton bDelRepOrden = new JButton("- Repuesto de Orden");
        JButton bModCant = new JButton("Modificar Cantidad");
        JButton bSeguimiento = new JButton("Seguimiento/Estado");
        JButton bCerrarOrden = new JButton("Cerrar Orden");

        JButton bReporteOrden = new JButton("Reporte Orden");
        JButton bListarTodo = new JButton("Listar Todo");
        JButton bLimpiar = new JButton("Limpiar Log");

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

        JPanel panelAbajo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        panelAbajo.add(bReporteOrden);
        panelAbajo.add(bListarTodo);
        panelAbajo.add(bLimpiar);

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

    /* ================= HELPERS ================= */

    private void log(String msg) {
        area.append(msg + "\n");
    }

    private String pedirTexto(String label) {
        String x = JOptionPane.showInputDialog(frame, label);
        if (x == null || x.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No puede estar vacío.");
            return null;
        }
        return x.trim();
    }

    private Integer pedirEnteroPositivo(String label) {
        try {
            String x = JOptionPane.showInputDialog(frame, label);
            if (x == null) return null;
            int v = Integer.parseInt(x.trim());
            if (v <= 0) throw new NumberFormatException();
            return v;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Ingrese un número entero positivo.");
            return null;
        }
    }

    private Double pedirDoubleNoNegativo(String label) {
        try {
            String x = JOptionPane.showInputDialog(frame, label);
            if (x == null) return null;
            double v = Double.parseDouble(x.trim());
            if (v < 0) throw new NumberFormatException();
            return v;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Ingrese un número válido (>= 0).");
            return null;
        }
    }

    /* ================= CLIENTE ================= */

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


}
