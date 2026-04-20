package dao;

import config.Koneksi;
import model.Buku;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BukuDAO {

    /**
     * Menyimpan data buku baru ke dalam database.
     *
     * @param buku Objek buku yang berisi data untuk disimpan (judul, penulis, kategori, stok)
     */
    public void insert(Buku buku) {
        String sql = "INSERT INTO buku (judul, penulis, kategori, stok) VALUES (?, ?, ?, ?)";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, buku.getJudul());
            ps.setString(2, buku.getPenulis());
            ps.setString(3, buku.getKategori());
            ps.setInt(4, buku.getStok());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error Insert: " + e.getMessage());
        }
    }

    /**
     * Memperbarui data buku yang sudah ada di database berdasarkan ID Buku.
     *
     * @param buku Objek buku yang berisi data terbaru
     */
    public void update(Buku buku) {
        String sql = "UPDATE buku SET judul=?, penulis=?, kategori=?, stok=? WHERE id_buku=?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, buku.getJudul());
            ps.setString(2, buku.getPenulis());
            ps.setString(3, buku.getKategori());
            ps.setInt(4, buku.getStok());
            ps.setInt(5, buku.getIdBuku());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error Update: " + e.getMessage());
        }
    }

    /**
     * Menghapus data buku dari database berdasarkan ID Buku.
     *
     * @param id ID Buku yang akan dihapus
     */
    public void delete(int id) {
        String sql = "DELETE FROM buku WHERE id_buku=?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Data buku berhasil dihapus!");

        } catch (SQLException e) {
            System.out.println("Error Delete: " + e.getMessage());
        }
    }

    /**
     * Mengambil keseluruhan data buku dari database.
     *
     * @return List koleksi dari objek Buku
     */
    public List<Buku> getAll() {
        List<Buku> list = new ArrayList<>();
        String sql = "SELECT * FROM buku";

        try (Connection conn = Koneksi.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Buku buku = new Buku();
                buku.setIdBuku(rs.getInt("id_buku"));
                buku.setJudul(rs.getString("judul"));
                buku.setPenulis(rs.getString("penulis"));
                buku.setKategori(rs.getString("kategori"));
                buku.setStok(rs.getInt("stok"));

                list.add(buku);
            }

        } catch (SQLException e) {
            System.out.println("Error GetAll: " + e.getMessage());
        }

        return list;
    }

    /**
     * Mengambil data semua buku yang memiliki stok lebih dari 0.
     * Fungsi ini dikhususkan untuk list pilihan pada form Peminjaman.
     *
     * @return List objek Buku dengan stok aktif
     */
    public List<Buku> getAvailableBuku() {
        List<Buku> list = new ArrayList<>();
        String sql = "SELECT * FROM buku WHERE stok > 0";

        try (Connection conn = Koneksi.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Buku buku = new Buku();
                buku.setIdBuku(rs.getInt("id_buku"));
                buku.setJudul(rs.getString("judul"));
                buku.setPenulis(rs.getString("penulis"));
                buku.setKategori(rs.getString("kategori"));
                buku.setStok(rs.getInt("stok"));
                list.add(buku);
            }
        } catch (SQLException e) {
            System.err.println("Error GetAvailableBuku: " + e.getMessage());
        }
        return list;
    }

    /**
     * Mencari data buku berdasarkan kata kunci pada judul.
     *
     * @param keyword Kata kunci untuk pencarian judul buku
     * @return List koleksi dari objek Buku yang cocok dengan kata kunci
     */
    public List<Buku> search(String keyword) {
        List<Buku> list = new ArrayList<>();
        String sql = "SELECT * FROM buku WHERE judul LIKE ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Buku buku = new Buku();
                buku.setIdBuku(rs.getInt("id_buku"));
                buku.setJudul(rs.getString("judul"));
                buku.setPenulis(rs.getString("penulis"));
                buku.setKategori(rs.getString("kategori"));
                buku.setStok(rs.getInt("stok"));

                list.add(buku);
            }

        } catch (SQLException e) {
            System.out.println("Error Search: " + e.getMessage());
        }

        return list;
    }
}