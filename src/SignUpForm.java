import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;

public class SignUpForm extends JFrame {
    private final Font mainFont = new Font("Arial", Font.BOLD, 16);
    private final Font titleFont = new Font("Verdana", Font.BOLD, 24);

    public void signInitialize() {
        // Title label
        JLabel lblSignForm = new JLabel("Sign Up Form", SwingConstants.CENTER);
        lblSignForm.setFont(titleFont);
        lblSignForm.setForeground(new Color(34, 45, 65));

        // Name Label and Input
        JLabel lblName = new JLabel("Name");
        lblName.setFont(mainFont);

        JTextField nameInput = new JTextField();
        nameInput.setFont(mainFont);
        nameInput.setPreferredSize(new Dimension(200, 30));

        // Email Label and Input
        JLabel lblEmail = new JLabel("Email");
        lblEmail.setFont(mainFont);

        JTextField emailInput = new JTextField();
        emailInput.setFont(mainFont);
        emailInput.setPreferredSize(new Dimension(200, 30));

        // Phone Number Label and Input
        JLabel lblPhoneNo = new JLabel("Phone Number");
        lblPhoneNo.setFont(mainFont);

        JTextField phoneInput = new JTextField();
        phoneInput.setFont(mainFont);
        phoneInput.setPreferredSize(new Dimension(200, 30));

        // Address Label and Input
        JLabel lblAddress = new JLabel("Address");
        lblAddress.setFont(mainFont);

        JTextField adrInput = new JTextField();
        adrInput.setFont(mainFont);
        adrInput.setPreferredSize(new Dimension(200, 30));

        // Password Label and Input
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(mainFont);

        JPasswordField passInput = new JPasswordField();
        passInput.setFont(mainFont);
        passInput.setPreferredSize(new Dimension(200, 30));

        // Form Panel
        JPanel signPanel = new JPanel();
        signPanel.setLayout(new GridBagLayout());
        signPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        signPanel.add(lblSignForm, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        signPanel.add(lblName, gbc);

        gbc.gridy++;
        gbc.gridwidth = 2;
        signPanel.add(nameInput, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        signPanel.add(lblEmail, gbc);

        gbc.gridy++;
        gbc.gridwidth = 2;
        signPanel.add(emailInput, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        signPanel.add(lblPhoneNo, gbc);

        gbc.gridy++;
        gbc.gridwidth = 2;
        signPanel.add(phoneInput, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        signPanel.add(lblAddress, gbc);

        gbc.gridy++;
        gbc.gridwidth = 2;
        signPanel.add(adrInput, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        signPanel.add(lblPassword, gbc);

        gbc.gridy++;
        gbc.gridwidth = 2;
        signPanel.add(passInput, gbc);

        // Sign Up Button
        JButton btnSignUp = new JButton("Sign Up");
        btnSignUp.setFont(mainFont);
        btnSignUp.setBackground(new Color(34, 167, 240));
        btnSignUp.setForeground(Color.WHITE);
        btnSignUp.setFocusPainted(false);
        btnSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameInput.getText();
                String email = emailInput.getText();
                String phone = phoneInput.getText();
                String address = adrInput.getText();
                String password = new String(passInput.getPassword());

                if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(SignUpForm.this, 
                                                  "All fields are required!", 
                                                  "Input Error", 
                                                  JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try (BufferedWriter writer = new BufferedWriter(new FileWriter("UserInfo.txt", true))) {
                    writer.write(name + "," + email + "," + phone + "," + address + "," + password);
                    writer.newLine();
                    JOptionPane.showMessageDialog(SignUpForm.this, 
                                                  "User information saved successfully!", 
                                                  "Success", 
                                                  JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(SignUpForm.this, 
                                                  "Error saving user information: " + ex.getMessage(), 
                                                  "Error", 
                                                  JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Back to Login Button
        JButton btnBackToLogin = new JButton("Back to Log in");
        btnBackToLogin.setFont(mainFont);
        btnBackToLogin.setBackground(new Color(34, 167, 240));
        btnBackToLogin.setForeground(Color.WHITE);
        btnBackToLogin.setFocusPainted(false);
        btnBackToLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    LoginForm backLoginForm = new LoginForm();
                    backLoginForm.initialize();
                    dispose(); // Close SignUpForm
                });
            }
        });

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Center align buttons
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.add(btnSignUp);
        buttonPanel.add(btnBackToLogin);

        // Main Frame Layout
        setLayout(new BorderLayout());
        add(signPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setTitle("Sign Up Form");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(400, 600);
        setMinimumSize(new Dimension(350, 500));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SignUpForm signup = new SignUpForm();
            signup.signInitialize();
        });
    }
}
