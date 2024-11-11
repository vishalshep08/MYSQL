import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HomePage extends JFrame {
    private JButton ManageTeacherButton;
    private JButton ManageAssignmentButton;
    private JButton ManageSubmissionButton;
    private JButton ManageStudentButton;
    private JButton adminLoginButton;

    public HomePage() {
        // Set frame properties
        setTitle("Home Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400); // Increased height to accommodate new button
        setLocationRelativeTo(null); // Center the window on the screen
        setResizable(false);

        // Set layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding

        // Add components
        gbc.gridx = 0; gbc.gridy = 1; // First column, second row
        ManageTeacherButton = new JButton("Manage Teacher Details");
        ManageTeacherButton.setPreferredSize(new Dimension(150, 30));
        add(ManageTeacherButton, gbc);

        gbc.gridx = 1; // Second column
        ManageAssignmentButton = new JButton("Manage Assignment Details");
        ManageAssignmentButton.setPreferredSize(new Dimension(150, 30));
        add(ManageAssignmentButton, gbc);

        gbc.gridx = 0; gbc.gridy = 2; // First column, third row
        ManageSubmissionButton = new JButton("Manage Submission Details");
        ManageSubmissionButton.setPreferredSize(new Dimension(150, 30));
        add(ManageSubmissionButton, gbc);

        // Updated position for ManageStudentButton
        gbc.gridx = 1; gbc.gridy = 2; // Second column, third row
        ManageStudentButton = new JButton("Manage Student Details");
        ManageStudentButton.setPreferredSize(new Dimension(150, 30));
        add(ManageStudentButton, gbc);

        gbc.gridx = 0; gbc.gridy = 3; // First column, fourth row
        adminLoginButton = new JButton("Admin Login");
        adminLoginButton.setPreferredSize(new Dimension(150, 30));
        add(adminLoginButton, gbc);


        ManageTeacherButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open ManageTeacher JFrame
                ManageTeacher ManageTeacher = new ManageTeacher();
                ManageTeacher.setVisible(true);
            }
        });

        ManageAssignmentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open ManageAssignment JFrame
                ManageAssignment ManageAssignment = new ManageAssignment();
                ManageAssignment.setVisible(true);
            }
        });

        // Action listener for Manage Orders button
        ManageSubmissionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open ManageSubmission JFrame
                ManageSubmission ManageSubmission = new ManageSubmission();
                ManageSubmission.setVisible(true);
            }
        });

        //Manage the details of the student
        ManageStudentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open ManageStudent JFrame
                ManageStudent ManageStudent = new ManageStudent();
                ManageStudent.setVisible(true);
            }
        });

        adminLoginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open AdminLogin JFrame
                Login adminLogin = new Login();
                adminLogin.setVisible(true);
            }
        });

        // Customize the look and feel
        UIManager.put("Button.background", Color.BLUE);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Panel.background", Color.WHITE);

        // Set a background color
        getContentPane().setBackground(Color.WHITE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HomePage homePage = new HomePage();
            homePage.setVisible(true);
        });
    }
}