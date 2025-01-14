import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SplashScreen extends JWindow {
    private JProgressBar progressBar;
    private Timer fadeInTimer;
    private Timer progressTimer;
    private float opacity = 0f;
    private int progress = 0;

    public SplashScreen() {
        // Set the size of the splash screen
        int width = 400;
        int height = 300;  // Increased height for the image
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);

        // Create a panel for the splash content
        JPanel content = new JPanel();
        content.setBackground(Color.WHITE);
        content.setLayout(new BorderLayout());

        // Load the image (Krabby.png) and resize it
        ImageIcon imageIcon = new ImageIcon("Krabby.png");  // Ensure the image path is correct
        Image image = imageIcon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);  // Resize image to 200x150 pixels
        imageIcon = new ImageIcon(image);  // Create new ImageIcon from resized image

        // Add the resized image to the splash screen
        JLabel imageLabel = new JLabel(imageIcon);
        content.add(imageLabel, BorderLayout.CENTER);  // Place the image at the center

        // Add a label for the splash text
        JLabel label = new JLabel("STREET BURGER", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setForeground(Color.BLACK);  // Set text color to black
        content.add(label, BorderLayout.NORTH);  // Place text at the top

        // Create a progress bar that fills up over time
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(progress);
        progressBar.setStringPainted(true); // Show progress as a percentage
        progressBar.setIndeterminate(false);
        content.add(progressBar, BorderLayout.SOUTH);  // Place progress bar at the bottom

        // Add content to the splash screen
        add(content);

        // Set initial opacity to 0 (invisible)
        setOpacity(opacity);

        // Fade-in Timer (opacity from 0 to 1)
        fadeInTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (opacity < 1f) {
                    opacity += 0.05f; // Increase opacity
                    opacity = Math.min(opacity, 1.0f); // Ensure opacity is not greater than 1.0
                    setOpacity(opacity);
                } else {
                    fadeInTimer.stop(); // Stop the fade-in effect
                }
            }
        });

        // Progress Timer (simulate loading by updating the progress bar)
        progressTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                progress += 1; // Increment the progress
                progressBar.setValue(progress);

                // When the progress reaches 100, stop the progress timer
                if (progress >= 100) {
                    progressTimer.stop();
                    setVisible(false);
                    dispose();

                    // Launch the LoginForm
                    SwingUtilities.invokeLater(() -> {
                        LoginForm loginForm = new LoginForm();
                        loginForm.initialize();
                    });
                }
            }
        });
    }

    public void showSplash() {
        // Start fade-in animation
        fadeInTimer.start();

        // Start progress bar animation (simulate loading)
        progressTimer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (opacity < 1.0f) {
            // Apply the opacity to the splash screen window
            Graphics2D g2d = (Graphics2D) g;
            AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
            g2d.setComposite(alphaComposite);
        }
    }

    // Method to update the progress bar from App class
    public void updateProgress(int progress) {
        progressBar.setValue(progress);
    }

    public static void main(String[] args) {
        // Show the splash screen
        SplashScreen splash = new SplashScreen();
        splash.setVisible(true);

        // Ensure that the splash screen animation starts after the window is visible
        SwingUtilities.invokeLater(() -> splash.showSplash());
    }
}
