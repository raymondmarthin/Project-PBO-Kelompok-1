package controllers;

import dao.TariffDAO;
import dao.UserDAO;
import models.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class AdminController {

    public static void loadPetugasData(DefaultTableModel model) {
        model.setRowCount(0);
        List<User> users = UserDAO.getAllPetugas();
        for (User user : users) {
            model.addRow(new Object[]{user.getId(), user.getUsername(), user.getPassword(), user.getRole()});
        }
    }

    public static void addPetugas(JTextField username, JTextField password, JComboBox<String> role, DefaultTableModel model) {
        User user = new User();
        user.setUsername(username.getText());
        user.setPassword(password.getText());
        user.setRole((String) role.getSelectedItem());

        if (UserDAO.insert(user)) {
            loadPetugasData(model);
            JOptionPane.showMessageDialog(null, "Petugas berhasil ditambahkan");
        }
    }

    public static boolean editPetugas(JTable table, JTextField usernameField, JTextField passwordField,
            JComboBox<String> roleComboBox, DefaultTableModel model) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Tidak ada petugas yang dipilih.");
            return false;
        }

        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String role = (String) roleComboBox.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username dan password tidak boleh kosong.");
            return false;
        }

        int id = Integer.parseInt(model.getValueAt(row, 0).toString());

        //Cek apakah nilai benar-benar berubah
        String oldUsername = model.getValueAt(row, 1).toString();
        String oldPassword = model.getValueAt(row, 2).toString();
        String oldRole = model.getValueAt(row, 3).toString();

        if (username.equals(oldUsername) && password.equals(oldPassword) && role.equals(oldRole)) {
            JOptionPane.showMessageDialog(null, "Tidak ada perubahan data.");
            return false;
        }

        // TODO: Lakukan update ke database
        model.setValueAt(username, row, 1);
        model.setValueAt(password, row, 2);
        model.setValueAt(role, row, 3);

        JOptionPane.showMessageDialog(null, "Data petugas berhasil diperbarui.");
        return true;
    }



    public static void deletePetugas(JTable table, DefaultTableModel model) {
        int selected = table.getSelectedRow();
        if (selected >= 0) {
            int id = (int) model.getValueAt(selected, 0);
            if (UserDAO.delete(id)) {
                loadPetugasData(model);
                JOptionPane.showMessageDialog(null, "Petugas berhasil dihapus");
            }
        }
    }

    public static void loadTarif(JTextField motorField, JTextField mobilField) {
        TariffDAO dao = new TariffDAO();
        motorField.setText(String.valueOf(dao.getTariff("motor")));
        mobilField.setText(String.valueOf(dao.getTariff("mobil")));
    }

    public static boolean updateTarif(JTextField motorField, JTextField mobilField) {
        TariffDAO dao = new TariffDAO();
        try {
            int newMotorTarif = Integer.parseInt(motorField.getText().trim());
            int newMobilTarif = Integer.parseInt(mobilField.getText().trim());

            int oldMotorTarif = dao.getTariff("motor");
            int oldMobilTarif = dao.getTariff("mobil");

            if (newMotorTarif == oldMotorTarif && newMobilTarif == oldMobilTarif) {
                return false; // Tidak ada perubahan
            }

            dao.updateTariff("motor", newMotorTarif);
            dao.updateTariff("mobil", newMobilTarif);
            JOptionPane.showMessageDialog(null, "Tarif berhasil diperbarui");
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Tarif harus berupa angka");
            return false;
        }
    }

}
