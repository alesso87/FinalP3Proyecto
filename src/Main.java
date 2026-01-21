package taller;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * MAIN (Interfaz gráfica)
 * - Aquí se ejecuta todo el programa.
 * - Aquí empieza cada opción del menú (botones).
 * - Aquí se valida cédula / números negativos.
 */
public class Main {

    // ============================
    // AQUÍ SE GUARDAN MECÁNICOS EN MEMORIA (simple)
    // ============================
    private static final Map<String, Mecanico> MECANICOS = new LinkedHashMap<>();

    public static void main(String[] args) {
        // ============================
        // AQUÍ INICIA EL PROGRAMA (main)
        // ============================
        SwingUtilities.invokeLater(() -> {
            // AQUÍ SE CREA EL "SERVICIO" (lógica / datos en memoria)
            TallerServicio service = new TallerServicio();

            // ============================
            // AQUÍ SE CREA LA VENTANA PRINCIPAL
            // ============================
            JFrame frame = new JFrame("DCS - Taller (Proyecto)");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(980, 620);
            frame.setLocationRelativeTo(null);
            frame.setLayout(new BorderLayout());

            // ============================
            // AQUÍ SE PONE EL ICONO DE LA VENTANA (esquina superior)
            // ============================
            try {
                ImageIcon icon = new ImageIcon(Main.class.getResource("/DCS LOGO.png"));
                frame.setIconImage(icon.getImage());
            } catch (Exception ignored) {}

            // Contenedor raíz
            JPanel root = new JPanel(new BorderLayout());
            frame.setContentPane(root);

            // ============================
            // AQUÍ VA EL HEADER CON EL LOGO (ARRIBA, PEQUEÑO)
            // ============================
            JPanel panelTop = new JPanel(new BorderLayout());
            panelTop.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            panelTop.setPreferredSize(new Dimension(10, 130));

            JLabel lblLogo = new JLabel();
            lblLogo.setHorizontalAlignment(SwingConstants.CENTER);

            try {
                ImageIcon original = new ImageIcon(Main.class.getResource("/DCS LOGO.png"));

                // AQUÍ SE EJECUTA EL ESCALADO DEL LOGO
                int altoDeseado = 90; // ajusta si quieres (70/80/100)
                int anchoDeseado = (original.getIconWidth() * altoDeseado) / original.getIconHeight();

                Image imgEscalada = original.getImage().getScaledInstance(anchoDeseado, altoDeseado, Image.SCALE_SMOOTH);
                lblLogo.setIcon(new ImageIcon(imgEscalada));
            } catch (Exception ex) {
                lblLogo.setText("LOGO NO CARGADO (revisa Resources Root / nombre del archivo)");
            }

            panelTop.add(lblLogo, BorderLayout.CENTER);
            root.add(panelTop, BorderLayout.NORTH);

            // ============================
            // CUERPO: MENÚ + ÁREA DE SALIDA
            // ============================
            JPanel panelCentro = new JPanel(new BorderLayout());
            panelCentro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            root.add(panelCentro, BorderLayout.CENTER);

            JTextArea salida = new JTextArea();
            salida.setEditable(false);
            salida.setFont(new Font("Consolas", Font.PLAIN, 13));
            panelCentro.add(new JScrollPane(salida), BorderLayout.CENTER);

            JPanel panelMenu = new JPanel(new GridLayout(0, 1, 8, 8));
            panelMenu.setPreferredSize(new Dimension(300, 10));
            panelCentro.add(panelMenu, BorderLayout.WEST);

            // Texto inicial
            salida.append("Sistema DCS - Taller iniciado.\n");
            salida.append("Logo cargado desde /DCS LOGO.png (resources).\n\n");

            // ==========================================================
            // AQUÍ EMPIEZAN LAS OPCIONES DEL MENÚ (BOTONES)
            // ==========================================================

            // ===== 1) Crear Cliente =====
            JButton btnCrearCliente = new JButton("1) Crear Cliente");
            btnCrearCliente.addActionListener(e -> {
                // AQUÍ SE EJECUTA LA OPCIÓN: CREAR CLIENTE
                try {
                    String cedula = pedirCedulaValida(frame, "Cédula del cliente (10 dígitos):");
                    if (cedula == null) return;

                    String nombre = pedirTextoNoVacio(frame, "Nombre del cliente:");
                    if (nombre == null) return;

                    String telefono = pedirTextoNoVacio(frame, "Teléfono del cliente:");
                    if (telefono == null) return;

                    Cliente c = service.crearCliente(cedula, nombre, telefono);
                    salida.append("Cliente creado: " + c + "\n");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            panelMenu.add(btnCrearCliente);

            // ===== 2) Listar Clientes =====
            JButton btnListarClientes = new JButton("2) Listar Clientes");
            btnListarClientes.addActionListener(e -> {
                // AQUÍ SE EJECUTA LA OPCIÓN: LISTAR CLIENTES
                salida.append("\n--- CLIENTES ---\n");
                for (Cliente c : service.clientes()) {
                    salida.append(c + "\n");
                }
            });
            panelMenu.add(btnListarClientes);

            // ===== 3) Crear Bicicleta =====
            JButton btnCrearBici = new JButton("3) Crear Bicicleta");
            btnCrearBici.addActionListener(e -> {
                // AQUÍ SE EJECUTA LA OPCIÓN: CREAR BICICLETA
                try {
                    String cedulaCliente = pedirCedulaValida(frame, "Cédula del cliente dueño:");
                    if (cedulaCliente == null) return;

                    String marca = pedirTextoNoVacio(frame, "Marca:");
                    if (marca == null) return;

                    String modelo = pedirTextoNoVacio(frame, "Modelo:");
                    if (modelo == null) return;

                    Bicicleta b = service.crearBicicleta(cedulaCliente, marca, modelo);
                    salida.append("Bicicleta creada: " + b + "\n");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            panelMenu.add(btnCrearBici);

            // ===== 4) Crear Repuesto =====
            JButton btnCrearRepuesto = new JButton("4) Crear Repuesto");
            btnCrearRepuesto.addActionListener(e -> {
                // AQUÍ SE EJECUTA LA OPCIÓN: CREAR REPUESTO
                try {
                    String nombre = pedirTextoNoVacio(frame, "Nombre del repuesto:");
                    if (nombre == null) return;

                    double precio = pedirDoubleNoNeg(frame, "Precio ($):");
                    if (Double.isNaN(precio)) return;

                    int stock = pedirIntNoNeg(frame, "Stock (0 o más):");
                    if (stock == Integer.MIN_VALUE) return;

                    Repuesto r = service.crearRepuesto(nombre, precio, stock);
                    salida.append("Repuesto creado: " + r + "\n");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            panelMenu.add(btnCrearRepuesto);

            // ===== 5) Crear Orden =====
            JButton btnCrearOrden = new JButton("5) Crear Orden");
            btnCrearOrden.addActionListener(e -> {
                // AQUÍ SE EJECUTA LA OPCIÓN: CREAR ORDEN
                try {
                    String cedulaCliente = pedirCedulaValida(frame, "Cédula del cliente:");
                    if (cedulaCliente == null) return;

                    int idBici = pedirIntPositivo(frame, "ID bicicleta (ej: 1, 2, 3...):");
                    if (idBici == Integer.MIN_VALUE) return;

                    String desc = pedirTextoNoVacio(frame, "Descripción del servicio:");
                    if (desc == null) return;

                    double mano = pedirDoubleNoNeg(frame, "Mano de obra ($):");
                    if (Double.isNaN(mano)) return;

                    OrdenServicio o = service.crearOrden(cedulaCliente, idBici, desc, mano);
                    salida.append("Orden creada: " + o + "\n");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            panelMenu.add(btnCrearOrden);

            // ===== 6) Listar Órdenes =====
            JButton btnListarOrdenes = new JButton("6) Listar Órdenes");
            btnListarOrdenes.addActionListener(e -> {
                // AQUÍ SE EJECUTA LA OPCIÓN: LISTAR ÓRDENES
                salida.append("\n--- ÓRDENES ---\n");
                for (OrdenServicio o : service.ordenes()) {
                    salida.append(o + "\n");
                }
            });
            panelMenu.add(btnListarOrdenes);

            // ===== 7) Cambiar Estado de Orden =====
            JButton btnCambiarEstado = new JButton("7) Cambiar Estado Orden");
            btnCambiarEstado.addActionListener(e -> {
                // AQUÍ SE EJECUTA LA OPCIÓN: CAMBIAR ESTADO ORDEN
                try {
                    int idOrden = pedirIntPositivo(frame, "ID de la orden:");
                    if (idOrden == Integer.MIN_VALUE) return;

                    EstadoOrden estado = (EstadoOrden) JOptionPane.showInputDialog(
                            frame,
                            "Selecciona el nuevo estado:",
                            "Estado de Orden",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            EstadoOrden.values(),
                            EstadoOrden.ABIERTA
                    );
                    if (estado == null) return;

                    service.cambiarEstadoOrden(idOrden, estado);
                    salida.append("Estado cambiado: Orden #" + idOrden + " -> " + estado + "\n");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            panelMenu.add(btnCambiarEstado);

            // ===== 8) Cerrar Orden =====
            JButton btnCerrarOrden = new JButton("8) Cerrar Orden");
            btnCerrarOrden.addActionListener(e -> {
                // AQUÍ SE EJECUTA LA OPCIÓN: CERRAR ORDEN
                try {
                    int idOrden = pedirIntPositivo(frame, "ID de la orden a cerrar:");
                    if (idOrden == Integer.MIN_VALUE) return;

                    service.cerrarOrden(idOrden);
                    salida.append("Orden cerrada: #" + idOrden + "\n");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            panelMenu.add(btnCerrarOrden);

            // ===== 9) Crear Mecánico =====
            JButton btnCrearMecanico = new JButton("9) Crear Mecánico");
            btnCrearMecanico.addActionListener(e -> {
                // AQUÍ SE EJECUTA LA OPCIÓN: CREAR MECÁNICO
                String cedula = pedirCedulaValida(frame, "Cédula del mecánico (10 dígitos):");
                if (cedula == null) return;

                String nombre = pedirTextoNoVacio(frame, "Nombre del mecánico:");
                if (nombre == null) return;

                if (MECANICOS.containsKey(cedula)) {
                    JOptionPane.showMessageDialog(frame, "Ya existe un mecánico con esa cédula.", "Aviso",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Mecanico m = new Mecanico(cedula, nombre);
                MECANICOS.put(cedula, m);

                salida.append("Mecánico creado: " + m + "\n");
            });
            panelMenu.add(btnCrearMecanico);

            // ===== 10) Asignar Mecánico a una Orden =====
            JButton btnAsignarMecanicoOrden = new JButton("10) Asignar Mecánico a Orden");
            btnAsignarMecanicoOrden.addActionListener(e -> {
                // AQUÍ SE EJECUTA LA OPCIÓN: ASIGNAR MECÁNICO A ORDEN
                try {
                    if (MECANICOS.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Primero crea un mecánico.", "Aviso",
                                JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    int idOrden = pedirIntPositivo(frame, "ID de la orden:");
                    if (idOrden == Integer.MIN_VALUE) return;

                    Object[] opciones = MECANICOS.values().toArray();
                    Mecanico elegido = (Mecanico) JOptionPane.showInputDialog(
                            frame,
                            "Selecciona el mecánico:",
                            "Asignar Mecánico",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            opciones,
                            opciones[0]
                    );
                    if (elegido == null) return;

                    // Si tienes método real en TallerServicio, úsalo.
                    // Si no, lo registramos como seguimiento:
                    service.agregarNotaSeguimiento(idOrden,
                            "Mecánico asignado: " + elegido.getNombre() + " | CI: " + elegido.getCedula());

                    salida.append("Orden #" + idOrden + " -> Mecánico asignado: " + elegido.getNombre() + "\n");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            panelMenu.add(btnAsignarMecanicoOrden);

            // ===== Salir =====
            JButton btnSalir = new JButton("Salir");
            btnSalir.addActionListener(e -> {
                // AQUÍ SE EJECUTA LA OPCIÓN: SALIR
                frame.dispose();
            });
            panelMenu.add(btnSalir);

            // Mostrar ventana
            frame.setVisible(true);
        });
    }

    // ==========================================================
    // AQUÍ EMPIEZAN LAS FUNCIONES DE VALIDACIÓN (helpers)
    // ==========================================================

    private static String pedirCedulaValida(Component parent, String mensaje) {
        while (true) {
            String input = JOptionPane.showInputDialog(parent, mensaje);
            if (input == null) return null;

            input = input.trim();
            if (!input.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(parent,
                        "Cédula inválida.\nDebe tener EXACTAMENTE 10 dígitos y solo números.\nIntenta de nuevo.",
                        "Validación", JOptionPane.WARNING_MESSAGE);
                continue;
            }
            return input;
        }
    }

    private static String pedirTextoNoVacio(Component parent, String mensaje) {
        while (true) {
            String input = JOptionPane.showInputDialog(parent, mensaje);
            if (input == null) return null;

            input = input.trim();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(parent,
                        "No puede estar vacío. Intenta de nuevo.",
                        "Validación", JOptionPane.WARNING_MESSAGE);
                continue;
            }
            return input;
        }
    }

    private static double pedirDoubleNoNeg(Component parent, String mensaje) {
        while (true) {
            String input = JOptionPane.showInputDialog(parent, mensaje);
            if (input == null) return Double.NaN;

            input = input.trim().replace(",", ".");
            try {
                double val = Double.parseDouble(input);
                if (val < 0) {
                    JOptionPane.showMessageDialog(parent,
                            "No se permiten números negativos. Intenta de nuevo.",
                            "Validación", JOptionPane.WARNING_MESSAGE);
                    continue;
                }
                return val;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent,
                        "Número inválido. Intenta de nuevo.",
                        "Validación", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private static int pedirIntNoNeg(Component parent, String mensaje) {
        while (true) {
            String input = JOptionPane.showInputDialog(parent, mensaje);
            if (input == null) return Integer.MIN_VALUE;

            input = input.trim();
            try {
                int val = Integer.parseInt(input);
                if (val < 0) {
                    JOptionPane.showMessageDialog(parent,
                            "No se permiten números negativos. Intenta de nuevo.",
                            "Validación", JOptionPane.WARNING_MESSAGE);
                    continue;
                }
                return val;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent,
                        "Número inválido. Intenta de nuevo.",
                        "Validación", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private static int pedirIntPositivo(Component parent, String mensaje) {
        while (true) {
            String input = JOptionPane.showInputDialog(parent, mensaje);
            if (input == null) return Integer.MIN_VALUE;

            input = input.trim();
            try {
                int val = Integer.parseInt(input);
                if (val <= 0) {
                    JOptionPane.showMessageDialog(parent,
                            "Debe ser un número MAYOR que 0. Intenta de nuevo.",
                            "Validación", JOptionPane.WARNING_MESSAGE);
                    continue;
                }
                return val;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent,
                        "Número inválido. Intenta de nuevo.",
                        "Validación", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
