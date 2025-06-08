import config.DatabaseConnection;
import java.sql.Connection;

public class MainTest {
    public static void main(String[] args) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            System.out.println("✅ Koneksi ke database berhasil!");
        } catch (Exception e) {
            System.out.println("❌ Gagal koneksi: " + e.getMessage());
        }
    }
}
