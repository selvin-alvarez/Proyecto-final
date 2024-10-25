package miProyectoFinal;

public class ProductoLocal extends Producto {
	
	
	// Constructor
	public ProductoLocal(String id, String nombre, double precio, int cantidad) {
        super(id, nombre, precio, cantidad);
    }

    @Override
    public boolean esImportado() {
        return false;
    }

}
