import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ManageSubmission extends JFrame {
    private JTextField idField, studIdField, assIdField, submissionDateField, statusField;
    private JButton addButton, updateButton, deleteButton;
    private Connection connection;

    public ManageSubmission() {
        // Set frame properties
        setTitle("Manage Submission");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add padding

        // Initialize components
        gbc.gridx = 0; gbc.gridy = 0; // First row
        add(new JLabel("Submission ID:"), gbc);
        gbc.gridx = 1;
        idField = new JTextField(10);
        add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; // Second row
        add(new JLabel("Student ID:"), gbc);
        gbc.gridx = 1;
        studIdField = new JTextField(10);
        add(studIdField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; // Third row
        add(new JLabel("Assignment ID:"), gbc);
        gbc.gridx = 1;
        assIdField = new JTextField(10);
        add(assIdField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; // Fourth row
        add(new JLabel("Submission Date:"), gbc);
        gbc.gridx = 1;
        submissionDateField = new JTextField(10);
        add(submissionDateField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; // Fifth row
        add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        statusField = new JTextField(10);
        add(statusField, gbc);

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout()); // Align buttons in a single row

        addButton = new JButton("Add Submission");
        updateButton = new JButton("Update Submission");
        deleteButton = new JButton("Delete Submission");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        gbc.gridx = 0; gbc.gridy = 5; // Sixth row for button panel
        gbc.gridwidth = 2; // Span across two columns
        add(buttonPanel, gbc);

        // Database connection
        connectToDatabase();

        // Add action listeners
        addButton.addActionListener(e -> addSubmission());
        updateButton.addActionListener(e -> updateSubmission());
        deleteButton.addActionListener(e -> deleteSubmission());
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

    private void addSubmission() {
        String id = idField.getText();
        String studId = studIdField.getText();
        String assId = assIdField.getText();
        String submissionDate = submissionDateField.getText();
        String status = statusField.getText();

        String query = "INSERT INTO Submissions (id, Stud_id, Ass_id, submission_date, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, Integer.parseInt(id));
            pstmt.setInt(2, Integer.parseInt(studId));
            pstmt.setInt(3, Integer.parseInt(assId));
            pstmt.setDate(4, Date.valueOf(submissionDate));
            pstmt.setString(5, status);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Submission added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding submission!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateSubmission() {
        String id = idField.getText();
        String studId = studIdField.getText();
        String assId = assIdField.getText();
        String submissionDate = submissionDateField.getText();
        String status = statusField.getText();

        String query = "UPDATE Submissions SET Stud_id = ?, Ass_id = ?, submission_date = ?, status = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, Integer.parseInt(studId));
            pstmt.setInt(2, Integer.parseInt(assId));
            pstmt.setDate(3, Date.valueOf(submissionDate));
            pstmt.setString(4, status);
            pstmt.setInt(5, Integer.parseInt(id));
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Submission updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Submission not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating submission!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSubmission() {
        String id = idField.getText();

        String query = "DELETE FROM Submissions WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, Integer.parseInt(id));
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Submission deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Submission not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting submission!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ManageSubmission manageSubmission = new ManageSubmission();
            manageSubmission.setVisible(true);
        });
    }
}