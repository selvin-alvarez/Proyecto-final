package miProyectoFinal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;

public class Importacion {
    private List<Producto> productos;

    public Importacion() {
        productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public void modificarProducto(String id, Producto nuevoProducto) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getId().equals(id)) {
                productos.set(i, nuevoProducto);
                return;
            }
        }
    }

    public void eliminarProducto(String id) {
        productos.removeIf(producto -> producto.getId().equals(id));
    }

    public List<Producto> getReporte() {
        return productos;
    }

    public void guardarCambios(String tabla, String archivo) {
        guardarEnBaseDeDatos(tabla);
        guardarEnJson(archivo);
    }


    // Guardar en base de datos
    private void guardarEnBaseDeDatos(String tabla) {
        try (Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/selvindb", "selvindb", "SHAN1985")) {
            for (Producto producto : productos) {
                String query = "INSERT INTO " + tabla + " (id, nombre, esImportado, precio, cantidad, modificado, estado) VALUES (?, ?, ?, ?, ?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE nombre = VALUES(nombre), esImportado = VALUES(esImportado), precio = VALUES(precio), cantidad = VALUES(cantidad), modificado = VALUES(modificado), estado = VALUES(estado)";
                try (PreparedStatement pst = con.prepareStatement(query)) {
                    pst.setString(1, producto.getId());
                    pst.setString(2, producto.getNombre());
                    pst.setBoolean(3, producto.esImportado());
                    pst.setDouble(4, producto.getPrecio());
                    pst.setInt(5, producto.getCantidad());
                    pst.setBoolean(6, true);
                    if (producto instanceof ProductoImportacion) {
                        pst.setString(7, ((ProductoImportacion) producto).getEstado());
                    } else {
                        pst.setNull(7, java.sql.Types.VARCHAR);
                    }
                    int rowsAffected = pst.executeUpdate();
                    System.out.println("Filas afectadas: " + rowsAffected);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Guardar en archivo Json
    private void guardarEnJson(String archivo) {
        Gson gson = new Gson();
        try (FileWriter escritor = new FileWriter(archivo)) {
            for (Producto producto : productos) {
                String jsonProducto = gson.toJson(producto);
                escritor.write(jsonProducto + System.lineSeparator());
            }
            System.out.println("Archivo JSON guardado correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
