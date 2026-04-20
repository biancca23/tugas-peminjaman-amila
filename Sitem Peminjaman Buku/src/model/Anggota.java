package model;

/**
 * Representasi tabel Anggota pada database.
 * Bertanggung jawab hanya untuk menyimpan data anggota (Clean Architecture).
 */
public class Anggota {
    private int idAnggota;
    private String nama;
    private String kelas;
    private String alamat;
    private String noHp;

    /**
     * Constructor kosong.
     */
    public Anggota() {
    }

    /**
     * Constructor dengan parameter.
     */
    public Anggota(int idAnggota, String nama, String kelas, String alamat, String noHp) {
        this.idAnggota = idAnggota;
        this.nama = nama;
        this.kelas = kelas;
        this.alamat = alamat;
        this.noHp = noHp;
    }

    // Getter & Setter
    public int getIdAnggota() { return idAnggota; }
    public void setIdAnggota(int idAnggota) { this.idAnggota = idAnggota; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getKelas() { return kelas; }
    public void setKelas(String kelas) { this.kelas = kelas; }

    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }

    public String getNoHp() { return noHp; }
    public void setNoHp(String noHp) { this.noHp = noHp; }
}
