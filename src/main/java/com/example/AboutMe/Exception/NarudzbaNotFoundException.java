package com.example.AboutMe.Exception;

public class NarudzbaNotFoundException extends RuntimeException{
    public NarudzbaNotFoundException(Long id) {
        super("Narudzba s ID-jem " + id + " nije pronađena.");
    }
}
