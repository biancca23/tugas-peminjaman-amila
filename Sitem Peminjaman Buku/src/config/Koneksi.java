package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {

    /**
     * Membuat dan mengembalikan koneksi baru ke database MySQL setiap kali dipanggil.
     * Pendekatan ini wajib digunakan jika DAO memanfaatkan try-with-resources.
     *
     * @return Objek Connection yang terhubung ke database perpustakaan_db
     */
    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/perpustakaan_db";
            String user = "root";
            String password = "";

            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Koneksi Gagal: " + e.getMessage());
            return null;
        }
    }
}