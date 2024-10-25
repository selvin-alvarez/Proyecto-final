package miProyectoFinal;

public abstract class Producto {
	
	
	// Atributos
	private String id;
    private String nombre;
    private double precio;
    private int cantidad;
    
    
    //Constructor
    public Producto(String id, String nombre, double precio, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    // Métodos abstractos que las subclases deberán implementar
    public abstract boolean esImportado();

    // Getters y Setters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getCantidad() { return cantidad; }
}


