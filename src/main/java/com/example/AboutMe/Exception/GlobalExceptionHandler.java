package com.example.AboutMe.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(KategorijaNotFoundException.class)
    public ResponseEntity<String> handleKategorijaNotFound(KategorijaNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ProizvodNotFoundException.class)
    public ResponseEntity<String> handleProizvodNotFound(ProizvodNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(PodKategorijaNotFoundException.class)
    public ResponseEntity<String> handlePodKategorijaNotFound(PodKategorijaNotFoundException ex) {
        // Vrati 404 i poruku
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(KorisnikNotFoundException.class)
    public ResponseEntity<String> handleNotFound(KorisnikNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(StavkaNotFoundException.class)
    public ResponseEntity<String> handleStavkaNotFound(StavkaNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(KorisnikAuthenticationException.class)
    public ResponseEntity<String> handleAuthentication(KorisnikAuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
    @ExceptionHandler(NarudzbaNotFoundException.class)
    public ResponseEntity<String> handleNarudzbaNotFound(NarudzbaNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        // Za sve druge neočekivane greške vrati 500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Došlo je do greške: " + ex.getMessage());
    }
}