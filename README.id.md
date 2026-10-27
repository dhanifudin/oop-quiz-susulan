# Kuis Latihan OOP (Susulan) — Penyewaan Alat Laboratorium

🇬🇧 English version: [README.md](README.md)

> **Kuis susulan.** Kuis ini untuk mahasiswa yang tidak mengikuti kuis utama (`oop-quiz`, Kasir Kafe). Konsep, struktur, tingkat kesulitan, jumlah tes, dan bobot nilainya sama persis. Yang berbeda hanya studi kasusnya.

## Studi Kasus

Kalian akan membangun inti dari sebuah **aplikasi penyewaan alat laboratorium** sederhana:

- Laboratorium memiliki **inventaris** alat yang bisa disewa (tripod, lampu studio, kamera, ...). Setiap alat punya **tarif harian**.
- Seorang **penyewa** membuat **penyewaan** yang berisi satu atau lebih alat beserta lama sewanya dalam **hari**.
- Penyewaan senilai **Rp 100.000 atau lebih** mendapat **diskon 10%**.
- **Petugas (clerk)** menerima uang tunai dan menghitung kembaliannya.

Kuis ini mencakup empat konsep OOP: **class**, **object**, **encapsulation**, dan **relasi antar kelas** (association, aggregation, composition, dependency).

## Diagram Kelas

Diagram kelas adalah **spesifikasi utama** kalian: semua kelas, field, konstruktor, dan signature method yang dibutuhkan sudah didefinisikan di sana. Bacalah dengan teliti.

![Class Diagram](docs/class-diagram.png)

Kelas yang bertanda **«create this class»** belum ada — kalian harus membuatnya sendiri.

### 4 Relasi Kelas dalam Proyek Ini

| Relasi | Di mana | Maknanya di sini |
|---|---|---|
| **Association** | `RentalLine → Equipment`, `Rental → Renter` | Objek menyimpan referensi ke objek lain yang hidup mandiri. |
| **Aggregation** | `Inventory ◇→ Equipment` | `Inventory` mengumpulkan objek `Equipment` yang **dibuat di luar** lalu dimasukkan; mereka tetap bisa ada tanpa inventaris. |
| **Composition** | `Rental ◆→ RentalLine` | `Rental` **membuat sendiri** objek `RentalLine` di dalam `addLine(...)`; objek itu tidak bisa ada tanpa penyewaannya. |
| **Dependency** | `Clerk ⇢ Rental` | `Clerk` hanya **memakai** `Rental` sebagai parameter method; tidak pernah menyimpannya di field. |

## Memulai

Repositori ini adalah **template**:

1. Klik tombol hijau **Use this template** (kanan atas) → **Create a new repository**, atur visibilitas ke **Private**, beri nama misalnya `oop-quiz-susulan-<nama-kalian>`.
2. Clone repositori **milik kalian**:
   ```bash
   git clone git@github.com:<username-kalian>/oop-quiz-susulan-<nama-kalian>.git
   ```
3. Buka proyek di editor (lihat di bawah), implementasikan kelas-kelasnya, lalu commit dan push.
4. Setiap push menjalankan autograder. Buka tab **Actions** di repositori kalian untuk melihat skor. Jika GitHub meminta mengaktifkan workflow saat pertama kali, klik **I understand my workflows, enable them**.

## Membuka Proyek

Ini proyek Maven standar — tanpa konfigurasi tambahan.

**NetBeans**
1. **File → Open Project…**
2. Pilih folder `oop-quiz-susulan-...` hasil clone (NetBeans otomatis mengenalinya sebagai proyek Maven) → **Open Project**.
3. Klik kanan proyek → **Test** untuk menjalankan semua tes, atau jalankan satu file tes dari node `Test Packages`.

**Visual Studio Code**
1. Pasang **Extension Pack for Java** (Microsoft) dari panel Extensions.
2. **File → Open Folder…** lalu pilih folder hasil clone.
3. Jalankan tes dari side bar **Testing** (ikon labu), atau lewat terminal:
   ```bash
   mvn test
   ```

## Bagian 1 — Lengkapi Kelas Kerangka (Skeleton)

`Equipment`, `Renter`, dan `Inventory` sudah ada di `src/main/java/id/ac/polinema/oop/`. Ganti setiap `throw new UnsupportedOperationException(...)` dengan implementasi yang benar. Struktur (field, konstruktor, signature) diambil dari diagram kelas; aturan perilakunya:

