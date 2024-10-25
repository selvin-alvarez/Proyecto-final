package miProyectoFinal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;


public class Inventario {
    private List<Producto> productos = new ArrayList<>();

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public List<Producto> getProductos() {
        return productos;
    }

    // Método para guardar en la base de datos
    public void guardarEnBaseDeDatos(String tabla) {
        try (Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/selvindb", "selvindb", "SHAN1985")) {
            for (Producto producto : productos) {
                String query = "INSERT INTO " + tabla + " (id, nombre, esImportado, precio, cantidad, modificado) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement pst = con.prepareStatement(query)) {
                    pst.setString(1, producto.getId());
                    pst.setString(2, producto.getNombre());
                    pst.setBoolean(3, producto.esImportado());
                    pst.setDouble(4, producto.getPrecio());
                    pst.setInt(5, producto.getCantidad());
                    pst.setBoolean(6, false); // Por defecto, no modificado
                    pst.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para guardar en un archivo JSON
    public void guardarEnJson(String archivo) {
        Gson gson = new Gson();
        try (FileWriter escritor = new FileWriter(archivo)) {
            for (Producto producto : productos) {
                String jsonProducto = gson.toJson(producto);
                escritor.write(jsonProducto + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para guardar en la base de datos con opción de modificar productos
    public void guardarOrdenVentaEnBaseDeDatos() {
        try (Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/selvindb", "selvindb", "SHAN1985")) {
            for (Producto producto : productos) {
                // Si el producto existe, actualiza; si no, inserta
                String query = "INSERT INTO orden_venta (id, nombre, esImportado, precio, cantidad, modificado) VALUES (?, ?, ?, ?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE nombre = VALUES(nombre), esImportado = VALUES(esImportado), precio = VALUES(precio), cantidad = VALUES(cantidad), modificado = VALUES(modificado)";
                try (PreparedStatement pst = con.prepareStatement(query)) {
                    pst.setString(1, producto.getId());
                    pst.setString(2, producto.getNombre());
                    pst.setBoolean(3, producto.esImportado());
                    pst.setDouble(4, producto.getPrecio());
                    pst.setInt(5, producto.getCantidad());
                    pst.setBoolean(6, true); // Indicar que el producto ha sido modificado
                    pst.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para guardar orden de venta en un archivo JSON
    public void guardarOrdenVentaEnJson() {
        Gson gson = new Gson();
        try (FileWriter escritor = new FileWriter("orden_venta.json")) {
            for (Producto producto : productos) {
                String jsonProducto = gson.toJson(producto);
                escritor.write(jsonProducto + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


