package miProyectoFinal;

public class ProductoFactory {
	
	public static Producto crearProducto(String id, String nombre, boolean esImportado, double precio, int cantidad) {
        if (esImportado) {
            return new ProductoImportado(id, nombre, precio, cantidad);
        } else {
            return new ProductoLocal(id, nombre, precio, cantidad);
        }
    }
}


