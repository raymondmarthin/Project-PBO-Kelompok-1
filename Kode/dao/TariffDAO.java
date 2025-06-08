package dao;

import config.DatabaseConnection;

import java.sql.*;

public class TariffDAO {
    public int getTariff(String type) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT rate_per_hour FROM tariffs WHERE type = ?");
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("rate_per_hour");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void updateTariff(String type, int rate) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("UPDATE tariffs SET rate_per_hour = ? WHERE type = ?");
            ps.setInt(1, rate);
            ps.setString(2, type);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
