package taller;

/*
 * CLASE BICICLETA
 * Mantiene ID int, pero se muestra como 01, 02, 03...
 */
public class Bicicleta {

    private final int id;
    private Cliente cliente; // AQUÍ ya trabajas con objeto Cliente
    private String marca;
    private String modelo;

    // AQUÍ SE EJECUTA LA CREACIÓN DE LA BICICLETA
    public Bicicleta(int id, Cliente cliente, String marca, String modelo) {
        this.id = id;
        setCliente(cliente);
        setMarca(marca);
        setModelo(modelo);
    }

    public int getId() { return id; }

    // AQUÍ SE MUESTRA EL ID COMO 01, 02...
    public String getIdFormateado() {
        return String.format("%02d", id);
    }

    public Cliente getCliente() { return cliente; }

    public String getMarca() { return marca; }

    public String getModelo() { return modelo; }

    public void setCliente(Cliente cliente) {
        if (cliente == null) throw new IllegalArgumentException("Cliente inválido.");
        this.cliente = cliente;
    }

    public void setMarca(String marca) {
        if (marca == null || marca.trim().isEmpty())
            throw new IllegalArgumentException("Marca inválida.");
        this.marca = marca.trim();
    }

    public void setModelo(String modelo) {
        if (modelo == null || modelo.trim().isEmpty())
            throw new IllegalArgumentException("Modelo inválido.");
        this.modelo = modelo.trim();
    }

    @Override
    public String toString() {
        return "Bici " + getIdFormateado() +
                " | Cliente: " + cliente.getNombre() +
                " | " + marca + " " + modelo;
    }
}

