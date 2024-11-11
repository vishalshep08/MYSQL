import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ManageAssignment extends JFrame {
    private JTextField idField, titleField, startDateField, endDateField;
    private JButton addButton, updateButton, deleteButton;
    private Connection connection;

    public ManageAssignment() {
        // Set frame properties
        setTitle("Manage Assignment");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add padding

        // Initialize components
        gbc.gridx = 0; gbc.gridy = 0; // First row
        add(new JLabel("Assignment ID:"), gbc);
        gbc.gridx = 1;
        idField = new JTextField(10);
        add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; // Second row
        add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        titleField = new JTextField(10);
        add(titleField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; // Third row
        add(new JLabel("Start Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        startDateField = new JTextField(10);
        add(startDateField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; // Fourth row
        add(new JLabel("End Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        endDateField = new JTextField(10);
        add(endDateField, gbc);

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout()); // Align buttons in a single row

        addButton = new JButton("Add Assignment");
        updateButton = new JButton("Update Assignment");
        deleteButton = new JButton("Delete Assignment");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        gbc.gridx = 0; gbc.gridy = 4; // Fifth row for button panel
        gbc.gridwidth = 2; // Span across two columns
        add(buttonPanel, gbc);

        // Database connection
        connectToDatabase();

        // Add action listeners
        addButton.addActionListener(e -> addAssignment());
        updateButton.addActionListener(e -> updateAssignment());
        deleteButton.addActionListener(e -> deleteAssignment());
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

    private void addAssignment() {
        String id = idField.getText();
        String title = titleField.getText();
        String startDate = startDateField.getText();
        String endDate = endDateField.getText();

        String query = "INSERT INTO Assignments (Ass_id, Title, Start_date, End_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, Integer.parseInt(id));
            pstmt.setString(2, title);
            pstmt.setDate(3, Date.valueOf(startDate));
            pstmt.setDate(4, Date.valueOf(endDate));
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Assignment added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding assignment!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateAssignment() {
        String id = idField.getText();
        String title = titleField.getText();
        String startDate = startDateField.getText();
        String endDate = endDateField.getText();

        String query = "UPDATE Assignments SET Title = ?, Start_date = ?, End_date = ? WHERE Ass_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, title);
            pstmt.setDate(2, Date.valueOf(startDate));
            pstmt.setDate(3, Date.valueOf(endDate));
            pstmt .setInt(4, Integer.parseInt(id));
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Assignment updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Assignment not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating assignment!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteAssignment() {
        String id = idField.getText();

        String query = "DELETE FROM Assignments WHERE Ass_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, Integer.parseInt(id));
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Assignment deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Assignment not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting assignment!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ManageAssignment manageAssignment = new ManageAssignment();
            manageAssignment.setVisible(true);
        });
    }
}