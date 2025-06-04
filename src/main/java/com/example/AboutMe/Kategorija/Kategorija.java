package com.example.AboutMe.Kategorija;

public class Kategorija {
    private Long id;
    private String naziv;
    private String spol;

    public Kategorija(Long id, String naziv, String spol) {
        this.id = id;
        this.naziv = naziv;
        this.spol = spol;
    }

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

    public String getSpol() {
        return spol;
    }

    public void setSpol(String spol) {
        this.spol = spol;
    }
}
