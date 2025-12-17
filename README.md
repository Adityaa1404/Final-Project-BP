# Point of Sales (POS) untuk UMKM

Aplikasi Point of Sales sederhana berbasis console untuk UMKM menggunakan Java.

## 📋 Deskripsi

Aplikasi ini dibuat sebagai Final Project yang menerapkan semua topik bahasan kuliah:
- ✅ **Percabangan** - if-else, switch-case untuk menu dan validasi
- ✅ **Perulangan** - while, for untuk iterasi data dan menu interaktif
- ✅ **Array** - Penyimpanan data produk dan transaksi
- ✅ **Pengurutan** - Selection Sort untuk mengurutkan data
- ✅ **Pencarian** - Linear search untuk mencari produk
- ✅ **Method** - Fungsi-fungsi modular dengan parameter

## 👥 Mode User

Aplikasi memiliki 3 mode user dengan PIN masing-masing:

| Mode | PIN | Deskripsi |
|------|-----|-----------|
| **Kasir** | 1234 | Menangani transaksi pembelian |
| **Admin** | 5678 | Mengelola data produk (CRUD) |
| **Owner** | 9999 | Melihat laporan penjualan |

## 🔧 Fitur

### Mode Kasir
- 🛒 Transaksi pembelian multi-item
- 📋 Lihat daftar produk dengan sorting
- 🔍 Cari produk berdasarkan kode/nama
- 🧾 Cetak struk pembelian

### Mode Admin
- 📋 Lihat daftar produk
- ➕ Tambah produk baru
- ✏️ Ubah data produk (dengan pencarian)
- ❌ Hapus produk
- 🔍 Cari produk

### Mode Owner
- 💰 Laporan total pemasukan
- 🏆 Top 5 produk terlaris
- 📅 Laporan transaksi harian
- 📊 Lihat semua transaksi

## 🚀 Cara Menjalankan

### Prasyarat
- Java Development Kit (JDK) 8 atau lebih baru

### Langkah-langkah

1. **Compile** program:
   ```bash
   javac PointOfSales.java
   ```

2. **Jalankan** program:
   ```bash
   java PointOfSales
   ```

## 📁 Struktur File

```
gdg/
├── PointOfSales.java    # File utama aplikasi
├── README.md            # Dokumentasi
└── .github/
    └── copilot-instructions.md
```

## 📝 Catatan Teknis

### Selection Sort
Method Selection Sort diimplementasikan dengan parameter untuk fleksibilitas:
```java
selectionSort(String[] kode, String[] nama, double[] harga, int[] stok, int n, int kriteria)
```
Kriteria pengurutan:
1. Berdasarkan kode produk (ascending)
2. Berdasarkan nama produk (ascending)
3. Berdasarkan harga (murah ke mahal)
4. Berdasarkan harga (mahal ke murah)

### Pencarian
Pencarian linear untuk menemukan produk berdasarkan:
- Kode produk (exact match)
- Nama produk (partial match)

### Struktur Data
Menggunakan parallel arrays untuk menyimpan data produk dan transaksi dengan kapasitas maksimum yang telah ditentukan.

## 👨‍💻 Pengembang

Final Project - Point of Sales UMKM

---
*Dibuat dengan ❤️ menggunakan Java*
