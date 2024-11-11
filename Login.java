import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Login extends JFrame {
    private JTextField idField;
    private JPasswordField passField;
    private JButton submitButton;

    public Login() {
        // Set frame properties
        setTitle("Admin Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null); // Center the window on the screen
        setResizable(false);

        // Set layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding

        // Add components
        gbc.gridx = 0; gbc.gridy = 0; // First column, first row
        add(new JLabel("Username:"), gbc);

        gbc.gridx = 1; // Second column
        idField = new JTextField(15);
        add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; // First column, second row
        add(new JLabel("Password:"), gbc);

        gbc.gridx = 1; // Second column
        passField = new JPasswordField(15);
        add(passField, gbc);

        gbc.gridx = 1; gbc.gridy = 2; // Second column, third row
        submitButton = new JButton("Login");
        submitButton.setPreferredSize(new Dimension(100, 30)); // Set button size
        add(submitButton, gbc);

        // Add action listener for the button
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String pass = new String(passField.getPassword());

                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/management", "root", "Vishal@4983");
                    PreparedStatement pst = conn.prepareStatement("SELECT * FROM admin WHERE login_id=? AND password=?");
                    pst.setString(1, id);
                    pst.setString(2, pass);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next()) {
                        // Login successful, open the home page
                        HomePage homePage = new HomePage();
                        homePage.setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid username or password");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            }
        });

        // Customize the look and feel
        UIManager.put("Button.background", Color.BLUE);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("TextField.background", Color.LIGHT_GRAY);
        UIManager.put("TextField.foreground", Color.BLACK);
        UIManager.put("PasswordField.background", Color.LIGHT_GRAY);
        UIManager.put("PasswordField.foreground", Color.BLACK);

        // Set a background color
        getContentPane().setBackground(Color.WHITE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login login = new Login();
            login.setVisible(true);
        });
    }
}