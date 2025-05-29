package com.example.AboutMe.Proizvod;

public class Boja {

    private Long id;

    private String naziv;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Boja(Long id, String naziv) {
        this.id = id;
        this.naziv = naziv;
    }
}
