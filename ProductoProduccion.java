package miProyectoFinal;

public class ProductoProduccion extends Producto {
    private String estado;

    public ProductoProduccion(String id, String nombre, double precio, int cantidad, String estado) {
        super(id, nombre, precio, cantidad);
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public boolean esImportado() {
        return false; // O según lo que aplique en tu caso
    }
}
