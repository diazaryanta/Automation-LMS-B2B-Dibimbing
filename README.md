# 🚀 LMS B2B (Dibimbing) Test Automation

Proyek ini merupakan framework automasi pengujian untuk platform **[LMS B2B (Dibimbing)](https://lms-b2b.do.dibimbing.id/dibimbingqa/login)**. Framework ini mencakup pengujian pada level **UI (Frontend)** menggunakan Selenium dan **API (Backend)** menggunakan RestAssured, yang diintegrasikan dengan **CI/CD** dan notifikasi **Slack**.

Proyek ini dikerjakan untuk memenuhi syarat **assignment mini project dibimbing day 30**. Silakan klik link berikut untuk melihat **[QA Report (Documentation)](https://docs.google.com/document/d/1LLAV7yfcufok-lBCGgFqZS-t1FLD2MKLaV1-k49AUqg/edit?usp=sharing)**.

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

Proyek ini dibangun menggunakan konsep **Page Object Model (POM)** untuk memisahkan antara logika pengujian, elemen halaman (locators), dan setup framework agar lebih modular.

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
## ⚙️ Persiapan Awal (Prerequisites)

Sebelum mulai nge-run proyek ini, pastikan sudah menginstall *tools* di bawah ini:
1.  **Java Development Kit (JDK):** Versi 17, atau 21.
2.  **IDE:** IntelliJ IDEA.
3.  **Koneksi Internet:** Wajib ada, karena pengetesan langsung ke server *live* LMS B2B Dibimbing.
---
## 🚀 Cara Menjalankan Test

### Opsi 1: Lewat IntelliJ IDEA
1. Buka file skenario test di folder `src/test/java/UI/tests/` atau `API/tests/`.
2. Cari ikon **Play (Segitiga Hijau)** di sebelah kiri tulisan nama *class* atau *method*.
3. Klik ikon tersebut lalu pilih **Run 'TicketTest...'**.
4. Hasil pengujian akan muncul di tab Console/Run dan laporan HTML akan diperbarui di folder `reports/`.

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

* ***GraphQL Integration*:** Framework ini sudah mendukung pengujian API berbasis GraphQL menggunakan file `.graphql` terpisah untuk menjaga kebersihan kode..
* ***Headless Execution*:** Mendukung mode Headless (tanpa tampilan browser) di GitHub Actions agar pipeline berjalan lebih cepat dan efisien.
* ***Automatic Screenshot*:** Untuk pengujian UI, sistem akan otomatis mengambil screenshot jika terjadi kegagalan (Failure) dan melampirkannya ke dalam Extent Report.
* ***Centralized Configuration*:** Kredensial dan URL dikelola dalam satu file `staging.properties` untuk memudahkan maintenance.
* ***CI/CD & Slack Notification*:** Setiap perubahan yang di-push ke GitHub akan memicu pengetesan otomatis. Hasil akhirnya (Success/Fail) akan langsung dikirim ke channel Slack tim QA.
---
## 📈 Cara Lihat Report
Setelah test selesai dijalankan lewat Terminal, sistem akan otomatis bikin laporan yang rapi.
* **UI Report:** `reports/UI_Report.html`.
* **API Report:**`reports/API_Report.html`.
* **GitHub Artifacts:** Bisa juga dapat men*download* laporan ini dari tab **Actions** di repositori GitHub setelah pipeline selesai.
---
## 👤 Tester
* **Name**: Diaz
* **Role**: QA Automation Engineer