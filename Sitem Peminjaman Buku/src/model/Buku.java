package model;

public class Buku {

    private int idBuku;
    private String judul;
    private String penulis;
    private String kategori;
    private int stok;

    /**
     * Constructor kosong untuk membuat objek Buku tanpa nilai awal.
     */
    public Buku() {
    }

    /**
     * Constructor dengan parameter untuk membuat objek Buku dengan kelengkapan atribut.
     *
     * @param idBuku   ID unik buku di database
     * @param judul    Judul buku
     * @param penulis  Nama pengarang buku
     * @param kategori Kategori atau genre buku
     * @param stok     Jumlah inventaris stok buku saat ini
     */
    public Buku(int idBuku, String judul, String penulis, String kategori, int stok) {
        this.idBuku = idBuku;
        this.judul = judul;
        this.penulis = penulis;
        this.kategori = kategori;
        this.stok = stok;
    }

    // Getter & Setter

    /**
     * Mendapatkan ID Buku.
     * @return ID Buku
     */
    public int getIdBuku() {
        return idBuku;
    }

    /**
     * Mengatur ID Buku baru.
     * @param idBuku ID Buku
     */
    public void setIdBuku(int idBuku) {
        this.idBuku = idBuku;
    }

    /**
     * Mendapatkan judul Buku.
     * @return Judul Buku
     */
    public String getJudul() {
        return judul;
    }

    /**
     * Mengatur judul Buku baru.
     * @param judul Judul Buku
     */
    public void setJudul(String judul) {
        this.judul = judul;
    }

    /**
     * Mendapatkan nama penulis Buku.
     * @return Penulis Buku
     */
    public String getPenulis() {
        return penulis;
    }

    /**
     * Mengatur penulis Buku baru.
     * @param penulis Penulis Buku
     */
    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    /**
     * Mendapatkan kategori atau genre Buku.
     * @return Kategori Buku
     */
    public String getKategori() {
        return kategori;
    }

    /**
     * Mengatur kategori Buku baru.
     * @param kategori Kategori Buku
     */
    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    /**
     * Mendapatkan stok buku saat ini.
     * @return Stok inventaris buku
     */
    public int getStok() {
        return stok;
    }

    /**
     * Mengatur jumlah stok buku.
     * @param stok Jumlah stok
     */
    public void setStok(int stok) {
        this.stok = stok;
    }
}