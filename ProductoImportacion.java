package miProyectoFinal;

public class ProductoImportacion extends Producto {
    private String estado;

    public ProductoImportacion(String id, String nombre, double precio, int cantidad, String estado) {
        super(id, nombre, precio, cantidad);
        this.estado = estado;
    }
    
    // Getters y Setters
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public boolean esImportado() {
        return true; // Es un producto de importación
    }
}
