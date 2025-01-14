import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        // Show the splash screen
        SplashScreen splash = new SplashScreen();
        splash.setVisible(true);

        // Simulate loading time
        try {
            Thread.sleep(3000); // 3 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Close the splash screen
        splash.setVisible(false);
        splash.dispose();

        // Launch the LoginForm
        SwingUtilities.invokeLater(() -> {
            LoginForm loginForm = new LoginForm();
            loginForm.initialize();
        });
    }
}
