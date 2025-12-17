import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Aplikasi Point of Sales (POS) untuk UMKM
 * Final Project - Menerapkan: Percabangan, Perulangan, Array, Pengurutan, Pencarian, dan Method
 * 
 * Fitur:
 * - 3 Mode User: Kasir, Admin, Owner
 * - PIN Authentication untuk setiap mode
 * - Transaksi pembelian multi-item
 * - CRUD data produk
 * - Laporan penjualan dan produk terlaris
 * - Selection Sort untuk pengurutan
 */
public class PointOfSales {
    
    // ==================== KONSTANTA ====================
    static final int MAX_PRODUK = 100;
    static final int MAX_TRANSAKSI = 1000;
    
    // PIN untuk setiap mode user
    static final String PIN_KASIR = "1234";
    static final String PIN_ADMIN = "5678";
    static final String PIN_OWNER = "9999";
    
    // ==================== DATA PRODUK (Master) ====================
    static String[] kodeProduk = new String[MAX_PRODUK];
    static String[] namaProduk = new String[MAX_PRODUK];
    static double[] hargaProduk = new double[MAX_PRODUK];
    static int[] stokProduk = new int[MAX_PRODUK];
    static int jumlahProduk = 0;
    
    // ==================== DATA TRANSAKSI ====================
    static String[] tanggalTransaksi = new String[MAX_TRANSAKSI];
    static String[] kodeProdukTerjual = new String[MAX_TRANSAKSI];
    static int[] jumlahTerjual = new int[MAX_TRANSAKSI];
    static double[] totalHargaTransaksi = new double[MAX_TRANSAKSI];
    static int jumlahTransaksi = 0;
    
    // Scanner untuk input
    static Scanner scanner = new Scanner(System.in);
    
