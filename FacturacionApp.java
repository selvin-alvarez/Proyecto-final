package miProyectoFinal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FacturacionApp {
	
	// Atributos
    private JFrame frame;
    private JTextField clienteField, direccionField, nitField, fechaField;
    private JTextArea productosArea;
    private JButton calcularButton, guardarButton;
    private Inventario inventario;
    private double total;

    public FacturacionApp(Inventario inventario) {
        this.inventario = inventario;
        frame = new JFrame("Facturación");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
        dataPanel.setBackground(new Color(245, 245, 245));

        JLabel titleLabel = new JLabel("Crear Factura");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 102, 204));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        dataPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        dataPanel.add(titleLabel);
        dataPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Datos del Cliente
        dataPanel.add(crearLabel("Nombre del Cliente:"));
        clienteField = new JTextField(20);
        dataPanel.add(clienteField);

        dataPanel.add(crearLabel("Dirección:"));
        direccionField = new JTextField(20);
        dataPanel.add(direccionField);

        dataPanel.add(crearLabel("NIT:"));
        nitField = new JTextField(20);
        dataPanel.add(nitField);

        dataPanel.add(crearLabel("Fecha (dd/MM/yyyy):"));
        fechaField = new JTextField(20);
        dataPanel.add(fechaField);

        productosArea = new JTextArea(10, 40);
        productosArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(productosArea);
        dataPanel.add(crearLabel("Productos:"));
        dataPanel.add(scrollPane);
        
        calcularButton = crearBoton("Calcular Total");
        guardarButton = crearBoton("Guardar Factura");

        dataPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        dataPanel.add(calcularButton);
        dataPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        dataPanel.add(guardarButton);

        frame.add(dataPanel, BorderLayout.CENTER);

        calcularButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularTotalDesdeDB();
                productosArea.append("\nTotal: " + total);
            }
        });

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarFactura();
                JOptionPane.showMessageDialog(frame, "¡Factura guardada!");
            }
        });

        frame.setVisible(true);
    }

    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(new Color(33, 150, 243));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JButton crearBoton(String texto) {
        JButton button = new JButton(texto);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 40));
        button.setBackground(new Color(33, 150, 243));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(25, 118, 210));
                button.setForeground(Color.BLACK);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(33, 150, 243));
                button.setForeground(Color.WHITE);
            }
        });
        return button;
    }

    // Total factura
    private void calcularTotalDesdeDB() {
        total = 0;
        productosArea.setText("");
        List<Producto> productos = new ArrayList<>();

        try (Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/selvindb", "selvindb", "SHAN1985")) {
            String query = "SELECT id, nombre, esImportado, precio, cantidad FROM orden_venta";
            
            try (PreparedStatement pst = con.prepareStatement(query);
                 ResultSet rs = pst.executeQuery()) {

                while (rs.next()) {
                    String id = rs.getString("id");
                    String nombre = rs.getString("nombre");
                    boolean esImportado = rs.getBoolean("esImportado");
                    double precio = rs.getDouble("precio");
                    int cantidad = rs.getInt("cantidad");

                    Producto producto = new Producto(id, nombre, precio, cantidad) {
                        @Override
                        public boolean esImportado() {
                            return esImportado;
                        }
                    };
                    
                    // Productos
                    productos.add(producto);
                    productosArea.append("ID: " + producto.getId() + "\n");
                    productosArea.append("Nombre: " + producto.getNombre() + "\n");
                    productosArea.append("Importado: " + producto.esImportado() + "\n");
                    productosArea.append("Precio: " + producto.getPrecio() + "\n");
                    productosArea.append("Cantidad: " + producto.getCantidad() + "\n");
                    productosArea.append("\n");
                    total += producto.getPrecio() * producto.getCantidad();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        inventario.getProductos().clear();
        inventario.getProductos().addAll(productos);
    }

    // Guardar factura en base de datos
    private void guardarFactura() {
        String cliente = clienteField.getText();
        String direccion = direccionField.getText();
        String nit = nitField.getText();
        String fecha = fechaField.getText();
        
        try (Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/selvindb", "selvindb", "SHAN1985")) {
            String query = "INSERT INTO facturas (cliente, direccion, nit, fecha, total) VALUES (?, ?, ?, ?, ?)";
            
            try (PreparedStatement pst = con.prepareStatement(query)) {
                pst.setString(1, cliente);
                pst.setString(2, direccion);
                pst.setString(3, nit);
                pst.setString(4, fecha);
                pst.setDouble(5, total);
                pst.executeUpdate();
            }
            
            guardarFacturaEnJson(cliente, direccion, nit, fecha);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // guardar factra en archivo json
    private void guardarFacturaEnJson(String cliente, String direccion, String nit, String fecha) {
        Gson gson = new Gson();
        Factura factura = new Factura(cliente, direccion, nit, fecha, new ArrayList<>(), total);

        try (FileWriter escritor = new FileWriter("factura.json")) {
            // Serializar solo la factura sin la lista de productos vacía
            gson.toJson(factura, escritor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }

    public static void main(String[] args) {
        new FacturacionApp(new Inventario());
    }
}