package miProyectoFinal;

public class ProductoImportado extends Producto {
	
	
	// Constructor
	public ProductoImportado(String id, String nombre, double precio, int cantidad) {
        super(id, nombre, precio, cantidad);
    }

    @Override
    public boolean esImportado() {
        return true;
    }
}