    // ==================== MAIN METHOD ====================
    public static void main(String[] args) {
        // Inisialisasi data produk awal (sample)
        inisialisasiDataProduk();
        
        boolean running = true;
        
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║       SELAMAT DATANG DI APLIKASI POINT OF SALES UMKM          ║");
        System.out.println("║                     Toko Berkah Jaya                          ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        
        while (running) {
            System.out.println("\n╔═══════════════════════════════════════╗");
            System.out.println("║           MENU UTAMA                  ║");
            System.out.println("╠═══════════════════════════════════════╣");
            System.out.println("║  1. Mode Kasir                        ║");
            System.out.println("║  2. Mode Admin                        ║");
            System.out.println("║  3. Mode Owner                        ║");
            System.out.println("║  0. Keluar Program                    ║");
            System.out.println("╚═══════════════════════════════════════╝");
            System.out.print("Pilih mode user: ");
            
            int pilihan = bacaInteger();
            
            // Percabangan untuk memilih mode user
            switch (pilihan) {
                case 1:
                    if (autentikasiPIN("Kasir", PIN_KASIR)) {
                        menuKasir();
                    }
                    break;
                case 2:
                    if (autentikasiPIN("Admin", PIN_ADMIN)) {
                        menuAdmin();
                    }
                    break;
                case 3:
                    if (autentikasiPIN("Owner", PIN_OWNER)) {
                        menuOwner();
                    }
                    break;
                case 0:
                    running = false;
                    System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
                    System.out.println("║    Terima kasih telah menggunakan aplikasi POS UMKM!          ║");
                    System.out.println("║                   Sampai jumpa kembali!                       ║");
                    System.out.println("╚═══════════════════════════════════════════════════════════════╝");
                    break;
                default:
                    System.out.println("Pilihan tidak valid! Silakan pilih 0-3.");
            }
        }
        
        scanner.close();
    }
    
    // ==================== METHOD INISIALISASI DATA PRODUK ====================
    public static void inisialisasiDataProduk() {
        // Data produk sample
        tambahProdukLangsung("P001", "Beras 5kg", 65000, 50);
        tambahProdukLangsung("P002", "Minyak Goreng 1L", 18000, 100);
        tambahProdukLangsung("P003", "Gula Pasir 1kg", 15000, 75);
        tambahProdukLangsung("P004", "Kopi Sachet", 2000, 200);
        tambahProdukLangsung("P005", "Mie Instan", 3500, 150);
        tambahProdukLangsung("P006", "Telur 1kg", 28000, 40);
        tambahProdukLangsung("P007", "Susu Kotak", 8000, 60);
        tambahProdukLangsung("P008", "Roti Tawar", 15000, 30);
        tambahProdukLangsung("P009", "Sabun Mandi", 5000, 80);
        tambahProdukLangsung("P010", "Shampo Sachet", 1500, 120);
    }
    
    // Method helper untuk menambah produk langsung
    public static void tambahProdukLangsung(String kode, String nama, double harga, int stok) {
        if (jumlahProduk < MAX_PRODUK) {
            kodeProduk[jumlahProduk] = kode;
            namaProduk[jumlahProduk] = nama;
            hargaProduk[jumlahProduk] = harga;
            stokProduk[jumlahProduk] = stok;
            jumlahProduk++;
        }
    }
    
    // ==================== METHOD AUTENTIKASI PIN ====================
    public static boolean autentikasiPIN(String modeUser, String pinBenar) {
        System.out.print("Masukkan PIN " + modeUser + ": ");
        String inputPIN = scanner.nextLine();
        
        if (inputPIN.equals(pinBenar)) {
            System.out.println("PIN benar! Selamat datang, " + modeUser + "!");
            return true;
        } else {
            System.out.println("PIN salah! Akses ditolak.");
            return false;
        }
    }
    
    // ==================== METHOD HELPER INPUT ====================
    public static int bacaInteger() {
        int nilai = 0;
        boolean valid = false;
        
        while (!valid) {
            try {
                String input = scanner.nextLine();
                nilai = Integer.parseInt(input);
                valid = true;
            } catch (NumberFormatException e) {
                System.out.print("Input tidak valid! Masukkan angka: ");
            }
        }
        return nilai;
    }
    
    public static double bacaDouble() {
        double nilai = 0;
        boolean valid = false;
        
        while (!valid) {
            try {
                String input = scanner.nextLine();
                nilai = Double.parseDouble(input);
                valid = true;
            } catch (NumberFormatException e) {
                System.out.print("Input tidak valid! Masukkan angka: ");
            }
        }
        return nilai;
    }
    
    // ==================== MENU KASIR ====================
    public static void menuKasir() {
        boolean dalamMenuKasir = true;
        
        while (dalamMenuKasir) {
            System.out.println("\n╔═══════════════════════════════════════╗");
            System.out.println("║           MENU KASIR                  ║");
            System.out.println("╠═══════════════════════════════════════╣");
            System.out.println("║  1. Transaksi Baru                    ║");
            System.out.println("║  2. Lihat Daftar Produk               ║");
            System.out.println("║  3. Cari Produk                       ║");
            System.out.println("║  0. Kembali ke Menu Utama             ║");
            System.out.println("╚═══════════════════════════════════════╝");
            System.out.print("Pilih menu: ");
            
            int pilihan = bacaInteger();
            
            switch (pilihan) {
                case 1:
                    prosesTransaksi();
                    break;
                case 2:
                    tampilkanDaftarProduk();
                    break;
                case 3:
                    cariProduk();
                    break;
                case 0:
                    dalamMenuKasir = false;
                    System.out.println("Keluar dari mode Kasir...");
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }
    
    // ==================== PROSES TRANSAKSI (KASIR) ====================
    public static void prosesTransaksi() {
        System.out.println("\n═══════════ TRANSAKSI BARU ═══════════");
        
        // Array untuk menyimpan item dalam satu transaksi
        String[] kodeItemTransaksi = new String[MAX_PRODUK];
        String[] namaItemTransaksi = new String[MAX_PRODUK];
        int[] jumlahItemTransaksi = new int[MAX_PRODUK];
        double[] hargaItemTransaksi = new double[MAX_PRODUK];
        double[] subtotalItemTransaksi = new double[MAX_PRODUK];
        int jumlahItemDibeli = 0;
        
        boolean tambahBarang = true;
        
        // Tampilkan daftar produk terlebih dahulu
        tampilkanDaftarProduk();
        
        // Perulangan untuk memilih barang
        while (tambahBarang) {
            System.out.print("\nMasukkan kode produk (atau ketik 'selesai' untuk checkout): ");
            String kodeInput = scanner.nextLine().toUpperCase();
            
            if (kodeInput.equals("SELESAI")) {
                tambahBarang = false;
            } else {
                // Pencarian produk berdasarkan kode
                int indexProduk = cariIndexProduk(kodeInput);
                
                if (indexProduk != -1) {
                    System.out.println("Produk: " + namaProduk[indexProduk] + " - Rp " + formatHarga(hargaProduk[indexProduk]));
                    System.out.println("Stok tersedia: " + stokProduk[indexProduk]);
                    System.out.print("Masukkan jumlah: ");
                    int jumlah = bacaInteger();
                    
                    if (jumlah > 0 && jumlah <= stokProduk[indexProduk]) {
                        // Simpan ke array transaksi
                        kodeItemTransaksi[jumlahItemDibeli] = kodeProduk[indexProduk];
                        namaItemTransaksi[jumlahItemDibeli] = namaProduk[indexProduk];
                        jumlahItemTransaksi[jumlahItemDibeli] = jumlah;
                        hargaItemTransaksi[jumlahItemDibeli] = hargaProduk[indexProduk];
                        subtotalItemTransaksi[jumlahItemDibeli] = jumlah * hargaProduk[indexProduk];
                        jumlahItemDibeli++;
                        
                        System.out.println("✓ " + namaProduk[indexProduk] + " x" + jumlah + " ditambahkan ke keranjang.");
                    } else if (jumlah > stokProduk[indexProduk]) {
                        System.out.println("Stok tidak mencukupi!");
                    } else {
                        System.out.println("Jumlah tidak valid!");
                    }
                } else {
                    System.out.println("Produk dengan kode '" + kodeInput + "' tidak ditemukan!");
                }
            }
        }
        
        // Proses checkout jika ada barang yang dibeli
        if (jumlahItemDibeli > 0) {
            double totalBelanja = 0;
            
            System.out.println("\n╔═══════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                              STRUK PEMBELIAN                              ║");
            System.out.println("║                           Toko Berkah Jaya                                ║");
            System.out.println("╠═══════════════════════════════════════════════════════════════════════════╣");
            
            // Dapatkan waktu transaksi
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String waktuTransaksi = now.format(formatter);
            System.out.println("║  Tanggal: " + waktuTransaksi + "                                          ║");
            System.out.println("╠═══════════════════════════════════════════════════════════════════════════╣");
            System.out.printf("║ %-6s | %-20s | %8s | %6s | %12s ║%n", "Kode", "Nama Produk", "Harga", "Qty", "Subtotal");
            System.out.println("╠═══════════════════════════════════════════════════════════════════════════╣");
            
            // Perulangan untuk menampilkan item dan menghitung total
            for (int i = 0; i < jumlahItemDibeli; i++) {
                System.out.printf("║ %-6s | %-20s | %8.0f | %6d | %12.0f ║%n",
                    kodeItemTransaksi[i],
                    namaItemTransaksi[i].length() > 20 ? namaItemTransaksi[i].substring(0, 17) + "..." : namaItemTransaksi[i],
                    hargaItemTransaksi[i],
                    jumlahItemTransaksi[i],
                    subtotalItemTransaksi[i]);
                totalBelanja += subtotalItemTransaksi[i];
                
                // Simpan ke data transaksi untuk laporan
                simpanTransaksi(waktuTransaksi, kodeItemTransaksi[i], jumlahItemTransaksi[i], subtotalItemTransaksi[i]);
                
                // Update stok produk
                int indexProduk = cariIndexProduk(kodeItemTransaksi[i]);
                if (indexProduk != -1) {
                    stokProduk[indexProduk] -= jumlahItemTransaksi[i];
                }
            }
            
            System.out.println("╠═══════════════════════════════════════════════════════════════════════════╣");
            System.out.printf("║                                     TOTAL: Rp %,15.0f            ║%n", totalBelanja);
            System.out.println("╠═══════════════════════════════════════════════════════════════════════════╣");
            
            // Proses pembayaran
            System.out.print("Masukkan jumlah uang pembayaran: Rp ");
            double pembayaran = bacaDouble();
            
            while (pembayaran < totalBelanja) {
                System.out.println("Uang tidak cukup! Total: Rp " + formatHarga(totalBelanja));
                System.out.print("Masukkan jumlah uang pembayaran: Rp ");
                pembayaran = bacaDouble();
            }
            
            double kembalian = pembayaran - totalBelanja;
            System.out.printf("║  Bayar    : Rp %,15.0f                                          ║%n", pembayaran);
            System.out.printf("║  Kembalian: Rp %,15.0f                                          ║%n", kembalian);
            System.out.println("╠═══════════════════════════════════════════════════════════════════════════╣");
            System.out.println("║              Terima kasih atas kunjungan Anda!                            ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════════════════╝");
            
        } else {
            System.out.println("Tidak ada barang yang dibeli. Transaksi dibatalkan.");
        }
    }
    
    // ==================== METHOD SIMPAN TRANSAKSI ====================
    public static void simpanTransaksi(String tanggal, String kodeProdukItem, int jumlah, double total) {
        if (jumlahTransaksi < MAX_TRANSAKSI) {
            tanggalTransaksi[jumlahTransaksi] = tanggal;
            kodeProdukTerjual[jumlahTransaksi] = kodeProdukItem;
            jumlahTerjual[jumlahTransaksi] = jumlah;
            totalHargaTransaksi[jumlahTransaksi] = total;
            jumlahTransaksi++;
        }
    }
    
    // ==================== METHOD PENCARIAN PRODUK ====================
    public static int cariIndexProduk(String kode) {
        // Pencarian linear berdasarkan kode produk
        for (int i = 0; i < jumlahProduk; i++) {
            if (kodeProduk[i].equalsIgnoreCase(kode)) {
                return i;
            }
        }
        return -1; // Tidak ditemukan
    }
    
    public static int cariIndexProdukByNama(String nama) {
        // Pencarian berdasarkan nama produk (partial match)
        for (int i = 0; i < jumlahProduk; i++) {
            if (namaProduk[i].toLowerCase().contains(nama.toLowerCase())) {
                return i;
            }
        }
        return -1; // Tidak ditemukan
    }
    
    public static void cariProduk() {
        System.out.println("\n═══════════ CARI PRODUK ═══════════");
        System.out.println("1. Cari berdasarkan Kode");
        System.out.println("2. Cari berdasarkan Nama");
        System.out.print("Pilih metode pencarian: ");
        int pilihan = bacaInteger();
        
        switch (pilihan) {
            case 1:
                System.out.print("Masukkan kode produk: ");
                String kode = scanner.nextLine().toUpperCase();
                int indexByKode = cariIndexProduk(kode);
                if (indexByKode != -1) {
                    tampilkanDetailProduk(indexByKode);
                } else {
                    System.out.println("Produk tidak ditemukan!");
                }
                break;
            case 2:
                System.out.print("Masukkan nama produk: ");
                String nama = scanner.nextLine();
                System.out.println("\nHasil pencarian untuk '" + nama + "':");
                boolean ditemukan = false;
                for (int i = 0; i < jumlahProduk; i++) {
                    if (namaProduk[i].toLowerCase().contains(nama.toLowerCase())) {
                        tampilkanDetailProduk(i);
                        ditemukan = true;
                    }
                }
                if (!ditemukan) {
                    System.out.println("Produk tidak ditemukan!");
                }
                break;
            default:
                System.out.println("Pilihan tidak valid!");
        }
    }
    
    public static void tampilkanDetailProduk(int index) {
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│ Kode  : " + kodeProduk[index]);
        System.out.println("│ Nama  : " + namaProduk[index]);
        System.out.println("│ Harga : Rp " + formatHarga(hargaProduk[index]));
        System.out.println("│ Stok  : " + stokProduk[index]);
        System.out.println("└─────────────────────────────────────┘");
    }
    
    // ==================== TAMPILKAN DAFTAR PRODUK (DENGAN SELECTION SORT) ====================
    public static void tampilkanDaftarProduk() {
        System.out.println("\n═══════════════════════════════════ DAFTAR PRODUK ═══════════════════════════════════");
        System.out.println("Urut berdasarkan: 1. Kode | 2. Nama | 3. Harga (Murah-Mahal) | 4. Harga (Mahal-Murah)");
        System.out.print("Pilih urutan (default: 1): ");
        String input = scanner.nextLine();
        int urutBerdasarkan = input.isEmpty() ? 1 : Integer.parseInt(input);
        
        // Buat copy array untuk sorting agar tidak mengubah data asli
        String[] kodeSort = new String[jumlahProduk];
        String[] namaSort = new String[jumlahProduk];
        double[] hargaSort = new double[jumlahProduk];
        int[] stokSort = new int[jumlahProduk];
        
        for (int i = 0; i < jumlahProduk; i++) {
            kodeSort[i] = kodeProduk[i];
            namaSort[i] = namaProduk[i];
            hargaSort[i] = hargaProduk[i];
            stokSort[i] = stokProduk[i];
        }
        
        // Panggil method Selection Sort dengan parameter
        selectionSort(kodeSort, namaSort, hargaSort, stokSort, jumlahProduk, urutBerdasarkan);
        
        // Tampilkan hasil
        System.out.println("┌────────┬──────────────────────────┬───────────────┬────────┐");
        System.out.printf("│ %-6s │ %-24s │ %13s │ %6s │%n", "Kode", "Nama Produk", "Harga", "Stok");
        System.out.println("├────────┼──────────────────────────┼───────────────┼────────┤");
        
        for (int i = 0; i < jumlahProduk; i++) {
            System.out.printf("│ %-6s │ %-24s │ Rp %10s │ %6d │%n",
                kodeSort[i],
                namaSort[i].length() > 24 ? namaSort[i].substring(0, 21) + "..." : namaSort[i],
                formatHarga(hargaSort[i]),
                stokSort[i]);
        }
        
        System.out.println("└────────┴──────────────────────────┴───────────────┴────────┘");
        System.out.println("Total produk: " + jumlahProduk);
    }
    
    // ==================== SELECTION SORT METHOD (DENGAN PARAMETER) ====================
    public static void selectionSort(String[] kode, String[] nama, double[] harga, int[] stok, int n, int kriteria) {
        // Selection Sort dengan kriteria berbeda
        // kriteria: 1 = kode, 2 = nama, 3 = harga (ascending), 4 = harga (descending)
        
        for (int i = 0; i < n - 1; i++) {
            int indexTerpilih = i;
            
            for (int j = i + 1; j < n; j++) {
                boolean perluTukar = false;
                
                // Percabangan berdasarkan kriteria pengurutan
                switch (kriteria) {
                    case 1: // Sort by kode (ascending)
                        perluTukar = kode[j].compareTo(kode[indexTerpilih]) < 0;
                        break;
                    case 2: // Sort by nama (ascending)
                        perluTukar = nama[j].compareToIgnoreCase(nama[indexTerpilih]) < 0;
                        break;
                    case 3: // Sort by harga (ascending - murah ke mahal)
                        perluTukar = harga[j] < harga[indexTerpilih];
                        break;
                    case 4: // Sort by harga (descending - mahal ke murah)
                        perluTukar = harga[j] > harga[indexTerpilih];
                        break;
                    default:
                        perluTukar = kode[j].compareTo(kode[indexTerpilih]) < 0;
                }
                
                if (perluTukar) {
                    indexTerpilih = j;
                }
            }
            
            // Tukar semua elemen terkait
            if (indexTerpilih != i) {
                // Tukar kode
                String tempKode = kode[i];
                kode[i] = kode[indexTerpilih];
                kode[indexTerpilih] = tempKode;
                
                // Tukar nama
                String tempNama = nama[i];
                nama[i] = nama[indexTerpilih];
                nama[indexTerpilih] = tempNama;
                
                // Tukar harga
                double tempHarga = harga[i];
                harga[i] = harga[indexTerpilih];
                harga[indexTerpilih] = tempHarga;
                
                // Tukar stok
                int tempStok = stok[i];
                stok[i] = stok[indexTerpilih];
                stok[indexTerpilih] = tempStok;
            }
        }
    }
    
    // ==================== MENU ADMIN ====================
    public static void menuAdmin() {
        boolean dalamMenuAdmin = true;
        
        while (dalamMenuAdmin) {
            System.out.println("\n╔═══════════════════════════════════════╗");
            System.out.println("║           MENU ADMIN                  ║");
            System.out.println("╠═══════════════════════════════════════╣");
            System.out.println("║  1. Lihat Daftar Produk               ║");
            System.out.println("║  2. Tambah Produk Baru                ║");
            System.out.println("║  3. Ubah Data Produk                  ║");
            System.out.println("║  4. Hapus Produk                      ║");
            System.out.println("║  5. Cari Produk                       ║");
            System.out.println("║  0. Kembali ke Menu Utama             ║");
            System.out.println("╚═══════════════════════════════════════╝");
            System.out.print("Pilih menu: ");
            
            int pilihan = bacaInteger();
            
            switch (pilihan) {
                case 1:
                    tampilkanDaftarProduk();
                    break;
                case 2:
                    tambahProduk();
                    break;
                case 3:
                    ubahProduk();
                    break;
                case 4:
                    hapusProduk();
                    break;
                case 5:
                    cariProduk();
                    break;
                case 0:
                    dalamMenuAdmin = false;
                    System.out.println("Keluar dari mode Admin...");
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }
    
    // ==================== CRUD PRODUK (ADMIN) ====================
    public static void tambahProduk() {
        System.out.println("\n═══════════ TAMBAH PRODUK BARU ═══════════");
        
        if (jumlahProduk >= MAX_PRODUK) {
            System.out.println("Kapasitas produk sudah penuh!");
            return;
        }
        
        System.out.print("Masukkan kode produk: ");
        String kode = scanner.nextLine().toUpperCase();
        
        // Cek apakah kode sudah ada
        if (cariIndexProduk(kode) != -1) {
            System.out.println("Kode produk sudah ada! Gunakan kode lain.");
            return;
        }
        
        System.out.print("Masukkan nama produk: ");
        String nama = scanner.nextLine();
        
        System.out.print("Masukkan harga produk: Rp ");
        double harga = bacaDouble();
        
        System.out.print("Masukkan stok produk: ");
        int stok = bacaInteger();
        
        // Tambah ke array
        kodeProduk[jumlahProduk] = kode;
        namaProduk[jumlahProduk] = nama;
        hargaProduk[jumlahProduk] = harga;
        stokProduk[jumlahProduk] = stok;
        jumlahProduk++;
        
        System.out.println("✓ Produk berhasil ditambahkan!");
    }
    
    public static void ubahProduk() {
        System.out.println("\n═══════════ UBAH DATA PRODUK ═══════════");
        System.out.print("Masukkan kode produk yang akan diubah: ");
        String kode = scanner.nextLine().toUpperCase();
        
        int index = cariIndexProduk(kode);
        
        if (index != -1) {
            System.out.println("\nData produk saat ini:");
            tampilkanDetailProduk(index);
            
            System.out.println("\nMasukkan data baru (kosongkan jika tidak ingin mengubah):");
            
            System.out.print("Nama baru [" + namaProduk[index] + "]: ");
            String namaBaru = scanner.nextLine();
            if (!namaBaru.isEmpty()) {
                namaProduk[index] = namaBaru;
            }
            
            System.out.print("Harga baru [" + hargaProduk[index] + "]: ");
            String hargaBaruStr = scanner.nextLine();
            if (!hargaBaruStr.isEmpty()) {
                hargaProduk[index] = Double.parseDouble(hargaBaruStr);
            }
            
            System.out.print("Stok baru [" + stokProduk[index] + "]: ");
            String stokBaruStr = scanner.nextLine();
            if (!stokBaruStr.isEmpty()) {
                stokProduk[index] = Integer.parseInt(stokBaruStr);
            }
            
            System.out.println("✓ Data produk berhasil diubah!");
            tampilkanDetailProduk(index);
        } else {
            System.out.println("Produk tidak ditemukan!");
        }
    }
    
    public static void hapusProduk() {
        System.out.println("\n═══════════ HAPUS PRODUK ═══════════");
        System.out.print("Masukkan kode produk yang akan dihapus: ");
        String kode = scanner.nextLine().toUpperCase();
        
        int index = cariIndexProduk(kode);
        
        if (index != -1) {
            System.out.println("Produk yang akan dihapus:");
            tampilkanDetailProduk(index);
            
            System.out.print("Yakin ingin menghapus? (y/n): ");
            String konfirmasi = scanner.nextLine();
            
            if (konfirmasi.equalsIgnoreCase("y")) {
                // Geser semua elemen setelah index ke kiri
                for (int i = index; i < jumlahProduk - 1; i++) {
                    kodeProduk[i] = kodeProduk[i + 1];
                    namaProduk[i] = namaProduk[i + 1];
                    hargaProduk[i] = hargaProduk[i + 1];
                    stokProduk[i] = stokProduk[i + 1];
                }
                jumlahProduk--;
                System.out.println("✓ Produk berhasil dihapus!");
            } else {
                System.out.println("Penghapusan dibatalkan.");
            }
        } else {
            System.out.println("Produk tidak ditemukan!");
        }
    }
    
    // ==================== MENU OWNER ====================
    public static void menuOwner() {
        boolean dalamMenuOwner = true;
        
        while (dalamMenuOwner) {
            System.out.println("\n╔═══════════════════════════════════════╗");
            System.out.println("║           MENU OWNER                  ║");
            System.out.println("╠═══════════════════════════════════════╣");
            System.out.println("║  1. Laporan Total Pemasukan           ║");
            System.out.println("║  2. Top 5 Produk Terlaris             ║");
            System.out.println("║  3. Laporan Transaksi Harian          ║");
            System.out.println("║  4. Lihat Semua Transaksi             ║");
            System.out.println("║  0. Kembali ke Menu Utama             ║");
            System.out.println("╚═══════════════════════════════════════╝");
            System.out.print("Pilih menu: ");
            
            int pilihan = bacaInteger();
            
            switch (pilihan) {
                case 1:
                    laporanTotalPemasukan();
                    break;
                case 2:
                    top5ProdukTerlaris();
                    break;
                case 3:
                    laporanHarian();
                    break;
                case 4:
                    lihatSemuaTransaksi();
                    break;
                case 0:
                    dalamMenuOwner = false;
                    System.out.println("Keluar dari mode Owner...");
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }
    
    // ==================== LAPORAN (OWNER) ====================
    public static void laporanTotalPemasukan() {
        System.out.println("\n═══════════ LAPORAN TOTAL PEMASUKAN ═══════════");
        
        if (jumlahTransaksi == 0) {
            System.out.println("Belum ada transaksi.");
            return;
        }
        
        double totalPemasukan = 0;
        int totalItemTerjual = 0;
        
        for (int i = 0; i < jumlahTransaksi; i++) {
            totalPemasukan += totalHargaTransaksi[i];
            totalItemTerjual += jumlahTerjual[i];
        }
        
        System.out.println("╔═══════════════════════════════════════════╗");
        System.out.println("║         RINGKASAN PEMASUKAN               ║");
        System.out.println("╠═══════════════════════════════════════════╣");
        System.out.printf("║  Total Transaksi    : %6d item          ║%n", jumlahTransaksi);
        System.out.printf("║  Total Item Terjual : %6d item          ║%n", totalItemTerjual);
        System.out.println("╠═══════════════════════════════════════════╣");
        System.out.printf("║  TOTAL PEMASUKAN    : Rp %,15.0f  ║%n", totalPemasukan);
        System.out.println("╚═══════════════════════════════════════════╝");
    }
    
    public static void top5ProdukTerlaris() {
        System.out.println("\n═══════════ TOP 5 PRODUK TERLARIS ═══════════");
        
        if (jumlahTransaksi == 0) {
            System.out.println("Belum ada transaksi.");
            return;
        }
        
        // Hitung total penjualan per produk
        String[] kodeUnik = new String[jumlahProduk];
        String[] namaUnik = new String[jumlahProduk];
        int[] totalTerjualPerProduk = new int[jumlahProduk];
        double[] totalPendapatanPerProduk = new double[jumlahProduk];
        int jumlahProdukTerjual = 0;
        
        // Perulangan untuk menghitung total per produk
        for (int i = 0; i < jumlahTransaksi; i++) {
            String kodeTr = kodeProdukTerjual[i];
            boolean ditemukan = false;
            
            // Cari apakah produk sudah ada di array unik
            for (int j = 0; j < jumlahProdukTerjual; j++) {
                if (kodeUnik[j].equals(kodeTr)) {
                    totalTerjualPerProduk[j] += jumlahTerjual[i];
                    totalPendapatanPerProduk[j] += totalHargaTransaksi[i];
                    ditemukan = true;
                    break;
                }
            }
            
            // Jika belum ada, tambahkan
            if (!ditemukan) {
                kodeUnik[jumlahProdukTerjual] = kodeTr;
                int indexProduk = cariIndexProduk(kodeTr);
                namaUnik[jumlahProdukTerjual] = (indexProduk != -1) ? namaProduk[indexProduk] : "Produk Dihapus";
                totalTerjualPerProduk[jumlahProdukTerjual] = jumlahTerjual[i];
                totalPendapatanPerProduk[jumlahProdukTerjual] = totalHargaTransaksi[i];
                jumlahProdukTerjual++;
            }
        }
        
        // Selection Sort berdasarkan jumlah terjual (descending)
        selectionSortProdukTerlaris(kodeUnik, namaUnik, totalTerjualPerProduk, totalPendapatanPerProduk, jumlahProdukTerjual);
        
        // Tampilkan top 5
        int tampilkan = Math.min(5, jumlahProdukTerjual);
        
        System.out.println("╔══════╦════════╦══════════════════════════╦══════════╦═══════════════════╗");
        System.out.printf("║ %4s ║ %-6s ║ %-24s ║ %8s ║ %17s ║%n", "Rank", "Kode", "Nama Produk", "Terjual", "Total Pendapatan");
        System.out.println("╠══════╬════════╬══════════════════════════╬══════════╬═══════════════════╣");
        
        for (int i = 0; i < tampilkan; i++) {
            System.out.printf("║  #%-2d ║ %-6s ║ %-24s ║ %8d ║ Rp %,13.0f ║%n",
                (i + 1),
                kodeUnik[i],
                namaUnik[i].length() > 24 ? namaUnik[i].substring(0, 21) + "..." : namaUnik[i],
                totalTerjualPerProduk[i],
                totalPendapatanPerProduk[i]);
        }
        
        System.out.println("╚══════╩════════╩══════════════════════════╩══════════╩═══════════════════╝");
    }
    
    // Selection Sort untuk produk terlaris (descending by jumlah terjual)
    public static void selectionSortProdukTerlaris(String[] kode, String[] nama, int[] jumlah, double[] pendapatan, int n) {
        for (int i = 0; i < n - 1; i++) {
            int indexMax = i;
            
            for (int j = i + 1; j < n; j++) {
                if (jumlah[j] > jumlah[indexMax]) {
                    indexMax = j;
                }
            }
            
            if (indexMax != i) {
                // Tukar kode
                String tempKode = kode[i];
                kode[i] = kode[indexMax];
                kode[indexMax] = tempKode;
                
                // Tukar nama
                String tempNama = nama[i];
                nama[i] = nama[indexMax];
                nama[indexMax] = tempNama;
                
                // Tukar jumlah
                int tempJumlah = jumlah[i];
                jumlah[i] = jumlah[indexMax];
                jumlah[indexMax] = tempJumlah;
                
                // Tukar pendapatan
                double tempPendapatan = pendapatan[i];
                pendapatan[i] = pendapatan[indexMax];
                pendapatan[indexMax] = tempPendapatan;
            }
        }
    }
    
    public static void laporanHarian() {
        System.out.println("\n═══════════ LAPORAN TRANSAKSI HARIAN ═══════════");
        
        if (jumlahTransaksi == 0) {
            System.out.println("Belum ada transaksi.");
            return;
        }
        
        // Ambil tanggal unik
        String[] tanggalUnik = new String[jumlahTransaksi];
        double[] totalPerHari = new double[jumlahTransaksi];
        int[] jumlahItemPerHari = new int[jumlahTransaksi];
        int jumlahHari = 0;
        
        for (int i = 0; i < jumlahTransaksi; i++) {
            // Ambil hanya bagian tanggal (dd-MM-yyyy)
            String tanggal = tanggalTransaksi[i].substring(0, 10);
            boolean ditemukan = false;
            
            for (int j = 0; j < jumlahHari; j++) {
                if (tanggalUnik[j].equals(tanggal)) {
                    totalPerHari[j] += totalHargaTransaksi[i];
                    jumlahItemPerHari[j] += jumlahTerjual[i];
                    ditemukan = true;
                    break;
                }
            }
            
            if (!ditemukan) {
                tanggalUnik[jumlahHari] = tanggal;
                totalPerHari[jumlahHari] = totalHargaTransaksi[i];
                jumlahItemPerHari[jumlahHari] = jumlahTerjual[i];
                jumlahHari++;
            }
        }
        
        // Tampilkan laporan harian
        System.out.println("╔══════════════╦══════════════╦═══════════════════════╗");
        System.out.printf("║ %-12s ║ %12s ║ %21s ║%n", "Tanggal", "Item Terjual", "Total Pemasukan");
        System.out.println("╠══════════════╬══════════════╬═══════════════════════╣");
        
        double grandTotal = 0;
        for (int i = 0; i < jumlahHari; i++) {
            System.out.printf("║ %-12s ║ %12d ║ Rp %,17.0f ║%n",
                tanggalUnik[i],
                jumlahItemPerHari[i],
                totalPerHari[i]);
            grandTotal += totalPerHari[i];
        }
        
        System.out.println("╠══════════════╬══════════════╬═══════════════════════╣");
        System.out.printf("║ %-12s ║ %12s ║ Rp %,17.0f ║%n", "TOTAL", "", grandTotal);
        System.out.println("╚══════════════╩══════════════╩═══════════════════════╝");
    }
    
    public static void lihatSemuaTransaksi() {
        System.out.println("\n═══════════ DAFTAR SEMUA TRANSAKSI ═══════════");
        
        if (jumlahTransaksi == 0) {
            System.out.println("Belum ada transaksi.");
            return;
        }
        
        System.out.println("╔════╦═════════════════════╦════════╦════════╦═══════════════════╗");
        System.out.printf("║ %2s ║ %-19s ║ %-6s ║ %-6s ║ %17s ║%n", "No", "Tanggal & Waktu", "Kode", "Qty", "Total");
        System.out.println("╠════╬═════════════════════╬════════╬════════╬═══════════════════╣");
        
        for (int i = 0; i < jumlahTransaksi; i++) {
            System.out.printf("║ %2d ║ %-19s ║ %-6s ║ %6d ║ Rp %,13.0f ║%n",
                (i + 1),
                tanggalTransaksi[i],
                kodeProdukTerjual[i],
                jumlahTerjual[i],
                totalHargaTransaksi[i]);
        }
        
        System.out.println("╚════╩═════════════════════╩════════╩════════╩═══════════════════╝");
    }
    
    // ==================== METHOD HELPER FORMAT HARGA ====================
    public static String formatHarga(double harga) {
        return String.format("%,.0f", harga);
    }
}