- **`Equipment`** — `setDailyRate` **mengabaikan** tarif **negatif** dan mempertahankan tarif lama.
- **`Renter`** — `setName` **mengabaikan** nama **null atau kosong (blank)** dan mempertahankan nama lama.
- **`Inventory`** *(aggregation)* — array berkapasitas **10**. `addEquipment` menyimpan alat pada indeks `equipmentCount` lalu menaikkan counter; jika inventaris penuh, tidak melakukan apa-apa. `findEquipment` mencocokkan nama secara persis dan mengembalikan `null` jika tidak ditemukan.

**Gunakan array biasa — JANGAN gunakan `List`/`ArrayList`** (Collections adalah materi pertemuan berikutnya).

## Bagian 2 — Buat Kelas Baru

`RentalLine`, `Rental`, dan `Clerk` **belum ada**. Buatlah di `src/main/java/id/ac/polinema/oop/` persis seperti yang digambarkan pada diagram kelas. Aturan perilakunya:

- **`RentalLine`** *(association)* — `getSubtotal()` mengembalikan tarif harian alat × jumlah hari.
- **`Rental`** *(composition, association)* — array berkapasitas **10**, seperti `Inventory`. `addLine(Equipment, int)` **membuat objek `RentalLine` di dalam method** (inilah composition!); jika penyewaan penuh, tidak melakukan apa-apa. `getTotal()` menjumlahkan subtotal semua baris. `getFinalTotal()` menerapkan **diskon 10%** jika total **≥ 100000**, selain itu mengembalikan total apa adanya.
- **`Clerk`** *(dependency)* — tanpa field; `Rental` hanya parameter. `calculateChange(Rental, double)` mengembalikan uang tunai dikurangi total akhir.

Tips: kerjakan sesuai urutan tabel penilaian di bawah — poinnya kecil dan bertahap, jadi setiap langkah yang selesai langsung menambah skor kalian.

## Coba Aplikasinya Secara Manual

`Main.java` adalah area bebas untuk mencoba (tidak dinilai). Setelah semua kelas selesai, tulis demo kalian di sana, contohnya:

```java
public static void main(String[] args) {
    Inventory inventory = new Inventory();
    inventory.addEquipment(new Equipment("Tripod", 18000));
    inventory.addEquipment(new Equipment("Lampu Studio", 12000));

    Renter sari = new Renter("R001", "Sari Wulandari");
    Rental rental = new Rental(sari);
    rental.addLine(inventory.findEquipment("Tripod"), 2);
    rental.addLine(inventory.findEquipment("Lampu Studio"), 1);

    Clerk clerk = new Clerk();
    double cash = 50000;

    System.out.println("Renter  : " + rental.getRenter().getName());
    System.out.println("Total   : " + rental.getTotal());
    System.out.println("Payable : " + rental.getFinalTotal());
    System.out.println("Cash    : " + cash);
    System.out.println("Change  : " + clerk.calculateChange(rental, cash));
}
```

Jalankan dari NetBeans (**Run Project**), VS Code (**Run** di atas `main`), atau terminal:

```bash
mvn -q compile exec:java
```

Keluaran yang diharapkan:

```
Renter  : Sari Wulandari
Total   : 48000.0
Payable : 48000.0
Cash    : 50000.0
Change  : 2000.0
```

## Penilaian

Total **100 poin**, terbagi ke 12 grup tes kecil (dijalankan lewat GitHub Actions pada setiap push). Uji dulu secara lokal:

```bash
mvn test                                  # semuanya
mvn test -Dtest=EquipmentConstructorTest  # satu grup saja
```

| # | Grup Tes | Konsep | Poin |
|---|---|---|---|
| 1 | Equipment Constructor Test | class & object | 5 |
| 2 | Equipment Getter Test | class & object | 5 |
| 3 | Equipment Encapsulation Test | encapsulation | 10 |
| 4 | Renter Test | encapsulation | 10 |
| 5 | Inventory Aggregation Test | aggregation | 10 |
| 6 | RentalLine Class Structure Test | kelas baru, association | 10 |
| 7 | RentalLine Subtotal Test | perilaku objek | 5 |
| 8 | Rental Class Structure Test | kelas baru, association | 10 |
| 9 | Rental Add Line Test | composition | 10 |
| 10 | Rental Total Test | penelusuran relasi | 10 |
| 11 | Rental Discount Test | logika bisnis | 5 |
| 12 | Clerk Test | dependency | 10 |
| | **Total** | | **100** |

## Aturan

- **Jangan mengubah** file apa pun di dalam `src/test/**` atau `.github/**`.
- Ikuti diagram kelas dengan tepat — nama kelas, nama field, nama method, dan signature dipakai autograder persis seperti yang digambar.
- Semua field harus `private` (ini diperiksa oleh tes).
- Gunakan array biasa saja — tanpa `List`, `ArrayList`, atau Collection lainnya.
