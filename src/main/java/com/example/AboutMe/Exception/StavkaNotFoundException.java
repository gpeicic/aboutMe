package com.example.AboutMe.Exception;

public class StavkaNotFoundException  extends RuntimeException{
    public StavkaNotFoundException(Long id) {
        super("Stavka s id-om " + id + " nije pronađena.");
    }
}
