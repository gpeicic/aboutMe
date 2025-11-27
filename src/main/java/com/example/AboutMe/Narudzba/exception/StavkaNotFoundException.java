package com.example.AboutMe.Narudzba.exception;

public class StavkaNotFoundException  extends RuntimeException{
    public StavkaNotFoundException(Long id) {
        super("Stavka s id-om " + id + " nije pronađena.");
    }
}
