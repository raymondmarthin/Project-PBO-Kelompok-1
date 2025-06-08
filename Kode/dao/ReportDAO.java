package dao;

import config.DatabaseConnection;

import java.sql.*;
import java.time.Duration;

public class ReportDAO {

    public static String getDailyReport(String date) {
        String sql = """
            SELECT COUNT(*) AS jumlah,
                   SUM(fee) AS total,
                   AVG(TIMESTAMPDIFF(MINUTE, checkin_time, checkout_time)) AS rata_durasi
            FROM vehicles
            WHERE DATE(checkout_time) = ?
        """;
        return generateReport(sql, date);
    }

    public static String getMonthlyReport(String month) {
        String sql = """
            SELECT COUNT(*) AS jumlah,
                   SUM(fee) AS total,
                   AVG(TIMESTAMPDIFF(MINUTE, checkin_time, checkout_time)) AS rata_durasi
            FROM vehicles
            WHERE DATE_FORMAT(checkout_time, '%Y-%m') = ?
        """;
        return generateReport(sql, month);
    }

    private static String generateReport(String sql, String param) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, param);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int jumlah = rs.getInt("jumlah");
                int total = rs.getInt("total");
                int rataDurasi = rs.getInt("rata_durasi");

                return """
                    📊 LAPORAN KEUANGAN
                    -----------------------
                    Periode: %s
                    Jumlah kendaraan keluar: %d
                    Total pemasukan: Rp %,d
                    Rata-rata durasi parkir: %d menit
                    """.formatted(param, jumlah, total, rataDurasi);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Gagal mengambil laporan.";
    }
}
