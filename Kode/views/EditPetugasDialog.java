package views;

import controllers.AdminController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class EditPetugasDialog extends JDialog {
    private final JTextField usernameField;
    private final JTextField passwordField;
    private final JComboBox<String> roleComboBox;

    public EditPetugasDialog(JFrame parent, JTable petugasTable, DefaultTableModel tableModel) {
        super(parent, "Edit Petugas", true);
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

        int selectedRow = petugasTable.getSelectedRow();
        if (selectedRow >= 0) {
            usernameField.setText((String) tableModel.getValueAt(selectedRow, 1));
            passwordField.setText((String) tableModel.getValueAt(selectedRow, 2));
            roleComboBox.setSelectedItem(tableModel.getValueAt(selectedRow, 3));
        }

        JButton saveBtn = new JButton("Simpan Perubahan");
        saveBtn.setBackground(new Color(23, 162, 184));
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
            boolean success = AdminController.editPetugas(
                    petugasTable, usernameField, passwordField, roleComboBox, tableModel
            );
            if (success) {
                dispose(); // hanya tutup jika berhasil
            }
        });

    }
}
