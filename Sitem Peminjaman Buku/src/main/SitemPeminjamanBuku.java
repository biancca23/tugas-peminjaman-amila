package main;

import config.Koneksi;
import view.FormMenuUtama;

public class SitemPeminjamanBuku {

    /**
     * Method utama (entry point) untuk menjalankan aplikasi Sistem Peminjaman Buku.
     *
     * @param args argumen baris perintah (tidak digunakan)
     */
    public static void main(String[] args) {
        
        // Test koneksi dulu (opsional)
        Koneksi.getConnection();
        
        // Tampilkan Form Menu Utama
        new FormMenuUtama().setVisible(true);
    }
}