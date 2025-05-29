package com.example.AboutMe.Proizvod;

import com.example.AboutMe.PodKategorija.PodKategorija;

import java.math.BigDecimal;

public class Proizvod {

    private Long id;
    private String ime;
    private BigDecimal cijena;
    private String opis;
    private String marka;
    private PodKategorija podkategorija;


    public Proizvod(Long id, String ime, BigDecimal cijena, String marka, String opis, PodKategorija podkategorija) {
        this.id = id;
        this.ime = ime;
        this.cijena = cijena;
        this.opis = opis;
        this.marka = marka;
        this.podkategorija = podkategorija;

    }

    public Proizvod() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public BigDecimal getCijena() {
        return cijena;
    }

    public void setCijena(BigDecimal cijena) {
        this.cijena = cijena;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public PodKategorija getPodkategorija() {
        return podkategorija;
    }

    public void setPodkategorija(PodKategorija podkategorija) {
        this.podkategorija = podkategorija;
    }

}
