import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ManageStudent extends JFrame {
    private JTextField idField, nameField, phoneField, addressField;
    private JButton addButton, updateButton, deleteButton;
    private Connection connection;

    public ManageStudent() {
        // Set frame properties
        setTitle("Manage Student");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding

        // Initialize components
        gbc.gridx = 0; gbc.gridy = 0; // First row
        add(new JLabel("Student ID:"), gbc);
        gbc.gridx = 1;
        idField = new JTextField(15);
        add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; // Second row
        add(new JLabel("Student Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(15);
        add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; // Third row
        add(new JLabel("Phone Number:"), gbc);
        gbc.gridx = 1;
        phoneField = new JTextField(15);
        add(phoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; // Fourth row
        add(new JLabel("Address:"), gbc);
        gbc.gridx = 1;
        addressField = new JTextField(15);
        add(addressField, gbc);

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout()); // Align buttons in a single row

        addButton = new JButton("Add Student");
        updateButton = new JButton("Update Student");
        deleteButton = new JButton("Delete Student");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        gbc.gridx = 0; gbc.gridy = 4; // Fifth row for button panel
        gbc.gridwidth = 2; // Span across two columns
        add(buttonPanel, gbc);

        // Database connection
        connectToDatabase();

        // Add action listeners
        addButton.addActionListener(e -> addStudent());
        updateButton.addActionListener(e -> updateStudent());
        deleteButton.addActionListener(e -> deleteStudent());
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

    private void addStudent() {
        String id = idField.getText();
        String name = nameField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();

        String query = "INSERT INTO Students (Stud_id, Stud_name, Phone_no, Address) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, Integer.parseInt(id));
            pstmt.setString(2, name);
            pstmt.setInt(3, Integer.parseInt(phone));
            pstmt.setString(4, address);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Student added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding student!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateStudent() {
        String id = idField.getText();
        String name = nameField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();

        String query = "UPDATE Students SET Stud_name = ?, Phone_no = ?, Address = ? WHERE Stud_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, Integer.parseInt(phone));
            pstmt.setString(3, address);
            pstmt.setInt(4, Integer.parseInt(id));
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Student updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Student not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating student!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteStudent() {
        String id = idField.getText();

        String query = "DELETE FROM Students WHERE Stud_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, Integer.parseInt(id));
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Student deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Student not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting student!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ManageStudent manageStudent = new ManageStudent();
            manageStudent.setVisible(true);
        });
    }
}