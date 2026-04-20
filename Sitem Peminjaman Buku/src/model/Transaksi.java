package model;

/**
 * Representasi tabel Transaksi pada database.
 * Juga menampung judul buku dan nama anggota hasil dari JOIN Query di DAO.
 */
public class Transaksi {
    private int idTransaksi;
    private int idBuku;
    private int idAnggota;
    private String tanggalPinjam;
    private String tanggalJatuhTempo;
    private String tanggalKembali;
    private int denda;
    private String status;
    
    // Properti tambahan (opsional) untuk mempermudah load tabel dengan LEFT JOIN
    private String judulBuku;
    private String namaAnggota;

    /**
     * Constructor kosong.
     */
    public Transaksi() {
    }

    /**
     * Constructor dengan parameter dasar.
     */
    public Transaksi(int idTransaksi, int idBuku, int idAnggota, String tanggalPinjam, 
                     String tanggalJatuhTempo, String tanggalKembali, int denda, String status) {
        this.idTransaksi = idTransaksi;
        this.idBuku = idBuku;
        this.idAnggota = idAnggota;
        this.tanggalPinjam = tanggalPinjam;
        this.tanggalJatuhTempo = tanggalJatuhTempo;
        this.tanggalKembali = tanggalKembali;
        this.denda = denda;
        this.status = status;
    }

    // Getter & Setter Data Utama
    public int getIdTransaksi() { return idTransaksi; }
    public void setIdTransaksi(int idTransaksi) { this.idTransaksi = idTransaksi; }

    public int getIdBuku() { return idBuku; }
    public void setIdBuku(int idBuku) { this.idBuku = idBuku; }

    public int getIdAnggota() { return idAnggota; }
    public void setIdAnggota(int idAnggota) { this.idAnggota = idAnggota; }

    public String getTanggalPinjam() { return tanggalPinjam; }
    public void setTanggalPinjam(String tanggalPinjam) { this.tanggalPinjam = tanggalPinjam; }

    public String getTanggalJatuhTempo() { return tanggalJatuhTempo; }
    public void setTanggalJatuhTempo(String tanggalJatuhTempo) { this.tanggalJatuhTempo = tanggalJatuhTempo; }

    public String getTanggalKembali() { return tanggalKembali; }
    public void setTanggalKembali(String tanggalKembali) { this.tanggalKembali = tanggalKembali; }

    public int getDenda() { return denda; }
    public void setDenda(int denda) { this.denda = denda; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // Getter & Setter Data Tambahan View
    public String getJudulBuku() { return judulBuku; }
    public void setJudulBuku(String judulBuku) { this.judulBuku = judulBuku; }

    public String getNamaAnggota() { return namaAnggota; }
    public void setNamaAnggota(String namaAnggota) { this.namaAnggota = namaAnggota; }
}
