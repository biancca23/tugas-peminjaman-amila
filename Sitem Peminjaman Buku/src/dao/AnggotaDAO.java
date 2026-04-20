package dao;

import config.Koneksi;
import model.Anggota;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handle seluruh operasi database (SQL) terisolasi khusus tabel Anggota.
 */
public class AnggotaDAO {

    /**
     * Menyimpan data anggota baru ke dalam database.
     */
    public void insert(Anggota anggota) {
        String sql = "INSERT INTO anggota (nama, kelas, alamat, no_hp) VALUES (?, ?, ?, ?)";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, anggota.getNama());
            ps.setString(2, anggota.getKelas());
            ps.setString(3, anggota.getAlamat());
            ps.setString(4, anggota.getNoHp());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error Insert Anggota: " + e.getMessage());
        }
    }

    /**
     * Memperbarui data anggota yang sudah ada di database.
     */
    public void update(Anggota anggota) {
        String sql = "UPDATE anggota SET nama=?, kelas=?, alamat=?, no_hp=? WHERE id_anggota=?";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, anggota.getNama());
            ps.setString(2, anggota.getKelas());
            ps.setString(3, anggota.getAlamat());
            ps.setString(4, anggota.getNoHp());
            ps.setInt(5, anggota.getIdAnggota());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error Update Anggota: " + e.getMessage());
        }
    }

    /**
     * Menghapus data anggota berdasarkan ID.
     */
    public void delete(int id) {
        String sql = "DELETE FROM anggota WHERE id_anggota=?";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error Delete Anggota: " + e.getMessage());
        }
    }

    /**
     * Mengambil keseluruhan data anggota dari database.
     * Mengembalikan list objek Anggota untuk memisahkan view dari ResultSet.
     * @return List objek Anggota
     */
    public List<Anggota> getAll() {
        List<Anggota> list = new ArrayList<>();
        String sql = "SELECT * FROM anggota";
        try (Connection conn = Koneksi.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Anggota a = new Anggota();
                a.setIdAnggota(rs.getInt("id_anggota"));
                a.setNama(rs.getString("nama"));
                a.setKelas(rs.getString("kelas"));
                a.setAlamat(rs.getString("alamat"));
                a.setNoHp(rs.getString("no_hp"));
                list.add(a);
            }
        } catch (SQLException e) {
            System.err.println("Error GetAll Anggota: " + e.getMessage());
        }
        return list;
    }
}
