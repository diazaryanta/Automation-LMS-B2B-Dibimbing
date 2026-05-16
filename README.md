# 🚀 LMS B2B (Dibimbing) Test Automation

Proyek ini merupakan framework automasi pengujian untuk platform **[LMS B2B (Dibimbing)](https://lms-b2b.do.dibimbing.id/dibimbingqa/login)**. Framework ini mencakup pengujian pada level **UI (Frontend)** menggunakan Selenium dan **API (Backend)** menggunakan RestAssured, yang diintegrasikan dengan **CI/CD** dan notifikasi **Slack**.

Proyek ini dikerjakan untuk memenuhi syarat **assignment final project Dibimbing**. Silakan klik link berikut untuk melihat **[QA Report (Documentation)](https://docs.google.com/document/d/1LLAV7yfcufok-lBCGgFqZS-t1FLD2MKLaV1-k49AUqg/edit?usp=sharing)**.

---

## 🛠️ Teknologi & Library yang Digunakan

* **Bahasa Pemrograman:** Java 21
* **Build Tool:** Gradle
* **Testing Framework:** TestNG
* **UI Automation:** Selenium WebDriver
* **API Automation:** RestAssured (GraphQL Support)
* **Reporting:** ExtentReports (Spark Reporter)
* **CI/CD:** GitHub Actions
* **Communication:** Slack Integration (via Incoming Webhooks)

---

## 📁 Struktur Proyek (Page Object Model)

Proyek ini dibangun menggunakan konsep **Page Object Model (POM)** untuk memisahkan antara logika pengujian, elemen halaman (*locators*), dan setup framework agar lebih modular.

```text
Automation-LMS-B2B-Dibimbing/
├── .github/workflows/       # Konfigurasi GitHub Actions (CI/CD Pipeline)
├── reports/                 # Folder hasil laporan otomatis (HTML & Screenshots)
│   ├── API_Report.html      # Laporan lengkap hasil testing API
│   └── UI_Report.html       # Laporan lengkap hasil testing UI
├── src/test/java/
│   ├── API/                 # Arsitektur API Testing
│   │   ├── base/            # Setup dasar API & reporting
│   │   ├── client/          # GraphQL Client wrapper
│   │   ├── services/        # Service layer (Auth, Employee, ProgramStudi)
│   │   └── tests/           # Skenario pengujian API (Login, Create, Edit, dll)
│   ├── UI/                  # Arsitektur UI Testing
│   │   ├── base/            # Setup dasar UI & screenshot logic
│   │   ├── config/          # Driver Manager (Chrome Headless setup)
│   │   ├── pages/           # Page Object Model (LoginPage, EmployeePage, dll)
│   │   └── tests/           # Skenario pengujian UI (Login, ProgramStudi, dll)
│   └── utils/               # Helper classes (ConfigReader, ExtentManager)
├── src/test/resources/
│   ├── config/              # File konfigurasi (.properties) untuk Staging
│   ├── graphql/             # Payload query/mutation GraphQL (.graphql)
│   └── suites/              # File XML TestNG untuk batch running
├── build.gradle             # Manajemen dependencies (RestAssured, Selenium, dll)
└── README.md                # Dokumentasi panduan proyek
```
---

## 🧪 Scenario Test
**UI TESTING**

| **Fitur**             |    **Status**    |                                                              **Catatan**                                                              |
|:----------------------|:----------------:|:-------------------------------------------------------------------------------------------------------------------------------------:|
| Login                 |        ✅         |                                                Berhasil autentikasi dengan akun Admin                                                 |
| Add New Employee      |        ✅         | Berhasil membuat data baru; pengujian mencakup validasi mandatory field, duplikasi email, dan edge cases (emoticon/spesial karakter). |
| Search Employee       |        ✅         |                      Tabel berhasil menampilkan hasil pencarian secara dinamis berdasarkan keyword (Nama/Email).                      |
| Edit Employee         |        ✅         |                             Perubahan data karyawan berhasil disimpan dan langsung update di tabel utama.                             |
| Filter by Angkatan    |        ✅         |                  Pilihan filter berfungsi dengan baik untuk memunculkan data karyawan sesuai angkatan yang dipilih.                   |
| Assigned Program      |        ✅         |                                         Menampilkan detail employee dari tab Assigned Program                                         |
| Activate / Inactivate |        ✅         |                   Tombol ganti status berfungsi, dan tulisan statusnya (Aktif/Tidak Aktif) juga ikut berubah warna.                   |
| Resend Email Acc      |        ✅         |                           Berhasil kirim ulang email, dan langsung muncul notifikasi sukses di pojok layar.                           |
| Action Download       |        ✅         |                           File Excel/CSV berhasil ter- download dengan aman sampai masuk ke folder laptop.                            |
| Delete Employee       |        ✅         |                                       Menghapus data employee dari database dan tabel employee                                        |
| **Total Skenario:**   | **10 ✅<br/>0 ❌** |                                               **Cakupan: Modul Login & Employee List**                                                |

**API TESTING**

| **Fitur**            |   **Status**    |                                          **Catatan**                                           |
|:---------------------|:---------------:|:----------------------------------------------------------------------------------------------:|
| Login                |        ✅        |      Berhasil autentikasi dengan akun Admin, termasuk validasi error pada negative test.       |
| Add Program Studi    |        ✅        |                  Berhasil menambahkan nama Program Studi baru ke dalam tabel.                  |
| Search Program Studi |        ✅        |           Fitur pencarian khusus untuk Program Studi berjalan dengan tepat sasaran.            |
| Edit Program Studi   |        ✅        |             Data Program Studi berhasil diubah, disimpan, dan langsung ter-update.             |
| Export CSV           |        ✅        | File CSV berisi daftar Program Studi sukses ter-download hingga sampai masuk ke folder laptop. |
| Delete Program Studi |        ✅        |              Menghapus data program studi dari database dan tabel program studi.               |
| **Total Skenario:**   | **6 ✅<br/>0 ❌** |                            **Cakupan: Modul Login & Program Studi**                            |
---
## ⚙️ Persiapan Awal *(Prerequisites)*

Sebelum mulai nge-run proyek ini, pastikan sudah menginstall *tools* di bawah ini:
1.  **Java Development Kit (JDK):** Versi 17, atau 21.
2.  **IDE:** IntelliJ IDEA.
3.  **Koneksi Internet:** Wajib ada, karena pengetesan langsung ke server *live* LMS B2B Dibimbing.
---

## 📥 Langkah Instalasi ***(Installation Steps)***
Ikuti langkah-langkah di bawah ini untuk mengunduh dan mengatur proyek di komputer

1. **Clone Repositori:** Buka Terminal/Command Prompt, arahkan ke folder tujuanmu, lalu jalankan perintah ini:
```text 
git clone https://github.com/diazaryanta/Automation-LMS-B2B-Dibimbing.git
```
2. **Buka Proyek di IntelliJ IDEA:**
* Buka IntelliJ IDEA.
* Pilih menu **Open.**
* Cari dan pilih folder `automation-lms-b2b-dibimbing` yang baru saja di-clone.

3. **Sinkronisasi Gradle (Download Dependencies):**
* Saat proyek terbuka, IntelliJ biasanya akan otomatis mendeteksi file `build.gradle` dan mulai mengunduh semua library (Selenium, RestAssured, TestNG, dll).
* Jika tidak otomatis, klik kanan pada file `build.gradle` dan pilih **Reload Gradle Project**.

4. **Konfigurasi Kredensial:**
* Buka file `src/test/resources/config/staging.properties`
* Pastikan URL, Email, dan Password untuk akun LMS B2B sudah terisi dengan benar.
``` Text
baseUrl=https://lms-b2b.do.dibimbing.id/dibimbingqa/login
email=arwendymelyn@dibimbing.id
password=s2et9bh6l
```
---

## 🚀 Cara Menjalankan Test ***(How to Run)***

### Opsi 1: Lewat IntelliJ IDEA
1. Buka file skenario test di folder `src/test/java/UI/tests/` atau `API/tests/`.
2. Cari ikon **Play (Segitiga Hijau)** di sebelah kiri tulisan nama *class* atau *method*.
3. Hasil pengujian akan muncul di tab Console/Run dan laporan HTML akan diperbarui di folder `reports/`.

### Opsi 2: Lewat Terminal (Pake Gradle)
Kalau mau nge-run semua test sekaligus lewat file konfigurasi XML:

```bash
# Menjalankan keseluruhan test (API & UI)
./gradlew clean test

# Menjalankan spesifik Suite API
./gradlew test -DsuiteXmlFile=src/test/resources/suites/Regression_API_Test.xml

# Menjalankan spesifik Suite UI
./gradlew test -DsuiteXmlFile=src/test/resources/suites/Regression_UI_Test.xml
```
---
## 🧠 Fitur Utama & Keunggulan Framework

* ***GraphQL Integration*:** Framework ini sudah mendukung pengujian API berbasis GraphQL menggunakan file `.graphql` terpisah untuk menjaga kebersihan kode.
* ***Headless Execution*:** Mendukung mode Headless (tanpa tampilan browser) di GitHub Actions agar pipeline berjalan lebih cepat dan efisien.
* ***Automatic Screenshot*:** Untuk pengujian UI, sistem akan otomatis mengambil screenshot jika terjadi kegagalan (Failure) dan melampirkannya ke dalam Extent Report.
* ***Centralized Configuration*:** Kredensial dan URL dikelola dalam satu file `staging.properties` untuk memudahkan maintenance.
* ***CI/CD & Slack Notification*:** Setiap perubahan yang di-push ke GitHub akan memicu pengetesan otomatis. Hasil akhirnya (Success/Fail) akan langsung dikirim ke channel Slack channel QA.
---
## 📈 Cara Lihat Report
Setelah test selesai dijalankan lewat Terminal, sistem akan otomatis bikin laporan yang rapi.
* **UI Report:** `reports/UI_Report.html`.
* **API Report:**`reports/API_Report.html`.
* **GitHub Artifacts:** Bisa juga dapat men*download* laporan ini dari tab **Actions** di repositori GitHub setelah pipeline selesai.
---
## 👨🏻‍💻 Tester
**Diaz**