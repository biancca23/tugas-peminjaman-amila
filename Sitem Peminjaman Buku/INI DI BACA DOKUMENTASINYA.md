# 📚 Sistem Peminjaman Buku (Perpustakaan App MVC)

Aplikasi Desktop berbasis **Java Swing (GUI)** yang dirancang menggunakan konsep arsitektur berlapis **Clean Architecture (Model-DAO-View)** untuk mempermudah pengelolaan data perpustakaan, mulai dari entri buku baru, manajemen anggota, hingga sirkulasi peminjaman dan pengembalian buku beserta kalkulasi denda otomatis.

---

## 🌟 Fitur Utama

1. **Manajemen Data Buku** (CRUD)
   - Menyimpan judul, penulis, kategori, dan stok buku.
   - Mengubah dan menghapus data buku.

2. **Manajemen Data Anggota** (CRUD)
   - Mendaftarkan nama, kelas, alamat, dan nomor HP anggota.
   - Mengedit data dan menghapus keanggotaan.

3. **Sistem Peminjaman Buku (Sirkulasi)**
   - Proses peminjaman **Multi-Buku**: Satu anggota bisa meminjam lebih dari 1 buku sekaligus dalam satu transaksi terpisah.
   - Otomatisasi tanggal jatuh tempo berdasarkan lama hari pinjam.
   - Pengurangan stok buku secara dinamis & otomatis dari database (-1).

4. **Sistem Pengembalian & Kalkulasi Denda**
   - Mengembalikan buku ke rak (otomatis menambah +1 stok buku).
   - Menghitung **denda keterlambatan** secara akurat dan otomatis saat tombol *Pengembalian* ditekan (Rp 2.000,- / hari dari selisih hari ini dengan tanggal jatuh tempo).

---

## 🏗️ Struktur Arsitektur Kode Utama

Aplikasi ini menggunakan pola pemisahan lapisan logika (*Separation of Concerns*) sehingga aplikasinya fleksibel dan tanpa tercampur-campur *("Spaghetti Code")*:

```text
Sistem Peminjaman Buku/
│
├── bin/                        # Folder build untuk file .class hasil kompilasi (otomatis)
├── lib/                        # Folder dependensi pihak ketiga (mysql-connector-java.jar)
└── src/                        # Kode Sumber Aplikasi (Source Code)
    ├── config/                 
    │   └── Koneksi.java        # → Konfigurasi akses Database MySQL (Factory Pattern)
    │
    ├── model/                  
    │   ├── Anggota.java        # → Blueprint Entitas Tabel Anggota (Atribut, Getter, Setter)
    │   ├── Buku.java           # → Blueprint Entitas Tabel Buku
    │   └── Transaksi.java      # → Blueprint Data Peminjaman (Relasi JOIN Anggota & Buku)
    │
    ├── dao/                    
    │   ├── AnggotaDAO.java     # → Akses logika SQL Khusus Anggota
    │   ├── BukuDAO.java        # → Akses logika SQL Khusus Buku (termasuk filter Stok Tersedia)
    │   └── TransaksiDAO.java   # → Pusat Algoritma SQL (Insert Pinjam, Update Kembali, Penyesuaian Denda)
    │
    ├── view/                   
    │   └── FormMenuUtama.java  # → Antarmuka Visual / GUI Induk Utama Aplikasi (Swing/AWT)
    │
    └── main/                   
        └── SitemPeminjamanBuku.java # → Entry Point Utama Sistem (Class Launcher)
```

---

## 🛠️ Persyaratan Instalasi

*   **Java Development Kit (JDK):** Dianjurkan JDK 8 atau di atasnya.
*   **Database Server:** MySQL (Disarankan menggunakan kontrol panel XAMPP).
*   **Database Driver:** Library MySQL Connector/J `v8.0+` yang diletakkan di dalam folder `lib/`. (Diperlukan jika Anda menjalankan *Compile / Run* lewat terminal lokal).

---

## ⚙️ Persiapan Database & Tabel

1. Buka layanan **MySQL** *(Start MySQL di aplikasi XAMPP)*.
2. Login ke **phpMyAdmin** secara lokal di `http://localhost/phpmyadmin/`.
3. Buatlah antarmuka database baru dengan nama `perpustakaan_db` lalu jalankan _query SQL_ berikut:

```sql
CREATE DATABASE IF NOT EXISTS perpustakaan_db;
USE perpustakaan_db;

-- Tabel Buku
CREATE TABLE buku (
    id_buku INT AUTO_INCREMENT PRIMARY KEY,
    judul VARCHAR(150) NOT NULL,
    penulis VARCHAR(100),
    kategori VARCHAR(50),
    stok INT NOT NULL DEFAULT 0
);

-- Tabel Anggota
CREATE TABLE anggota (
    id_anggota INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(150) NOT NULL,
    kelas VARCHAR(50),
    alamat TEXT,
    no_hp VARCHAR(20)
);

-- Tabel Transaksi
CREATE TABLE transaksi (
    id_transaksi INT AUTO_INCREMENT PRIMARY KEY,
    id_buku INT NOT NULL,
    id_anggota INT NOT NULL,
    tanggal_pinjam DATE,
    tanggal_jatuh_tempo DATE,
    tanggal_kembali DATE NULL,
    denda INT DEFAULT 0,
    status ENUM('Dipinjam', 'Dikembalikan') DEFAULT 'Dipinjam',
    FOREIGN KEY (id_buku) REFERENCES buku(id_buku) ON DELETE CASCADE,
    FOREIGN KEY (id_anggota) REFERENCES anggota(id_anggota) ON DELETE CASCADE
);
```

---

## 🚀 Cara Menjalankan Aplikasi

Jika Anda mencoba mengeksekusi program **melalui Command Prompt/Terminal Console** dari *root directory* proyek:

**1. Kompilasi (Compile Source Code):**
```bash
# Pastikan Anda berada di root folder "Sistem Peminjaman Buku"
javac -d bin -cp "lib/*" src/view/*.java src/dao/*.java src/config/*.java src/model/*.java src/main/*.java
```

**2. Jalankan Program (Run):**
```bash
# Untuk OS Windows
java -cp "bin;lib/*" main.SitemPeminjamanBuku

# Untuk OS macOS / Linux (menggunakan pemisah colon ":")
java -cp "bin:lib/*" main.SitemPeminjamanBuku
```

> **Catatan:** Jika Anda menggunakan **IntelliJ IDEA, Eclipse, atau Netbeans**, biasanya IDE tersebut sudah langsung mendeteksi folder `src/` dan mengautomasi konfigurasi pustaka `lib/`. Cukup cari file `main/SitemPeminjamanBuku.java` lalu *Run / Shift+F10*.
