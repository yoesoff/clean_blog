package com.example.blog.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * GlobalExceptionHandler
 * 
 * Kelas ini berfungsi untuk menangani (handle) semua exception yang terjadi
 * di semua controller dalam aplikasi secara TERPUSAT (centralized).
 * 
 * Tanpa kelas ini, setiap exception akan memberikan response yang default
 * dan tidak spesifik dari Spring.
 * 
 * Keuntungan menggunakan GlobalExceptionHandler:
 * 1. Konsistensi - Semua error response memiliki format yang sama
 * 2. Maintainability - Mudah mengubah format error di satu tempat
 * 3. Kejelasan - Response error lebih informatif dan spesifik
 */
@RestControllerAdvice // Annotation untuk menandakan ini adalah exception handler global
public class GlobalExceptionHandler {

    /**
     * handleValidationException()
     * 
     * Method ini menangani exception MethodArgumentNotValidException,
     * yang merupakan exception yang di-throw oleh Spring saat validasi gagal.
     * 
     * Kapan exception ini terjadi?
     * - Ketika request body memiliki validasi error (contoh: @NotBlank, @Size, dll)
     * - Dan ada @Valid annotation pada parameter di controller
     * 
     * @param ex - Exception yang berisi informasi tentang validasi error
     * @return - ResponseEntity berisi response JSON dengan detail error
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(
            MethodArgumentNotValidException ex) {
        
        // 1. MEMBUAT RESPONSE OBJECT
        // Map untuk menyimpan response yang akan dikirim ke client
        Map<String, Object> response = new HashMap<>();
        
        // Menambahkan timestamp (waktu saat error terjadi)
        // Ini berguna untuk logging dan tracking error
        response.put("timestamp", LocalDateTime.now());
        
        // Menambahkan HTTP status code (400 = Bad Request)
        // Bilangan bulat 400 lebih mudah dipahami oleh client dibanding enum
        response.put("status", HttpStatus.BAD_REQUEST.value());
        
        // Menambahkan pesan error umum
        response.put("error", "Validation Failed");
        
        // 2. MENGEKSTRAK DETAIL VALIDASI ERROR
        // Map untuk menyimpan error per field
        // Key = nama field (contoh: "title", "body")
        // Value = pesan error spesifik (contoh: "Title tidak boleh kosong")
        Map<String, String> errors = new HashMap<>();
        
        // getBindingResult() - Mengambil hasil binding/validasi dari Spring
        // getFieldErrors() - Mengambil semua field yang memiliki error validasi
        // forEach() - Melakukan iterasi untuk setiap error
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            // error.getField() - Ambil nama field yang error
            // error.getDefaultMessage() - Ambil pesan error dari annotation
            // Contoh: field="title", message="Title harus antara 5 hingga 100 karakter"
            errors.put(error.getField(), error.getDefaultMessage());
        });
        
        // Menambahkan map errors ke dalam response
        response.put("errors", errors);
        
        // 3. MENGEMBALIKAN RESPONSE
        // ResponseEntity - Kelas Spring yang membungkus response body dan HTTP status
        // HttpStatus.BAD_REQUEST - HTTP status code 400
        // 
        // Hasil akhir response JSON:
        // {
        //   "timestamp": "2026-03-19T06:28:10.123+07:00",
        //   "status": 400,
        //   "error": "Validation Failed",
        //   "errors": {
        //     "title": "Title harus antara 5 hingga 100 karakter",
        //     "body": "Body tidak boleh kosong"
        //   }
        // }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * handleEntityNotFoundException()
     * 
     * Method ini menangani exception ketika data tidak ditemukan di database.
     * 
     * Contoh kasus:
     * - User mencoba GET /articles/123 tapi article dengan id 123 tidak ada
     * - User mencoba DELETE article yang sudah dihapus sebelumnya
     * 
     * @param ex - Exception yang berisi pesan tentang entity yang tidak ditemukan
     * @return - ResponseEntity dengan HTTP status 404 (Not Found)
     */
    @ExceptionHandler(java.util.NoSuchElementException.class)
    public ResponseEntity<Map<String, Object>> handleEntityNotFoundException(
            java.util.NoSuchElementException ex) {
        
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.NOT_FOUND.value()); // 404
        response.put("error", "Entity Not Found");
        response.put("message", ex.getMessage() != null ? 
                ex.getMessage() : "Data yang dicari tidak ditemukan");
        
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * handleIllegalArgumentException()
     * 
     * Method ini menangani exception ketika argument yang diberikan tidak valid.
     * Berbeda dengan validation error (@Valid), ini adalah error logika bisnis.
     * 
     * Contoh kasus:
     * - Quantity tidak boleh negatif
     * - Status harus salah satu dari: DRAFT, PUBLISHED, ARCHIVED
     * 
     * @param ex - Exception yang berisi pesan tentang argument yang tidak valid
     * @return - ResponseEntity dengan HTTP status 400 (Bad Request)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(
            IllegalArgumentException ex) {
        
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value()); // 400
        response.put("error", "Invalid Argument");
        response.put("message", ex.getMessage());
        
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * handleGenericException()
     * 
     * Method ini adalah CATCH-ALL exception handler.
     * Ini menangani semua exception yang tidak di-handle oleh @ExceptionHandler lain.
     * 
     * Digunakan untuk:
     * - Exception yang tidak terduga (unexpected error)
     * - Database error
     * - Network error
     * - Internal server error
     * 
     * Tips: Jangan expose detail error sensitif ke client, log ke server saja.
     * 
     * @param ex - Exception apa saja
     * @return - ResponseEntity dengan HTTP status 500 (Internal Server Error)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(
            Exception ex) {
        
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value()); // 500
        response.put("error", "Internal Server Error");
        // Jangan kirim detail error ke client untuk alasan security
        response.put("message", "Terjadi kesalahan pada server. Silakan coba lagi.");
        
        // Log actual error ke server (untuk debugging)
        // logger.error("Unexpected error occurred", ex);
        
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

