package views;

import controllers.VehicleController;
import models.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StaffDashboard extends JFrame {
    private final User staff;
    private JTextField plateFieldIn, plateFieldOut;
    private JComboBox<String> typeComboBox;
    private JTable parkedTable;

    public StaffDashboard(User staff) {
        this.staff = staff;
        setTitle("Dashboard Petugas - " + staff.getUsername());
        setSize(850, 600);
        setLocationRelativeTo(null);
        setUndecorated(true);
        getContentPane().setBackground(new Color(245, 245, 245));

        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(Color.WHITE);

        JLabel title = new JLabel("\uD83D\uDEE0 Dashboard Petugas", SwingConstants.LEFT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(25, 118, 210));
        title.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 0));

        titleBar.add(title, BorderLayout.WEST);
        add(titleBar, BorderLayout.NORTH);

        Font defaultFont = new Font("Segoe UI", Font.PLAIN, 14);
        UIManager.put("Label.font", defaultFont);
        UIManager.put("Button.font", defaultFont);
        UIManager.put("TextField.font", defaultFont);
        UIManager.put("ComboBox.font", defaultFont);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(defaultFont);
        tabs.addTab("Kendaraan Masuk", createMasukPanel());
        tabs.addTab("Kendaraan Keluar", createKeluarPanel());
        tabs.addTab("Daftar Parkir", createDaftarPanel());
        tabs.addTab("Riwayat Transaksi", createHistoryPanel());
        tabs.setBackground(Color.WHITE);
        tabs.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(new Color(220, 53, 69));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        logoutBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoutBtn.setPreferredSize(new Dimension(100, 35));
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginForm().setVisible(true);
        });

        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.setBackground(new Color(245, 245, 245));
        logoutPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        logoutPanel.add(logoutBtn);

        add(tabs, BorderLayout.CENTER);
        add(logoutPanel, BorderLayout.SOUTH);
    }

    private JPanel createMasukPanel() {
        JPanel panel = newCardPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = formConstraints();
        plateFieldIn = styledField();
        typeComboBox = new JComboBox<>(new String[]{"motor", "mobil"});
        typeComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JButton masukBtn = styledButton("Simpan Masuk", new Color(59, 130, 246));

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Plat Nomor:"), gbc);
        gbc.gridx = 1; panel.add(plateFieldIn, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Jenis Kendaraan:"), gbc);
        gbc.gridx = 1; panel.add(typeComboBox, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; panel.add(masukBtn, gbc);

        masukBtn.addActionListener(e -> {
            String plate = plateFieldIn.getText().trim();
            String type = (String) typeComboBox.getSelectedItem();
            if (plate.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Plat nomor tidak boleh kosong.");
                return;
            }
            VehicleController.insertMasuk(plate, type, staff.getId());
            loadParkedTable();
            plateFieldIn.setText("");
        });

        return panel;
    }

    private JPanel createKeluarPanel() {
        JPanel panel = newCardPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = formConstraints();
        plateFieldOut = styledField();

        JButton keluarBtn = styledButton("Proses Keluar", new Color(59, 130, 246));

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Plat Nomor:"), gbc);
        gbc.gridx = 1; panel.add(plateFieldOut, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2; panel.add(keluarBtn, gbc);

        keluarBtn.addActionListener(e -> {
            String plate = plateFieldOut.getText().trim();
            if (plate.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Plat nomor tidak boleh kosong.");
                return;
            }
            VehicleController.prosesKeluar(plate, staff.getId());
            loadParkedTable();
            plateFieldOut.setText("");
        });

        return panel;
    }

    private JPanel createDaftarPanel() {
        JPanel panel = newCardPanel();
        panel.setLayout(new BorderLayout(10, 10));

        String[] columns = {"Plat", "Jenis", "Waktu Masuk"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        parkedTable = new JTable(model);
        parkedTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        parkedTable.setRowHeight(24);
        parkedTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        parkedTable.getTableHeader().setBackground(new Color(230, 230, 230));

        JScrollPane scrollPane = new JScrollPane(parkedTable);
        scrollPane.getViewport().setBackground(Color.WHITE);

        panel.add(scrollPane, BorderLayout.CENTER);

        loadParkedTable();
      
        return panel;
    }

    private JPanel createHistoryPanel() {
        JPanel panel = newCardPanel();
        panel.setLayout(new BorderLayout(10, 10));

        String[] columns = {"Plat Nomor", "Jenis", "Masuk", "Keluar", "Tarif"};
        DefaultTableModel historyModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable historyTable = new JTable(historyModel);
        historyTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        historyTable.setRowHeight(24);
        historyTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        historyTable.getTableHeader().setBackground(new Color(230, 230, 230));


        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.getViewport().setBackground(Color.WHITE);

        JButton refreshBtn = styledButton("Refresh", new Color(25, 135, 84));
        refreshBtn.addActionListener(e -> {
            historyModel.setRowCount(0);
            VehicleController.loadTransactionHistory(staff.getId(), historyModel);
        });

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setBackground(Color.WHITE);
        topPanel.add(refreshBtn);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        VehicleController.loadTransactionHistory(staff.getId(), historyModel);
        return panel;
    }

    private JPanel newCardPanel() {
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true));
        return p;
    }

    private GridBagConstraints formConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    private JTextField styledField() {
        JTextField f = new JTextField(15);
        f.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));
        return f;
    }

    private JButton styledButton(String text, Color bg) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(140, 32));
        return b;
    }

    private void loadParkedTable() {
        DefaultTableModel model = (DefaultTableModel) parkedTable.getModel();
        model.setRowCount(0);
        VehicleController.loadParkedVehicles(model);
    }
}
