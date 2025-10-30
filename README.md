## Cara Menjalankan

### 1. Persiapan

Pastikan sudah menginstal:

- **Java 11+**
- **Maven 3+**

Clone repo dan buka terminal di folder proyek.

### 2. Jalankan Server

Jalankan perintah berikut:

```bash
mvn spring-boot:run
```

Server akan berjalan di `http://localhost:8000`.

### 3. Endpoint Utama

| Method | Endpoint      | Deskripsi                                      |
| ------ | ------------- | ---------------------------------------------- |
| GET    | `/notes`      | Ambil semua catatan                            |
| POST   | `/notes`      | Tambah catatan baru (JSON: `title`, `content`) |
| DELETE | `/notes/{id}` | Hapus catatan berdasarkan ID                   |

Untuk dokumentasi API lengkap tersedia di file `postman.json`.

# Backend Preview Postman

untuk gambar bisa diakses via direktori PREVIEW

Get All notes
![GET All Notes.png](https://github.com/puxxbu/astrapay-spring-boot-external/blob/master/PREVIEW/GET%20All%20Notes.png?raw=true)

POST Note
![Post Note.png](https://github.com/puxxbu/astrapay-spring-boot-external/blob/master/PREVIEW/Post%20Note.png?raw=true)

POST Note Validation
![POST notes Validation.png](https://github.com/puxxbu/astrapay-spring-boot-external/blob/master/PREVIEW/POST%20notes%20Validation.png?raw=true)

DELETE Note
![DELETE Note by id.png](https://github.com/puxxbu/astrapay-spring-boot-external/blob/master/PREVIEW/DELETE%20Note%20by%20id.png?raw=true)

DELETE Note Validation
![DELETE Note not found.png](https://github.com/puxxbu/astrapay-spring-boot-external/blob/master/PREVIEW/DELETE%20Note%20not%20found.png?raw=true)
