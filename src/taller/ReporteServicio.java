package taller;

import java.util.StringJoiner;

/*
 * REPORTE SERVICIO
 * Genera un resumen de una orden (para imprimir o mostrar en GUI).
 */
public class ReporteServicio {

    // AQUÍ SE EJECUTA LA GENERACIÓN DEL REPORTE
    public static String generarReporte(OrdenServicio o) {
        if (o == null) return "Orden nula.";

        StringJoiner sj = new StringJoiner("\n");
        sj.add("=== REPORTE ORDEN ===");
        sj.add("Orden: #" + o.getIdFormateado());
        sj.add("Cliente: " + o.getCliente().getNombre() + " | CI: " + o.getCliente().getId());
        sj.add("Bicicleta: " + o.getBicicleta().getMarca() + " " + o.getBicicleta().getModelo()
                + " | ID: " + o.getBicicleta().getIdFormateado());
        sj.add("Estado: " + o.getEstado());
        sj.add("Descripción: " + o.getDescripcion());
        sj.add("Mano de obra: $" + o.getManoObra());
        sj.add("--- Detalles ---");
        if (o.getDetalles().isEmpty()) sj.add("(sin repuestos)");
        for (DetalleRepuesto d : o.getDetalles()) sj.add(d.toString());
        sj.add("TOTAL: $" + o.total());
        sj.add("--- Seguimiento ---");
        if (o.getSeguimiento().isEmpty()) sj.add("(sin seguimiento)");
        for (String s : o.getSeguimiento()) sj.add(s);

        return sj.toString();
    }
}
