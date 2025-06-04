package com.example.AboutMe.Exception;

public class KorisnikAuthenticationException extends RuntimeException {
    public KorisnikAuthenticationException() {
        super("Neispravan email ili lozinka.");
    }
}
