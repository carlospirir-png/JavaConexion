
package main;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class CRUD extends JFrame {

    JTextField txtId, txtNombre, txtPrecio, txtCantidad;
    JButton btnInsertar, btnBuscar, btnActualizar, btnEliminar;

    Connection con;

    public CRUD() {
        setTitle("CRUD Productos");
        setSize(400, 300);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        conectar();

        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(20, 20, 100, 20);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(120, 20, 200, 20);
        add(txtId);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 60, 100, 20);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(120, 60, 200, 20);
        add(txtNombre);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setBounds(20, 100, 100, 20);
        add(lblPrecio);

        txtPrecio = new JTextField();
        txtPrecio.setBounds(120, 100, 200, 20);
        add(txtPrecio);

        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setBounds(20, 140, 100, 20);
        add(lblCantidad);

        txtCantidad = new JTextField();
        txtCantidad.setBounds(120, 140, 200, 20);
        add(txtCantidad);

        btnInsertar = new JButton("Insertar");
        btnInsertar.setBounds(20, 180, 90, 25);
        add(btnInsertar);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(120, 180, 90, 25);
        add(btnBuscar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(220, 180, 100, 25);
        add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(120, 220, 90, 25);
        add(btnEliminar);

        // EVENTOS
        btnInsertar.addActionListener(e -> insertar());
        btnBuscar.addActionListener(e -> buscar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());
    }

    public void conectar() {
        try {
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/tienda",
                "root",
                "admin" // tu contraseña
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error conexión: " + e.getMessage());
        }
    }

    public void insertar() {
        try {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO productos VALUES (?, ?, ?, ?)"
            );
            ps.setInt(1, Integer.parseInt(txtId.getText()));
            ps.setString(2, txtNombre.getText());
            ps.setDouble(3, Double.parseDouble(txtPrecio.getText()));
            ps.setInt(4, Integer.parseInt(txtCantidad.getText()));

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Insertado");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    public void buscar() {
        try {
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM productos WHERE id=?"
            );
            ps.setInt(1, Integer.parseInt(txtId.getText()));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                txtNombre.setText(rs.getString("nombre"));
                txtPrecio.setText(String.valueOf(rs.getDouble("precio")));
                txtCantidad.setText(String.valueOf(rs.getInt("cantidad")));
            } else {
                JOptionPane.showMessageDialog(this, "No encontrado");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    public void actualizar() {
        try {
            PreparedStatement ps = con.prepareStatement(
                "UPDATE productos SET nombre=?, precio=?, cantidad=? WHERE id=?"
            );
            ps.setString(1, txtNombre.getText());
            ps.setDouble(2, Double.parseDouble(txtPrecio.getText()));
            ps.setInt(3, Integer.parseInt(txtCantidad.getText()));
            ps.setInt(4, Integer.parseInt(txtId.getText()));

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Actualizado");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    public void eliminar() {
        try {
            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM productos WHERE id=?"
            );
            ps.setInt(1, Integer.parseInt(txtId.getText()));

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Eliminado");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}