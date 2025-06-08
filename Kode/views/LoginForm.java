package views;

import controllers.AuthController;
import models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class LoginForm extends JFrame {

    private final JTextField usernameField = new JTextField(15);
    private final JPasswordField passwordField = new JPasswordField(15);
    private final JButton loginBtn = new JButton("Masuk");
    private Point initialClick;

    public LoginForm() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}

        setTitle("Login • Sistem Parkir");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true); // Borderless window
        setSize(400, 480);
        setLocationRelativeTo(null);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20)); // Rounded corners

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Title
        JLabel title = new JLabel("🚗 ParkirKu", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(25, 118, 210));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(title, BorderLayout.PAGE_START);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(30, 20, 30, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridwidth = 2;

        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        loginBtn.setBackground(new Color(33, 150, 243));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        loginBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginBtn.setPreferredSize(new Dimension(120, 40));
        loginBtn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        // Exit Button
        JButton exitBtn = new JButton("Keluar");
        exitBtn.setBackground(new Color(244, 67, 54));
        exitBtn.setForeground(Color.WHITE);
        exitBtn.setFocusPainted(false);
        exitBtn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        exitBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        exitBtn.setPreferredSize(new Dimension(100, 35));
        exitBtn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        exitBtn.addActionListener(e -> System.exit(0));

        // Menambahkan Komponen Ke Form Panel
        gbc.gridy = 0;
        formPanel.add(new JLabel("Username"), gbc);
        gbc.gridy++;
        formPanel.add(usernameField, gbc);
        gbc.gridy++;
        formPanel.add(new JLabel("Password"), gbc);
        gbc.gridy++;
        formPanel.add(passwordField, gbc);
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(loginBtn, gbc);
        gbc.gridy++;
        formPanel.add(exitBtn, gbc); // Exit button

        // Main Panel
        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);

        // Drag Window
        mainPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                getComponentAt(initialClick);
            }
        });

        mainPanel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int thisX = getLocation().x;
                int thisY = getLocation().y;

                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;

                int X = thisX + xMoved;
                int Y = thisY + yMoved;

                setLocation(X, Y);
            }
        });

        // Logika Login
        Runnable doLogin = () -> {
            String user = usernameField.getText().trim();
            String pass = new String(passwordField.getPassword()).trim();

            User u = AuthController.login(user, pass);
            if (u != null) {
                dispose();
                if ("admin".equalsIgnoreCase(u.getRole())) {
                    new AdminDashboard(u).setVisible(true);
                } else {
                    new StaffDashboard(u).setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Username atau password salah!",
                        "Login Gagal",
                        JOptionPane.ERROR_MESSAGE
                );
                passwordField.setText("");
                passwordField.requestFocusInWindow();
            }
        };

        loginBtn.addActionListener(e -> doLogin.run());
        passwordField.addActionListener(e -> doLogin.run());
    }
}
