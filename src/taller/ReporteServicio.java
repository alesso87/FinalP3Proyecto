package taller;

public class ReporteServicio {

    public static String reporteOrden(TallerServicio servicio, int idOrden) {
        OrdenServicio o = servicio.buscarOrden(idOrden);
        if (o == null) return "No existe la orden con ID " + idOrden;

        StringBuilder sb = new StringBuilder();
        sb.append("===== REPORTE DE SERVICIO =====\n");
        sb.append("Orden #").append(o.getId()).append("\n");
        sb.append("Cliente: ").append(o.getCliente()).append("\n");
        sb.append("Bicicleta: ").append(o.getBicicleta()).append("\n");
        sb.append("Descripción: ").append(o.getDescripcion()).append("\n");
        sb.append("Estado: ").append(o.getEstado()).append("\n");
        sb.append("Mano de obra: $").append(o.getManoObra()).append("\n\n");

        sb.append("--- Repuestos ---\n");
        if (o.getDetalles().isEmpty()) {
            sb.append("(Sin repuestos)\n");
        } else {
            for (DetalleRepuesto d : o.getDetalles()) {
                sb.append("- ").append(d).append("\n");
            }
        }

        sb.append("\nTOTAL: $").append(o.total()).append("\n\n");

        sb.append("--- Seguimiento ---\n");
        if (o.getSeguimiento().isEmpty()) {
            sb.append("(Sin seguimiento)\n");
        } else {
            for (String s : o.getSeguimiento()) {
                sb.append("- ").append(s).append("\n");
            }
        }

        sb.append("==============================\n");
        return sb.toString();
    }

    public static String listadoGeneral(TallerServicio s) {
        StringBuilder sb = new StringBuilder();

        sb.append("\n--- CLIENTES ---\n");
        s.clientes().forEach(c -> sb.append(c).append("\n"));

        sb.append("\n--- BICICLETAS ---\n");
        s.bicicletas().forEach(b -> sb.append(b).append("\n"));

        sb.append("\n--- REPUESTOS ---\n");
        s.repuestos().forEach(r -> sb.append(r).append("\n"));

        sb.append("\n--- ORDENES ---\n");
        s.ordenes().forEach(o -> sb.append(o).append("\n"));

        return sb.toString();
    }
}
