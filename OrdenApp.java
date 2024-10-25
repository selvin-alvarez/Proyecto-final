package miProyectoFinal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrdenApp {
	
	// Atributos
    private JFrame frame;
    private JTextField idField, nombreField, precioField, cantidadField;
    private JCheckBox esImportadoBox;
    private JButton agregarButton, guardarButton;
    private Inventario inventario;

    public OrdenApp() {
        inventario = new Inventario();
        frame = new JFrame("Orden de Compra");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
        dataPanel.setBackground(new Color(245, 245, 245));
        
        JLabel titleLabel = new JLabel("Crear Orden de Compra");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 102, 204));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        dataPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        dataPanel.add(titleLabel);
        dataPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        dataPanel.add(crearLabel("ID del Producto:"));
        idField = new JTextField(20);
        dataPanel.add(idField);
        dataPanel.add(crearLabel("Nombre del Producto:"));
        nombreField = new JTextField(20);
        dataPanel.add(nombreField);
        dataPanel.add(crearLabel("Precio:"));
        precioField = new JTextField(20);
        dataPanel.add(precioField);
        dataPanel.add(crearLabel("Cantidad:"));
        cantidadField = new JTextField(20);
        dataPanel.add(cantidadField);
        esImportadoBox = new JCheckBox("Importado");
        esImportadoBox.setBackground(new Color(245, 245, 245));
        dataPanel.add(esImportadoBox);
        
        agregarButton = crearBoton("Agregar Producto");
        guardarButton = crearBoton("Guardar Orden");
        
        dataPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        dataPanel.add(agregarButton);
        dataPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        dataPanel.add(guardarButton);
        
        frame.add(dataPanel, BorderLayout.CENTER);
        
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String nombre = nombreField.getText();
                boolean esImportado = esImportadoBox.isSelected();
                double precio = Double.parseDouble(precioField.getText());
                int cantidad = Integer.parseInt(cantidadField.getText());
                Producto producto = ProductoFactory.crearProducto(id, nombre, esImportado, precio, cantidad);
                inventario.agregarProducto(producto);
                JOptionPane.showMessageDialog(frame, "¡Producto agregado a la orden!");
                limpiarCampos();
            }
        });
        
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inventario.guardarEnBaseDeDatos("inventario");
                inventario.guardarEnJson("inventario.json");
                JOptionPane.showMessageDialog(frame, "¡Orden guardada!");
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

    
    // Limpiar
    private void limpiarCampos() {
        idField.setText("");
        nombreField.setText("");
        precioField.setText("");
        cantidadField.setText("");
        esImportadoBox.setSelected(false);
    }

    public static void main(String[] args) {
        new OrdenApp();
    }
}
        


