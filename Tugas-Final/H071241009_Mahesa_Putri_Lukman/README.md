# SkillSaga 🚀

SkillSaga adalah aplikasi pembelajaran interaktif dan kuis berbasis petualangan (RPG-style). Aplikasi ini dirancang untuk mengubah pengalaman belajar menjadi sebuah petualangan yang menyenangkan, di mana pengguna dapat memilih peran (Mage, Fighter, dll), membaca modul pembelajaran, dan menguji pengetahuan mereka melalui kuis untuk mendapatkan XP dan naik level.

---

## 📖 Deskripsi Aplikasi

SkillSaga adalah aplikasi belajar berbasis kuis dengan elemen ala dunia RPG. Aplikasi ini mengintegrasikan elemen gamifikasi (seperti *Player Level*, XP, dan *Titles*) untuk memotivasi pengguna dalam belajar dan mengerjakan kuis. Dilengkapi dengan kuis dari berbagai kategori yang ditarik secara *real-time* maupun mode *offline*, aplikasi ini memastikan proses belajar tidak pernah membosankan. Terdapat juga fitur kustomisasi tema (Dark/Light Mode) dan notifikasi pengingat belajar.

---

## 🎮 Cara Penggunaan

1. **Registrasi & Avatar:** Pengguna baru wajib mendaftar dan memilih avatar peran (contoh: *T-800 Tank*, *Mystic Mage*, dll) melalui *carousel* interaktif.
2. **Dashboard (Home):** Setelah login, pengguna akan melihat ringkasan status perjalanan mereka, termasuk Total Kuis, XP yang diraih, dan Modul yang telah dibaca.
3. **Membaca Modul (Learn):** Pengguna dapat mengakses berbagai modul pembelajaran. Progres membaca akan disimpan secara otomatis.
4. **Bermain Kuis (Quiz):** - Pilih kategori kuis (Sains, Matematika, Komputer, Kendaraan, dll).
    - Jawab pertanyaan pilihan ganda sebelum waktu habis (*15 detik per soal*).
    - Kumpulkan XP dan raih skor minimal 80 untuk membuka level kuis berikutnya.
5. **Riwayat (History):** Pengguna dapat meninjau kembali kuis apa saja yang sudah diselesaikan beserta XP yang didapat.
6. **Profil (Profile):** Pengguna dapat mengedit data diri, mengganti avatar/foto profil dengan foto dari galeri, menyalakan/mematikan Mode Gelap (*Dark Mode*), serta mengatur izin Notifikasi Sistem.

---

## 🛠️ Implementasi Teknis

SkillSaga dibangun menggunakan Java untuk Android dengan mengadopsi standar arsitektur dan komponen modern:

* **Arsitektur UI & Fragmentasi:** Menggunakan `BottomNavigationView` dan `Fragment` untuk navigasi dasbor yang mulus. Dikelompokkan menggunakan arsitektur *Package by Feature/Layer* (ui, adapter, model, database, network).
* **Local Storage (Penyimpanan Lokal):**
    - **SQLite Database:** Digunakan (`DatabaseHelper`) untuk menyimpan riwayat kuis secara persisten, akumulasi XP, dan sistem *unlock level*.
    - **SharedPreferences:** Digunakan untuk manajemen sesi *Login*, preferensi *Dark Mode*, saklar notifikasi, serta progres modul yang dibaca.
* **Networking & API:**
    - Menggunakan library **Retrofit** dan **Gson** untuk melakukan *fetching* data soal dari *Open Trivia DB API* secara *asynchronous*.
    - Mengimplementasikan sistem **Cache Lokal** (menyimpan JSON) sebagai *fallback* agar aplikasi tetap bisa dimainkan (dengan soal statis) saat tidak ada koneksi internet.
* **Sistem Notifikasi:** Memanfaatkan `NotificationManager` dan `NotificationChannel` dengan sistem *opt-in* (meminta izin pengguna di layar profil) untuk mengirimkan notifikasi saat pengguna naik level atau mencapai target tertentu.
* **User Interface & UX:**
    - Implementasi **Material Design 3**, termasuk warna indikator dinamis.
    - Implementasi `ViewPager2` untuk karosel pemilihan avatar.
    - Penanganan khusus untuk mode rotasi layar (*Screen Orientation Locked to Portrait*) pada saat kuis agar *timer* dan *layout* tidak rusak.

---