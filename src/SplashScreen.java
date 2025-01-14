import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JWindow {

    public SplashScreen() {
        // Set the size of the splash screen
        int width = 400;
        int height = 200;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);

        // Create a panel for the splash content
        JPanel content = new JPanel();
        content.setBackground(Color.WHITE);
        content.setLayout(new BorderLayout());

        // Add a label for the splash image or text
        JLabel label = new JLabel("Burger App", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        content.add(label, BorderLayout.CENTER);
        
        // Optional: Add a progress bar
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        content.add(progressBar, BorderLayout.SOUTH);

        // Add content to the splash screen
        add(content);
    }

    

}
