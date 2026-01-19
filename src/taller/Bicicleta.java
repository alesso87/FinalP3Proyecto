package taller;

public class Bicicleta {
    private final int id;
    private Cliente cliente; // ✅ objeto, NO idCliente
    private String marca;
    private String modelo;

    public Bicicleta(int id, Cliente cliente, String marca, String modelo) {
        this.id = id;
        setCliente(cliente);
        setMarca(marca);
        setModelo(modelo);
    }

    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }

    public void setCliente(Cliente cliente) {
        if (cliente == null) throw new IllegalArgumentException("Cliente inválido.");
        this.cliente = cliente;
    }

    public void setMarca(String marca) {
        if (marca == null || marca.trim().isEmpty()) throw new IllegalArgumentException("Marca vacía.");
        this.marca = marca.trim();
    }

    public void setModelo(String modelo) {
        if (modelo == null || modelo.trim().isEmpty()) throw new IllegalArgumentException("Modelo vacío.");
        this.modelo = modelo.trim();
    }

    @Override
    public String toString() {
        return "Bici #" + id + " | Cliente: " + cliente.getNombre() + " | " + marca + " | " + modelo;
    }
}
