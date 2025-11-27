package com.example.AboutMe.Korisnik.exception;

public class KorisnikNotFoundException extends RuntimeException {
    public KorisnikNotFoundException(Integer id) {
        super("Korisnik s ID-jem " + id + " nije pronađen.");
    }
}

