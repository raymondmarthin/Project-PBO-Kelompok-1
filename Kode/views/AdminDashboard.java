package views;

import controllers.AdminController;
import controllers.ReportController;
import models.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.text.SimpleDateFormat;

public class AdminDashboard extends JFrame {
    private final User admin;
    private JTable petugasTable;
    private JTextField usernameField, passwordField;
    private JComboBox<String> roleComboBox;
    private DefaultTableModel tableModel;
    private JTextField motorTarifField, mobilTarifField;

    public AdminDashboard(User admin) {
        this.admin = admin;
        setTitle("Dashboard Admin – " + admin.getUsername());
        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, 920, 620, 20, 20));
        setSize(920, 620);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 245));

        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(Color.WHITE);

        JLabel title = new JLabel("\uD83D\uDC6E Dashboard Admin", SwingConstants.LEFT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(25, 118, 210));
        title.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 0));


        titleBar.add(title, BorderLayout.WEST);
        add(titleBar, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabs.add("Petugas", createPetugasPanel());
        tabs.add("Tarif", createTarifPanel());
        tabs.add("Laporan", createReportPanel());
        tabs.setBackground(Color.WHITE);
        tabs.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        logoutBtn.setBackground(new Color(244, 67, 54));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        logoutBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoutBtn.setPreferredSize(new Dimension(100, 35));
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginForm().setVisible(true);
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(new Color(245, 245, 245));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
        bottomPanel.add(logoutBtn);

        add(tabs, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setupWindowDragging();
    }

    private JPanel createPetugasPanel() {
        JPanel panel = newCardPanel();
        panel.setLayout(new BorderLayout(15, 15));

        tableModel = new DefaultTableModel(new String[]{"ID", "Username", "Password", "Role"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        petugasTable = new JTable(tableModel);
        petugasTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        petugasTable.setRowHeight(24);
        petugasTable.setFillsViewportHeight(true);
        AdminController.loadPetugasData(tableModel);
        
        petugasTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = petugasTable.rowAtPoint(e.getPoint());
                if (row == -1) {
                    petugasTable.clearSelection(); // Kosongkan seleksi jika klik di luar baris
                }
            }
        });

        panel.add(new JScrollPane(petugasTable), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        btnPanel.setBackground(Color.WHITE);

        JButton addBtn = styledButton("Tambah", new Color(40, 167, 69));
        JButton editBtn = styledButton("Edit", new Color(23, 162, 184));
        JButton delBtn = styledButton("Hapus", new Color(220, 53, 69));

        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(delBtn);

        addBtn.addActionListener(e -> {
            AddPetugasDialog dialog = new AddPetugasDialog(this, tableModel);
            dialog.setVisible(true);
        });

        editBtn.addActionListener(e -> {
            int selectedRow = petugasTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Pilih petugas yang ingin diedit terlebih dahulu!");
                return;
            }
            EditPetugasDialog dialog = new EditPetugasDialog(this, petugasTable, tableModel);
            dialog.setVisible(true);
        });

        delBtn.addActionListener(e -> {
            int selectedRow = petugasTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(
                        this,
                        "Silakan pilih petugas yang ingin dihapus terlebih dahulu!",
                        "Tidak ada petugas dipilih",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            String username = petugasTable.getValueAt(selectedRow, 1).toString(); // kolom ke-1 = username
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Yakin ingin menghapus petugas dengan username: " + username + "?",
                    "Konfirmasi Penghapusan",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                AdminController.deletePetugas(petugasTable, tableModel);
            }
        });


        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }


    private JPanel createTarifPanel() {
        JPanel panel = newCardPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = formConstraints();
        motorTarifField = styledField(8);
        mobilTarifField = styledField(8);
        JButton saveBtn = styledButton("Simpan Tarif", new Color(0, 123, 255));
        AdminController.loadTarif(motorTarifField, mobilTarifField);

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Tarif Motor (per jam):"), gbc);
        gbc.gridx = 1; panel.add(motorTarifField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Tarif Mobil (per jam):"), gbc);
        gbc.gridx = 1; panel.add(mobilTarifField, gbc);
        gbc.gridx = 1; gbc.gridy = 2; panel.add(saveBtn, gbc);
        
        saveBtn.addActionListener(e -> {
            boolean updated = AdminController.updateTarif(motorTarifField, mobilTarifField);
            if (!updated) {
                JOptionPane.showMessageDialog(this, "Tidak ada perubahan tarif yang disimpan.");
            }
        });


        return panel;
    }

    private JPanel createReportPanel() {
        JPanel panel = newCardPanel();
        panel.setLayout(new BorderLayout(15, 15));
        JPanel inp = new JPanel(new GridBagLayout());
        inp.setBackground(Color.WHITE);
        GridBagConstraints gbc = formConstraints();

        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        ((JTextField) dateChooser.getDateEditor().getUiComponent()).setEditable(false);
        JDateChooser monthChooser = new JDateChooser();
        monthChooser.setDateFormatString("yyyy-MM");
        ((JTextField) monthChooser.getDateEditor().getUiComponent()).setEditable(false);

        JButton harianBtn = styledButton("Laporan Harian", new Color(23, 162, 184));
        JButton bulananBtn = styledButton("Laporan Bulanan", new Color(23, 162, 184));

        JTextArea reportArea = new JTextArea();
        reportArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        reportArea.setEditable(false);
        JScrollPane sp = new JScrollPane(reportArea);

        gbc.gridx = 0; gbc.gridy = 0; inp.add(dateChooser, gbc);
        gbc.gridx = 1; inp.add(harianBtn, gbc);
        gbc.gridy = 1; gbc.gridx = 0; inp.add(monthChooser, gbc);
        gbc.gridx = 1; inp.add(bulananBtn, gbc);

        harianBtn.addActionListener(e -> {
            if (dateChooser.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Pilih tanggal dulu!");
                return;
            }
            reportArea.setText(ReportController.getDailyReport(
                    new SimpleDateFormat("yyyy-MM-dd").format(dateChooser.getDate())
            ));
        });
        bulananBtn.addActionListener(e -> {
            if (monthChooser.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Pilih bulan dulu!");
                return;
            }
            reportArea.setText(ReportController.getMonthlyReport(
                    new SimpleDateFormat("yyyy-MM").format(monthChooser.getDate())
            ));
        });

        panel.add(inp, BorderLayout.NORTH);
        panel.add(sp, BorderLayout.CENTER);
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
        return styledField(15);
    }

    private JTextField styledField(int cols) {
        JTextField f = new JTextField(cols);
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

    private void setupWindowDragging() {
        final Point[] mouseDownCompCoords = {null};
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseDownCompCoords[0] = e.getPoint();
            }
            public void mouseReleased(MouseEvent e) {
                mouseDownCompCoords[0] = null;
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point currCoords = e.getLocationOnScreen();
                setLocation(currCoords.x - mouseDownCompCoords[0].x, currCoords.y - mouseDownCompCoords[0].y);
            }
        });
    }
}
