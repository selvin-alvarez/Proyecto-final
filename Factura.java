package miProyectoFinal;

import java.util.List;

public class Factura {
	
	// Atributos
    private String cliente;
    private String direccion;
    private String nit;
    private String fecha;
    private List<Producto> productos;
    private double total;
    
    
    // Constructor
    public Factura(String cliente, String direccion, String nit, String fecha, List<Producto> productos, double total) {
        this.cliente = cliente;
        this.direccion = direccion;
        this.nit = nit;
        this.fecha = fecha;
        this.productos = productos;
        this.total = total;
    }

    // Getters y Setters
    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public List<Producto> getProductos() { return productos; }
    public void setProductos(List<Producto> productos) { this.productos = productos; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}

