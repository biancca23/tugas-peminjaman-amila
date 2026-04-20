package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

import dao.BukuDAO;
import model.Buku;
import dao.AnggotaDAO;
import model.Anggota;
import dao.TransaksiDAO;
import model.Transaksi;

public class FormMenuUtama extends JFrame {

    // Colors
    private final Color SIDEBAR_BG = Color.decode("#2c3e50");
    private final Color SIDEBAR_FG = Color.white;
    private final Color BTN_SIMPAN_BG = Color.decode("#27ae60");
    private final Color BTN_UPDATE_BG = Color.decode("#2980b9");
    private final Color BTN_DELETE_BG = Color.decode("#c0392b");
    private final Color BTN_RESET_BG = Color.decode("#f39c12");
    private final Color MAIN_BG = Color.white;

    private final Font FONT_REGULAR = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font FONT_HEADER = new Font("Segoe UI", Font.BOLD, 24);

    private JPanel mainContentPanel;
    private CardLayout cardLayout;

    public FormMenuUtama() {
        setTitle("Sistem Peminjaman Buku - Dashboard");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        initComponents();
    }

    /**
     * Menginisialisasi komponen utama Graphical User Interface (GUI), 
     * termasuk pengaturan frame, pengaturan layout CardLayout, dan menu sidebar.
     */
    private void initComponents() {
        // --- 1. SIDEBAR ---
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setBackground(SIDEBAR_BG);
        sidebarPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        sidebarPanel.setPreferredSize(new Dimension(250, 0));
        sidebarPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.weightx = 1.0;

        JLabel lblAppTitle = new JLabel("Perpustakaan");
        lblAppTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblAppTitle.setForeground(SIDEBAR_FG);
        lblAppTitle.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 0;
        sidebarPanel.add(lblAppTitle, gbc);

        gbc.insets = new Insets(20, 0, 5, 0);
        JButton btnMenuBuku = createMenuButton("📖 Kelola Buku");
        gbc.gridy = 1; sidebarPanel.add(btnMenuBuku, gbc);

        gbc.insets = new Insets(5, 0, 5, 0);
        JButton btnMenuAnggota = createMenuButton("👥 Kelola Anggota");
        gbc.gridy = 2; sidebarPanel.add(btnMenuAnggota, gbc);

        JButton btnMenuTransaksi = createMenuButton("🔄 Transaksi");
        gbc.gridy = 3; sidebarPanel.add(btnMenuTransaksi, gbc);

        gbc.gridy = 4; gbc.weighty = 1.0;
        sidebarPanel.add(Box.createVerticalGlue(), gbc);
        gbc.weighty = 0.0;

        JButton btnKeluar = createMenuButton("🚪 Keluar");
        btnKeluar.setForeground(new Color(231, 76, 60)); 
        gbc.gridy = 5; gbc.insets = new Insets(20, 0, 0, 0);
        sidebarPanel.add(btnKeluar, gbc);

        add(sidebarPanel, BorderLayout.WEST);

        // --- 2. MAIN CONTENT AREA (CardLayout) ---
        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);
        mainContentPanel.setBackground(MAIN_BG);

        // Menambahkan panel (card) yang dipanggil ketika menu diklik
        mainContentPanel.add(createPanelWelcome(), "Welcome");
        mainContentPanel.add(createPanelBuku(), "Buku");
        mainContentPanel.add(createPanelAnggota(), "Anggota");
        mainContentPanel.add(createPanelTransaksi(), "Transaksi");

        add(mainContentPanel, BorderLayout.CENTER);

        // --- 3. EVENT LISTENERS ---
        btnMenuBuku.addActionListener(e -> cardLayout.show(mainContentPanel, "Buku"));
        btnMenuAnggota.addActionListener(e -> cardLayout.show(mainContentPanel, "Anggota"));
        btnMenuTransaksi.addActionListener(e -> cardLayout.show(mainContentPanel, "Transaksi"));
        btnKeluar.addActionListener(e -> System.exit(0));

