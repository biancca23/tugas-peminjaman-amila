package dao;

import config.Koneksi;
import model.Transaksi;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handle seluruh operasi database (SQL) terisolasi khusus tabel Transaksi.
 */
public class TransaksiDAO {

    /**
     * Memasukkan data transaksi baru (peminjaman) sekaligus memperbarui stok buku (-1).
     * @param transaksi Objek transaksi yang berisi id_buku, id_anggota, dan tanggal
     */
    public void insert(Transaksi transaksi) {
        String sqlTransaksi = "INSERT INTO transaksi (id_buku, id_anggota, tanggal_pinjam, tanggal_jatuh_tempo, status) VALUES (?, ?, ?, ?, 'Dipinjam')";
        String sqlUpdateStok = "UPDATE buku SET stok = stok - 1 WHERE id_buku = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement psTx = conn.prepareStatement(sqlTransaksi);
             PreparedStatement psStok = conn.prepareStatement(sqlUpdateStok)) {
            
            // Insert transaksi
            psTx.setInt(1, transaksi.getIdBuku());
            psTx.setInt(2, transaksi.getIdAnggota());
            psTx.setString(3, transaksi.getTanggalPinjam());
            psTx.setString(4, transaksi.getTanggalJatuhTempo());
            psTx.executeUpdate();

            // Automatis kurangi stok
            psStok.setInt(1, transaksi.getIdBuku());
            psStok.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error Insert Transaksi: " + e.getMessage());
        }
    }

    /**
     * Melakukan pengembalian buku (Update denda & tanggal_kembali) 
     * dan secara otomatis mengembalikan stok buku (+1).
     */
    public void kembalikan(int idTransaksi, int idBuku, String tglKembali, int denda) {
        String sqlKembali = "UPDATE transaksi SET tanggal_kembali=?, denda=?, status='Dikembalikan' WHERE id_transaksi=?";
        String sqlUpdateStok = "UPDATE buku SET stok = stok + 1 WHERE id_buku=?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement psKembali = conn.prepareStatement(sqlKembali);
             PreparedStatement psStok = conn.prepareStatement(sqlUpdateStok)) {
            
            // Update transaksi
            psKembali.setString(1, tglKembali);
            psKembali.setInt(2, denda);
            psKembali.setInt(3, idTransaksi);
            psKembali.executeUpdate();

            // Automatis tambah stok
            psStok.setInt(1, idBuku);
            psStok.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error Kembalikan Transaksi: " + e.getMessage());
        }
    }

    /**
     * Mengambil keseluruhan data transaksi, digabungkan (JOIN) 
     * dengan nama buku dan nama anggota agar siap ditampilkan di tabel View.
     * @return List objek Transaksi
     */
    public List<Transaksi> getAll() {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT t.id_transaksi, t.id_buku, b.judul, t.id_anggota, a.nama, " +
                     "t.tanggal_pinjam, t.tanggal_jatuh_tempo, t.tanggal_kembali, t.denda, t.status " +
                     "FROM transaksi t " +
                     "LEFT JOIN buku b ON t.id_buku = b.id_buku " +
                     "LEFT JOIN anggota a ON t.id_anggota = a.id_anggota " +
                     "ORDER BY t.id_transaksi DESC";

        try (Connection conn = Koneksi.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                Transaksi t = new Transaksi();
                t.setIdTransaksi(rs.getInt("id_transaksi"));
                t.setIdBuku(rs.getInt("id_buku"));
                t.setIdAnggota(rs.getInt("id_anggota"));
                t.setJudulBuku(rs.getString("judul"));
                t.setNamaAnggota(rs.getString("nama"));
                t.setTanggalPinjam(rs.getString("tanggal_pinjam"));
                t.setTanggalJatuhTempo(rs.getString("tanggal_jatuh_tempo"));
                t.setTanggalKembali(rs.getString("tanggal_kembali"));
                t.setDenda(rs.getInt("denda"));
                t.setStatus(rs.getString("status"));
                list.add(t);
            }
        } catch (SQLException e) {
            System.err.println("Error GetAll Transaksi: " + e.getMessage());
        }
        return list;
    }
}
