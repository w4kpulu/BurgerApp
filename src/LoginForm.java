import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.List;
import javax.swing.*;

public class LoginForm extends JFrame {
    private final Font mainFont = new Font("Arial", Font.BOLD, 16);
    private final Font titleFont = new Font("Verdana", Font.BOLD, 24);

    public void initialize() {
        // Title Label
        JLabel lblLoginForm = new JLabel("Login Form", SwingConstants.CENTER);
        lblLoginForm.setFont(titleFont);
        lblLoginForm.setForeground(new Color(34, 45, 65));

        // Email Label and Input
        JLabel lblEmail = new JLabel("Email");
        lblEmail.setFont(mainFont);

        JTextField emailInput = new JTextField();
        emailInput.setFont(mainFont);
        emailInput.setPreferredSize(new Dimension(200, 30));

        // Password Label and Input
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(mainFont);

        JPasswordField passwordInput = new JPasswordField();
        passwordInput.setFont(mainFont);
        passwordInput.setPreferredSize(new Dimension(200, 30));

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(lblLoginForm, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        formPanel.add(lblEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        formPanel.add(emailInput, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        formPanel.add(lblPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        formPanel.add(passwordInput, gbc);

        // Login Button
        JButton btnLogin = new JButton("Login");
        btnLogin.setFont(mainFont);
        btnLogin.setBackground(new Color(34, 167, 240));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailInput.getText();
                String password = new String(passwordInput.getPassword());

                User user = getValidation(email, password);

                if (user != null) {
                    // Show a welcome message
                    JOptionPane.showMessageDialog(LoginForm.this, 
                                                  "Welcome, " + user.getName() + "!", 
                                                  "Login Successful", 
                                                  JOptionPane.INFORMATION_MESSAGE);

                    // After successful login, launch the MenuGUI
                    launchMenuGUI(); // Call to launch the menu
                } else {
                    // Show error message if validation fails
                    JOptionPane.showMessageDialog(LoginForm.this, 
                                                  "Invalid email or password.", 
                                                  "Login Failed", 
                                                  JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Sign Up Button
        JButton btnSignUp = new JButton("Sign Up");
        btnSignUp.setFont(mainFont);
        btnSignUp.setBackground(new Color(34, 167, 240));
        btnSignUp.setForeground(Color.WHITE);
        btnSignUp.setFocusPainted(false);
        btnSignUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    SignUpForm signUpForm = new SignUpForm();
                    signUpForm.signInitialize();
                    dispose(); // Close the login form
                });
            }
        });

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Center align buttons with spacing
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnSignUp);

        // Main Frame Layout
        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setTitle("Login Form");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(400, 500);
        setMinimumSize(new Dimension(350, 450));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private User getValidation(String email, String password) {
        try {
            List<User> users = User.loadUsers("UserInfo.txt");
            for (User user : users) {
                if (user.validateCredentials(email, password)) {
                    return user;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                                          "Error loading user data: " + e.getMessage(), 
                                          "Error", 
                                          JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    private void launchMenuGUI() {
        // Hide the login form
        setVisible(false);
        dispose();

        // Launch the MenuGUI
        SwingUtilities.invokeLater(() -> {
            MenuGUI menuGUI = new MenuGUI();
            menuGUI.setVisible(true);
        });
    }

    public static void main(String[] args) {
        // Start the LoginForm when the application runs
        SwingUtilities.invokeLater(() -> {
            LoginForm loginForm = new LoginForm();
            loginForm.initialize();
        });
    }
}