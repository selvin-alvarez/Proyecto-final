package miProyectoFinal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal {
    private JFrame frame;
    private JButton ordenCompraButton, ordenVentaButton, produccionButton, importacionButton, facturacionButton;

    public MenuPrincipal() {
        frame = new JFrame("Menú Principal");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(new Color(245, 245, 245)); // Fondo gris claro

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(new Color(245, 245, 245)); // Fondo gris claro

        // Título
        JLabel titleLabel = new JLabel("Menú Principal");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 102, 204)); // Azul oscuro
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(titleLabel);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Botones con diseño
        ordenCompraButton = crearBoton("Orden de Compra");
        ordenVentaButton = crearBoton("Orden de Venta");
        produccionButton = crearBoton("Producción");
        importacionButton = crearBoton("Importación");
        facturacionButton = crearBoton("Facturación");

        // Añadir botones al panel
        buttonPanel.add(ordenCompraButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(ordenVentaButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(produccionButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(importacionButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(facturacionButton);

        // Configurar GridBagConstraints para centrado
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        
        // Añadir panel de botones al marco
        frame.add(buttonPanel, gbc);

        // Funcionalidad de los botones
        ordenCompraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OrdenApp();
            }
        });

        ordenVentaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OrdenVentaApp();
            }
        });

        produccionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProduccionApp();
            }
        });

        importacionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ImportacionApp();
            }
        });

        facturacionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FacturacionApp(new Inventario());
            }
        });

        frame.setVisible(true);
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
                button.setBackground(new Color(25, 118, 210)); // Azul oscuro
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
    
    // Menú Principal
    public static void main(String[] args) {
        new MenuPrincipal();
    }
}

