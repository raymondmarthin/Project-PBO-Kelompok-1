package controllers;

import dao.TariffDAO;
import dao.VehicleDAO;

import javax.swing.table.DefaultTableModel;
import javax.swing.*;

public class VehicleController {

    
    public static void insertMasuk(String plate, String type, int staffId) {
        if (plate.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Plat nomor tidak boleh kosong!");
            return;
        }
        boolean success = VehicleDAO.insertMasuk(plate, type, staffId);
        if (success) {
            JOptionPane.showMessageDialog(null, "Kendaraan masuk dicatat.");
        } else {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data.");
        }
    }

    public static void prosesKeluar(String plate, int staffId) {
        TariffDAO tariffDAO = new TariffDAO();
        int tarif = VehicleDAO.processKeluar(plate, staffId, tariffDAO);
        if (tarif >= 0) {
            JOptionPane.showMessageDialog(null, "Total tarif: Rp " + tarif);
        } else {
            JOptionPane.showMessageDialog(null, "Kendaraan tidak ditemukan atau sudah keluar.");
        }
    }

    public static void loadTransactionHistory(int staffId, DefaultTableModel model) {
    dao.VehicleDAO.getTransactionHistory(staffId, model);
}

    
    public static void loadParkedVehicles(DefaultTableModel model) {
        VehicleDAO.getParkedVehicles(model);
    }

}
