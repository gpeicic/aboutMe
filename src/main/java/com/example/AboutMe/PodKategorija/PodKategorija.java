package com.example.AboutMe.PodKategorija;

import com.example.AboutMe.Kategorija.Kategorija;

public class PodKategorija {

    private Long id;
    private String naziv;
    private String tip_podkategorije;
    private Kategorija kategorija;

    public PodKategorija(){}

    public PodKategorija(Long id, String naziv, String tip_podkategorije, Kategorija kategorija) {
        this.id = id;
        this.naziv = naziv;
        this.tip_podkategorije = tip_podkategorije;
        this.kategorija = kategorija;
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

    public String getTip_podkategorije() {
        return tip_podkategorije;
    }

    public void setTip_podkategorije(String tip_podkategorije) {
        this.tip_podkategorije = tip_podkategorije;
    }

    public Kategorija getKategorija() {
        return kategorija;
    }

    public void setKategorija(Kategorija kategorija) {
        this.kategorija = kategorija;
    }
}
