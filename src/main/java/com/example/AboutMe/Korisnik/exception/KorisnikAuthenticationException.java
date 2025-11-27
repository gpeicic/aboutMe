package com.example.AboutMe.Korisnik.exception;

public class KorisnikAuthenticationException extends RuntimeException {
    public KorisnikAuthenticationException() {
        super("Neispravan email ili lozinka.");
    }
}
