package dao;

import config.DatabaseConnection;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.Duration;

public class VehicleDAO {

    public static boolean insertMasuk(String plate, String type, int staffId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO vehicles (plate_number, type, checkin_time, handled_by) VALUES (?, ?, NOW(), ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, plate);
            ps.setString(2, type);
            ps.setInt(3, staffId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int processKeluar(String plate, int staffId, TariffDAO tarifDao) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String select = "SELECT id, type, checkin_time FROM vehicles WHERE plate_number = ? AND checkout_time IS NULL";
            PreparedStatement ps = conn.prepareStatement(select);
            ps.setString(1, plate);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String type = rs.getString("type");
                Timestamp checkin = rs.getTimestamp("checkin_time");

                long minutes = Duration.between(checkin.toLocalDateTime(), LocalDateTime.now()).toMinutes();
                int tarifPerJam = tarifDao.getTariff(type);
                int fee = (int) Math.ceil(minutes / 60.0) * tarifPerJam;

                String update = "UPDATE vehicles SET checkout_time = NOW(), fee = ?, handled_by = ? WHERE id = ?";
                PreparedStatement ups = conn.prepareStatement(update);
                ups.setInt(1, fee);
                ups.setInt(2, staffId);
                ups.setInt(3, id);
                ups.executeUpdate();

                return fee;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void getParkedVehicles(DefaultTableModel model) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT plate_number, type, checkin_time FROM vehicles WHERE checkout_time IS NULL ORDER BY checkin_time DESC";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("plate_number"),
                    rs.getString("type"),
                    rs.getTimestamp("checkin_time")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getTransactionHistory(int staffId, DefaultTableModel model) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = """
                SELECT plate_number, type, checkin_time, checkout_time, fee 
                FROM vehicles 
                WHERE handled_by = ? AND checkout_time IS NOT NULL 
                ORDER BY checkout_time DESC
            """;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, staffId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("plate_number"),
                    rs.getString("type"),
                    rs.getTimestamp("checkin_time"),
                    rs.getTimestamp("checkout_time"),
                    rs.getInt("fee")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