        // Tampilkan halaman welcome pertama kali
        cardLayout.show(mainContentPanel, "Welcome");
    }

    /**
     * Membuat panel ucapan selamat datang yang tampil saat aplikasi pertama kali dibuka.
     * @return JPanel berisi pesan Welcome
     */
    private JPanel createPanelWelcome() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(MAIN_BG);
        JLabel lblWelcome = new JLabel("Selamat Datang di Manajemen Pustaka", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblWelcome.setForeground(SIDEBAR_BG);
        panel.add(lblWelcome, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Membuat panel untuk Kelola Data Buku (CRUD Buku).
     * Panel ini berisi form input buku dan tabel daftar buku beserta event listener-nya.
     * @return JPanel untuk menu Data Buku
     */
    private JPanel createPanelBuku() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(MAIN_BG);
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));

        JLabel lblHeader = new JLabel("Kelola Data Buku");
        lblHeader.setFont(FONT_HEADER);
        lblHeader.setForeground(SIDEBAR_BG);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(MAIN_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.insets = new Insets(5, 5, 5, 5); gbc.weightx = 1.0;

        JTextField txtJudul = createTextField(); JTextField txtPenulis = createTextField();
        JTextField txtKategori = createTextField(); JTextField txtStok = createTextField();

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(createLabel("Judul"), gbc);
        gbc.gridx = 1; formPanel.add(txtJudul, gbc);
        gbc.gridx = 2; formPanel.add(createLabel("Penulis"), gbc);
        gbc.gridx = 3; formPanel.add(txtPenulis, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(createLabel("Kategori"), gbc);
        gbc.gridx = 1; formPanel.add(txtKategori, gbc);
        gbc.gridx = 2; formPanel.add(createLabel("Stok"), gbc);
        gbc.gridx = 3; formPanel.add(txtStok, gbc);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnPanel.setBackground(MAIN_BG);
        JButton btnSimpan = createActionButton("Simpan", BTN_SIMPAN_BG);
        JButton btnUpdate = createActionButton("Update", BTN_UPDATE_BG);
        JButton btnDelete = createActionButton("Delete", BTN_DELETE_BG);
        JButton btnReset = createActionButton("Reset", BTN_RESET_BG);

        btnPanel.add(btnSimpan);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnReset);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 4; gbc.insets = new Insets(15, 5, 5, 5);
        formPanel.add(btnPanel, gbc);

        JPanel northPanel = new JPanel(new BorderLayout(0, 20));
        northPanel.setBackground(MAIN_BG);
        northPanel.add(lblHeader, BorderLayout.NORTH);
        northPanel.add(formPanel, BorderLayout.CENTER);
        
        panel.add(northPanel, BorderLayout.NORTH);

        String[] columns = {"ID", "Judul", "Penulis", "Kategori", "Stok"};
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, columns);
        JTable table = createStyledTable(model);
        loadDataBuku(model);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(223, 228, 234)));
        scrollPane.getViewport().setBackground(MAIN_BG);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Tabel Buku Listener
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                if(row != -1) {
                    txtJudul.setText(model.getValueAt(row, 1).toString());
                    txtPenulis.setText(model.getValueAt(row, 2).toString());
                    txtKategori.setText(model.getValueAt(row, 3).toString());
                    txtStok.setText(model.getValueAt(row, 4).toString());
                }
            }
        });

        // CRUD Actions for Buku
        btnSimpan.addActionListener(e -> {
            try {
                Buku buku = new Buku();
                buku.setJudul(txtJudul.getText());
                buku.setPenulis(txtPenulis.getText());
                buku.setKategori(txtKategori.getText());
                buku.setStok(Integer.parseInt(txtStok.getText()));
                
                new BukuDAO().insert(buku);
                JOptionPane.showMessageDialog(panel, "Data Buku Berhasil Disimpan!");
                loadDataBuku(model);
                btnReset.doClick();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Error Save: " + ex.getMessage());
            }
        });

        btnUpdate.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(panel, "Pilih data buku yang akan diupdate dari tabel!");
                return;
            }
            try {
                Buku buku = new Buku();
                buku.setIdBuku(Integer.parseInt(model.getValueAt(row, 0).toString()));
                buku.setJudul(txtJudul.getText());
                buku.setPenulis(txtPenulis.getText());
                buku.setKategori(txtKategori.getText());
                buku.setStok(Integer.parseInt(txtStok.getText()));

                new BukuDAO().update(buku);
                JOptionPane.showMessageDialog(panel, "Data Buku Berhasil Diupdate!");
                loadDataBuku(model);
                btnReset.doClick();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Error Update: " + ex.getMessage());
            }
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(panel, "Pilih data buku yang akan dihapus dari tabel!");
                return;
            }
            if (JOptionPane.showConfirmDialog(panel, "Yakin hapus buku ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                try {
                    int id = Integer.parseInt(model.getValueAt(row, 0).toString());
                    new BukuDAO().delete(id);
                    JOptionPane.showMessageDialog(panel, "Data Buku Berhasil Dihapus!");
                    loadDataBuku(model);
                    btnReset.doClick();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Error Delete: " + ex.getMessage());
                }
            }
        });

        btnReset.addActionListener(e -> {
            txtJudul.setText("");
            txtPenulis.setText("");
            txtKategori.setText("");
            txtStok.setText("");
            table.clearSelection();
        });

        return panel;
    }

    /**
     * Membuat panel untuk Kelola Data Anggota (CRUD Anggota).
     * Panel ini berisi form input anggota dan tabel daftar anggota beserta event listener-nya.
     * @return JPanel untuk menu Data Anggota
     */
    private JPanel createPanelAnggota() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(MAIN_BG);
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));

        JLabel lblHeader = new JLabel("Kelola Data Anggota");
        lblHeader.setFont(FONT_HEADER);
        lblHeader.setForeground(SIDEBAR_BG);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(MAIN_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.insets = new Insets(5, 5, 5, 5); gbc.weightx = 1.0;

        JTextField txtNama = createTextField(); JTextField txtKelas = createTextField();
        JTextField txtAlamat = createTextField(); JTextField txtNoHp = createTextField();

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(createLabel("Nama"), gbc);
        gbc.gridx = 1; formPanel.add(txtNama, gbc);
        gbc.gridx = 2; formPanel.add(createLabel("Kelas"), gbc);
        gbc.gridx = 3; formPanel.add(txtKelas, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(createLabel("Alamat"), gbc);
        gbc.gridx = 1; formPanel.add(txtAlamat, gbc);
        gbc.gridx = 2; formPanel.add(createLabel("No HP"), gbc);
        gbc.gridx = 3; formPanel.add(txtNoHp, gbc);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnPanel.setBackground(MAIN_BG);
        JButton btnSimpan = createActionButton("Simpan", BTN_SIMPAN_BG);
        JButton btnUpdate = createActionButton("Update", BTN_UPDATE_BG);
        JButton btnDelete = createActionButton("Delete", BTN_DELETE_BG);
        JButton btnReset = createActionButton("Reset", BTN_RESET_BG);

        btnPanel.add(btnSimpan);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnReset);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 4; gbc.insets = new Insets(15, 5, 5, 5);
        formPanel.add(btnPanel, gbc);

        JPanel northPanel = new JPanel(new BorderLayout(0, 20));
        northPanel.setBackground(MAIN_BG);
        northPanel.add(lblHeader, BorderLayout.NORTH);
        northPanel.add(formPanel, BorderLayout.CENTER);
        
        panel.add(northPanel, BorderLayout.NORTH);

        String[] columns = {"ID", "Nama", "Kelas", "Alamat", "No HP"};
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, columns);
        JTable table = createStyledTable(model);
        loadDataAnggota(model);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(223, 228, 234)));
        scrollPane.getViewport().setBackground(MAIN_BG);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Tabel Anggota Listener
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                if(row != -1) {
                    txtNama.setText(model.getValueAt(row, 1).toString());
                    txtKelas.setText(model.getValueAt(row, 2).toString());
                    txtAlamat.setText(model.getValueAt(row, 3).toString());
                    txtNoHp.setText(model.getValueAt(row, 4).toString());
                }
            }
        });

        // CRUD Actions for Anggota
        btnSimpan.addActionListener(e -> {
            try {
                Anggota anggota = new Anggota();
                anggota.setNama(txtNama.getText());
                anggota.setKelas(txtKelas.getText());
                anggota.setAlamat(txtAlamat.getText());
                anggota.setNoHp(txtNoHp.getText());

                new AnggotaDAO().insert(anggota);
                JOptionPane.showMessageDialog(panel, "Data Anggota Berhasil Disimpan!");
                loadDataAnggota(model);
                btnReset.doClick();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Error Save: " + ex.getMessage());
            }
        });

        btnUpdate.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(panel, "Pilih data anggota yang akan diupdate dari tabel!");
                return;
            }
            try {
                Anggota anggota = new Anggota();
                anggota.setIdAnggota(Integer.parseInt(model.getValueAt(row, 0).toString()));
                anggota.setNama(txtNama.getText());
                anggota.setKelas(txtKelas.getText());
                anggota.setAlamat(txtAlamat.getText());
                anggota.setNoHp(txtNoHp.getText());

                new AnggotaDAO().update(anggota);
                JOptionPane.showMessageDialog(panel, "Data Anggota Berhasil Diupdate!");
                loadDataAnggota(model);
                btnReset.doClick();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Error Update: " + ex.getMessage());
            }
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(panel, "Pilih data anggota yang akan dihapus dari tabel!");
                return;
            }
            if (JOptionPane.showConfirmDialog(panel, "Yakin hapus anggota ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                try {
                    int id = Integer.parseInt(model.getValueAt(row, 0).toString());
                    new AnggotaDAO().delete(id);
                    JOptionPane.showMessageDialog(panel, "Data Anggota Berhasil Dihapus!");
                    loadDataAnggota(model);
                    btnReset.doClick();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Error Delete: " + ex.getMessage());
                }
            }
        });

        btnReset.addActionListener(e -> {
            txtNama.setText("");
            txtKelas.setText("");
            txtAlamat.setText("");
            txtNoHp.setText("");
            table.clearSelection();
        });

        return panel;
    }

    /**
     * Membuat panel untuk Transaksi (Peminjaman dan Pengembalian Buku).
     * Panel ini mendukung peminjaman beberapa buku sekaligus dan otomatisasi perhitungan denda.
     * @return JPanel untuk menu Transaksi Peminjaman
     */
    private JPanel createPanelTransaksi() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(MAIN_BG);
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));

        JLabel lblHeader = new JLabel("Transaksi Peminjaman");
        lblHeader.setFont(FONT_HEADER);
        lblHeader.setForeground(SIDEBAR_BG);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(MAIN_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.insets = new Insets(5, 5, 5, 5); gbc.weightx = 1.0;

        // Merubah JComboBox Buku menjadi JList agar bisa pilih lebih dari 1
        DefaultListModel<String> listModelBuku = new DefaultListModel<>();
        JList<String> listBuku = new JList<>(listModelBuku);
        listBuku.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listBuku.setFont(FONT_REGULAR);
        JScrollPane scrollBuku = new JScrollPane(listBuku);
        scrollBuku.setPreferredSize(new Dimension(0, 100)); // Diperbesar tingginya menjadi 100

        JComboBox<String> cbAnggota = createComboBox();
        JTextField txtLamaPinjam = createTextField(); // Berisi inputan angka lama hari peminjaman
        JTextField txtDenda = createTextField(); txtDenda.setEditable(false);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridheight = 1; 
        formPanel.add(createLabel("Buku (Tahan Ctrl >1)"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridheight = 2; // Biarkan ScrollBuku mengambil 2 baris
        formPanel.add(scrollBuku, gbc);
        
        gbc.gridheight = 1; // Kembalikan ke 1 baris untuk elemen lainnya
        gbc.gridx = 2; gbc.gridy = 0; 
        formPanel.add(createLabel("Pilih Anggota"), gbc);
        gbc.gridx = 3; gbc.gridy = 0; 
        formPanel.add(cbAnggota, gbc);
        
        gbc.gridx = 2; gbc.gridy = 1; 
        formPanel.add(createLabel("Lama Pinjam (Hari)"), gbc);
        gbc.gridx = 3; gbc.gridy = 1; 
        formPanel.add(txtLamaPinjam, gbc);

        gbc.gridx = 0; gbc.gridy = 2; 
        formPanel.add(createLabel("Denda"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; 
        formPanel.add(txtDenda, gbc);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnPanel.setBackground(MAIN_BG);
        JButton btnPinjam = createActionButton("Pinjam", BTN_SIMPAN_BG);
        JButton btnKembalikan = createActionButton("Kembalikan", BTN_UPDATE_BG);
        JButton btnReset = createActionButton("Reset", BTN_RESET_BG);

        btnPanel.add(btnPinjam);
        btnPanel.add(btnKembalikan);
        btnPanel.add(btnReset);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 4; gbc.insets = new Insets(15, 5, 5, 5);
        formPanel.add(btnPanel, gbc);

        JPanel northPanel = new JPanel(new BorderLayout(0, 20));
        northPanel.setBackground(MAIN_BG);
        northPanel.add(lblHeader, BorderLayout.NORTH);
        northPanel.add(formPanel, BorderLayout.CENTER);
        
        panel.add(northPanel, BorderLayout.NORTH);

        String[] columns = {"ID", "Buku", "Anggota", "Tgl Pinjam", "Jatuh Tempo", "Tgl Kembali", "Denda", "Status"};
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, columns);
        JTable table = createStyledTable(model);
        loadDataTransaksi(model);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(223, 228, 234)));
        scrollPane.getViewport().setBackground(MAIN_BG);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Load List/ComboBox Data
        loadListBuku(listModelBuku);
        loadComboBoxAnggota(cbAnggota);

        // Tabel Transaksi Listener
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                if(row != -1) {
                    txtDenda.setText(model.getValueAt(row, 6) != null ? model.getValueAt(row, 6).toString() : "0");
                }
            }
        });

        // Bagian Validasi & Logika Transaksi
        btnPinjam.addActionListener(e -> {
            try {
                java.util.List<String> selectedBooks = listBuku.getSelectedValuesList();
                if(selectedBooks.isEmpty() || cbAnggota.getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(panel, "Pilih minimal 1 Buku dan 1 Anggota terlebih dahulu!");
                    return;
                }
                if (txtLamaPinjam.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Harap masukkan Lama Hari Pinjam!");
                    return;
                }
                
                String anggotaSelected = cbAnggota.getSelectedItem().toString();
                int idAnggota = Integer.parseInt(anggotaSelected.split(" - ")[0]);
                int lamaHari = Integer.parseInt(txtLamaPinjam.getText());
                
                java.time.LocalDate hariIni = java.time.LocalDate.now();
                java.time.LocalDate jatuhTempo = hariIni.plusDays(lamaHari);

                TransaksiDAO transaksiDAO = new TransaksiDAO();
                
                // Gunakan loop untuk menyimpan multi-buku
                for(String bukuSelected : selectedBooks) {
                    int idBuku = Integer.parseInt(bukuSelected.split(" - ")[0]);

                    Transaksi trx = new Transaksi();
                    trx.setIdBuku(idBuku);
                    trx.setIdAnggota(idAnggota);
                    trx.setTanggalPinjam(hariIni.toString());
                    trx.setTanggalJatuhTempo(jatuhTempo.toString());

                    transaksiDAO.insert(trx);
                }

                JOptionPane.showMessageDialog(panel, "Transaksi Peminjaman Berhasil (" + selectedBooks.size() + " Buku)!\nJatuh Tempo pada: " + jatuhTempo.toString());
                loadDataTransaksi(model);
                // Refresh list buku karena stok berubah
                loadListBuku(listModelBuku);
                
                btnReset.doClick();
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(panel, "Lama Hari Pinjam harus berupa Angka!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Error Pinjam: " + ex.getMessage());
            }
        });

        btnKembalikan.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(panel, "Pilih data transaksi yang akan dikembalikan dari tabel!");
                return;
            }
            
            String status = model.getValueAt(row, 7).toString();
            if(status.equals("Dikembalikan")) {
                JOptionPane.showMessageDialog(panel, "Buku ini sudah dikembalikan sebelumnya!");
                return;
            }

            try {
                int idTransaksi = Integer.parseInt(model.getValueAt(row, 0).toString());
                String tglJatuhTempoStr = model.getValueAt(row, 4).toString();
                
                // Hitung Denda (Keterlambatan)
                long denda = 0;
                int tarifDendaPerHari = 2000;
                
                java.time.LocalDate today = java.time.LocalDate.now();
                java.time.LocalDate jatuhTempo = java.time.LocalDate.parse(tglJatuhTempoStr);
                
                if (today.isAfter(jatuhTempo)) {
                    long selisihHari = java.time.temporal.ChronoUnit.DAYS.between(jatuhTempo, today);
                    denda = selisihHari * tarifDendaPerHari;
                    JOptionPane.showMessageDialog(panel, "Terlambat " + selisihHari + " hari.\nDenda yang harus dibayar: Rp " + denda, "Informasi Denda", JOptionPane.WARNING_MESSAGE);
                }

                // Get id_buku for this transaction using DAO pattern instead of direct query
                // Actually, our model currently holds it from loadDataTransaksi, 
                // but since JTable only displays Judul, we need to extract idBuku.
                // We stored "ID, Buku, Anggota..."
                // To do this properly without raw SQL, we use getAll to find matching idTransaksi
                int idBuku = -1;
                for (Transaksi t : new TransaksiDAO().getAll()) {
                    if (t.getIdTransaksi() == idTransaksi) {
                        idBuku = t.getIdBuku();
                        break;
                    }
                }

                new TransaksiDAO().kembalikan(idTransaksi, idBuku, today.toString(), (int) denda);

                JOptionPane.showMessageDialog(panel, "Buku Berhasil Dikembalikan!");
                loadDataTransaksi(model);
                loadListBuku(listModelBuku); // update stok di list visual
                btnReset.doClick();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Error Kembalikan: " + ex.getMessage());
            }
        });

        btnReset.addActionListener(e -> {
            txtLamaPinjam.setText("");
            txtDenda.setText("");
            listBuku.clearSelection();
            cbAnggota.setSelectedIndex(-1);
            table.clearSelection();
        });

        return panel;
    }

    // --- LOGIKA DATABASE --- //
    /**
     * Memuat daftar judul buku yang memiliki stok > 0 ke dalam komponen List pada form Transaksi.
     *
     * @param model Model struktur data List (DefaultListModel)
     */
    private void loadListBuku(DefaultListModel<String> model) {
        model.clear();
        try {
            List<Buku> listBuku = new BukuDAO().getAvailableBuku();
            for (Buku b : listBuku) {
                model.addElement(b.getIdBuku() + " - " + b.getJudul());
            }
        } catch (Exception e) {}
    }

    /**
     * Memuat daftar id dan nama anggota ke dalam komponen Dropdown (ComboBox) form Transaksi.
     *
     * @param cb Komponen JComboBox untuk menampilkan anggota
     */
    private void loadComboBoxAnggota(JComboBox<String> cb) {
        cb.removeAllItems();
        try {
            List<Anggota> listAnggota = new AnggotaDAO().getAll();
            for (Anggota a : listAnggota) {
                cb.addItem(a.getIdAnggota() + " - " + a.getNama());
            }
            cb.setSelectedIndex(-1);
        } catch (Exception e) {}
    }

    /**
     * Mengambil data seluruh buku dari DAO dan menampilkannya dalam Tabel GUI.
     *
     * @param model Model struktur data Tabel (DefaultTableModel) untuk form Kelola Buku
     */
    private void loadDataBuku(DefaultTableModel model) {
        model.setRowCount(0);
        try {
            List<Buku> listBuku = new BukuDAO().getAll();
            for (Buku b : listBuku) {
                model.addRow(new Object[]{
                    b.getIdBuku(), b.getJudul(), b.getPenulis(),
                    b.getKategori(), b.getStok()
                });
            }
        } catch (Exception e) {}
    }

    /**
     * Mengambil data seluruh anggota dari DAO dan menampilkannya dalam Tabel GUI.
     * Menangkap dan mencetak exception di console jika gagal meload data.
     *
     * @param model Model struktur data Tabel (DefaultTableModel) untuk form Kelola Anggota
     */
    private void loadDataAnggota(DefaultTableModel model) {
        model.setRowCount(0);
        try {
            List<Anggota> listAnggota = new AnggotaDAO().getAll();
            for (Anggota a : listAnggota) {
                model.addRow(new Object[]{
                    a.getIdAnggota(), a.getNama(), a.getKelas(),
                    a.getAlamat(), a.getNoHp()
                });
            }
        } catch (Exception e) {
            System.err.println("Gagal memuat data Anggota: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Mengambil data transaksi dari DAO dan menampilkannya ke dalam Tabel GUI.
     *
     * @param model Model struktur data Tabel (DefaultTableModel) untuk form Kelola Transaksi
     */
    private void loadDataTransaksi(DefaultTableModel model) {
        model.setRowCount(0);
        try {
            List<Transaksi> list = new TransaksiDAO().getAll();
            for (Transaksi t : list) {
                model.addRow(new Object[]{
                    t.getIdTransaksi(), t.getJudulBuku(), t.getNamaAnggota(),
                    t.getTanggalPinjam(), t.getTanggalJatuhTempo(),
                    t.getTanggalKembali(), t.getDenda(), t.getStatus()
                });
            }
        } catch (Exception e) {}
    }

    /**
     * Fungsi *Helper* untuk membuat rancangan tombol Menu Sidebar khusus dengan Flat Design.
     * Tombol dilengkapi dengan hover listener untuk merubah warnanya saat cursor mendekat/menghindar.
     *
     * @param text Teks judul pada menu tombol
     * @return Objek JButton sidebar
     */
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(SIDEBAR_BG); 
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setPreferredSize(new Dimension(0, 45));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(52, 73, 94)); 
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(SIDEBAR_BG);
            }
        });
        return button;
    }

    /**
     * Fungsi *Helper* membuat bentuk tombol Operasional (CRUD) berbasis warna spesifik.
     *
     * @param text Teks perintah (Simpan, Update, dll)
     * @param bg Warna background (Hex to RGB) dari button
     * @return Objek JButton CRUD
     */
    private JButton createActionButton(String text, Color bg) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(bg);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(110, 35));
        return button;
    }

    /**
     * Membuat JLabel seragam berwarna gelap tebal untuk judul Input Form.
     * 
     * @param text Label parameter yang disesuaikan
     * @return Objek JLabel
     */
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_REGULAR);
        label.setForeground(Color.DARK_GRAY);
        return label;
    }

    /**
     * Membuat Kotak Input Teks (JTextField) dengan gaya border modern dan padding dalam di formulir.
     *
     * @return Objek JTextField siap pakai
     */
    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(FONT_REGULAR);
        textField.setPreferredSize(new Dimension(0, 35));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return textField;
    }
    
    /**
     * Membuat Kotak Pilihan Dropdown Tergantung (JComboBox) untuk input dinamis.
     *
     * @return Objek JComboBox<String> berlatar putih.
     */
    private JComboBox<String> createComboBox() {
        JComboBox<String> cb = new JComboBox<>();
        cb.setFont(FONT_REGULAR);
        cb.setPreferredSize(new Dimension(0, 35));
        cb.setBackground(Color.WHITE);
        return cb;
    }

    /**
     * Mendesain estetika kolom Table agar lebih mudah dilihat, 
     * mencakup border biru, tinggi cell diperlebar, dan background sel belang-belang putih/abu-abu halus (via Renderer).
     *
     * @param model Model Default Tabel untuk menginject kerangka kolom (Rows)
     * @return Objek JTable dengan modifikasi warna dan Renderer
     */
    private JTable createStyledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setFont(FONT_REGULAR);
        table.setRowHeight(30); 
        table.setSelectionBackground(new Color(236, 240, 241));
        table.setSelectionForeground(Color.BLACK);
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tableHeader.setBackground(SIDEBAR_BG);
        tableHeader.setForeground(SIDEBAR_FG);
        tableHeader.setPreferredSize(new Dimension(0, 40)); 
        ((DefaultTableCellRenderer)tableHeader.getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);
        return table;
    }
}