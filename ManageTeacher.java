import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ManageTeacher extends JFrame {
    private JTextField idField, nameField, emailField;
    private JButton addButton, updateButton, deleteButton;
    private Connection connection;

    public ManageTeacher() {
        // Set frame properties
        setTitle("Manage Teacher");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2));

        // Initialize components
        add(new JLabel("Teacher ID:"));
        idField = new JTextField();
        add(idField);

        add(new JLabel("Teacher Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        addButton = new JButton("Add Teacher");
        updateButton = new JButton("Update Teacher");
        deleteButton = new JButton("Delete Teacher");

        add(addButton);
        add(updateButton);
        add(deleteButton);

        // Database connection
        connectToDatabase();

        // Add action listeners
        addButton.addActionListener(e -> addTeacher());
        updateButton.addActionListener(e -> updateTeacher());
        deleteButton.addActionListener(e -> deleteTeacher());
    }

    private void connectToDatabase() {
        try {
            // Replace with your database URL, username, and password
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/management", "root", "Vishal@4983");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database connection failed!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addTeacher() {
        String id = idField.getText();
        String name = nameField.getText();
        String email = emailField.getText();

        String query = "INSERT INTO Teachers (Te_id, Te_name, email) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, Integer.parseInt(id));
            pstmt.setString(2, name);
            pstmt.setString(3, email);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Teacher added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding teacher!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTeacher() {
        String id = idField.getText();
        String name = nameField.getText();
        String email = emailField.getText();

        String query = "UPDATE Teachers SET Te_name = ?, email = ? WHERE Te_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setInt(3, Integer.parseInt(id));
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Teacher updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Teacher not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating teacher!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteTeacher() {
        String id = idField.getText();

        String query = "DELETE FROM Teachers WHERE Te_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, Integer.parseInt(id));
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Teacher deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Teacher not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting teacher!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ManageTeacher manageTeacher = new ManageTeacher();
            manageTeacher.setVisible(true);
        });
    }
}