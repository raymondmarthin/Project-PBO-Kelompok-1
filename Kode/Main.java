

import javax.swing.SwingUtilities;
import views.LoginForm;

public class Main {
    public static void main(String[] args) {
        // Jalankan LoginForm di thread Swing
        SwingUtilities.invokeLater(() -> {
            LoginForm login = new LoginForm();
            login.setVisible(true);
        });
    }
}
