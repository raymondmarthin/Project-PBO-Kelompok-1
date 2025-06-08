package views;

import controllers.AdminController;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class AddPetugasDialog extends JDialog {
    private final JTextField usernameField;
    private final JTextField passwordField;
    private final JComboBox<String> roleComboBox;

    public AddPetugasDialog(JFrame parent, DefaultTableModel tableModel) {
        super(parent, "Tambah Petugas", true);
        setSize(350, 250);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 12, 10, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        usernameField = new JTextField(15);
        passwordField = new JTextField(15);
        roleComboBox = new JComboBox<>(new String[]{"petugas"});

        JButton saveBtn = new JButton("Simpan");
        saveBtn.setBackground(new Color(40, 167, 69));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);
        saveBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Layout form
        gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; add(usernameField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; add(passwordField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Role:"), gbc);
        gbc.gridx = 1; add(roleComboBox, gbc);
        gbc.gridx = 1; gbc.gridy = 3; add(saveBtn, gbc);

        saveBtn.addActionListener(e -> {
            if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username dan password tidak boleh kosong!");
                return;
            }
            AdminController.addPetugas(usernameField, passwordField, roleComboBox, tableModel);
            dispose();
        });
    }
}
