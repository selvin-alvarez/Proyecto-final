package miProyectoFinal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ImportacionApp {
	
	// Atributos
    private JFrame frame;
    private JTextField idField, nombreField, estadoField, cantidadField;
    private JButton agregarButton, modificarButton, eliminarButton, guardarButton;
    private Importacion importacion;

    public ImportacionApp() {
        importacion = new Importacion();
        frame = new JFrame("Importación");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
        dataPanel.setBackground(new Color(245, 245, 245));

        JLabel titleLabel = new JLabel("Gestión de Importación");
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

        dataPanel.add(crearLabel("Estado:"));
        estadoField = new JTextField(20);
        dataPanel.add(estadoField);

        dataPanel.add(crearLabel("Cantidad:"));
        cantidadField = new JTextField(20);
        dataPanel.add(cantidadField);

        agregarButton = crearBoton("Agregar Producto");
        modificarButton = crearBoton("Modificar Producto");
        eliminarButton = crearBoton("Eliminar Producto");
        guardarButton = crearBoton("Guardar Cambios");

        dataPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        dataPanel.add(agregarButton);
        dataPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        dataPanel.add(modificarButton);
        dataPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        dataPanel.add(eliminarButton);
        dataPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        dataPanel.add(guardarButton);

        frame.add(dataPanel, BorderLayout.CENTER);

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = idField.getText();
                    String nombre = nombreField.getText();
                    String estado = estadoField.getText();
                    int cantidad = Integer.parseInt(cantidadField.getText());
                    ProductoImportacion producto = new ProductoImportacion(id, nombre, 0, cantidad, estado);
                    importacion.agregarProducto(producto);
                    JOptionPane.showMessageDialog(frame, "¡Producto agregado!");
                    limpiarCampos();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Error: Cantidad debe ser un número entero.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Ha ocurrido un error al agregar el producto.");
                }
            }
        });

        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = idField.getText();
                    String nombre = nombreField.getText();
                    String estado = estadoField.getText();
                    int cantidad = Integer.parseInt(cantidadField.getText());
                    ProductoImportacion producto = new ProductoImportacion(id, nombre, 0, cantidad, estado);
                    importacion.modificarProducto(id, producto);
                    JOptionPane.showMessageDialog(frame, "¡Producto modificado!");
                    limpiarCampos();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Error: Cantidad debe ser un número entero.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Ha ocurrido un error al modificar el producto.");
                }
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = idField.getText();
                    importacion.eliminarProducto(id);
                    JOptionPane.showMessageDialog(frame, "¡Producto eliminado!");
                    limpiarCampos();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Ha ocurrido un error al eliminar el producto.");
                }
            }
        });

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importacion.guardarCambios("importacion", "importacion.json");
                JOptionPane.showMessageDialog(frame, "¡Cambios guardados!");
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
        estadoField.setText("");
        cantidadField.setText("");
    }

    public static void main(String[] args) {
        new ImportacionApp();
    }
}