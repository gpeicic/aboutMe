package com.example.AboutMe.Narudzba;

import jakarta.persistence.criteria.CriteriaBuilder;

import java.math.BigDecimal;

import java.time.LocalDateTime;

public class Narudzba {
    private Integer id;
    private Integer korisnik_id;
    private BigDecimal ukupnaCijena = BigDecimal.ZERO;
    private LocalDateTime datum_narudzbe;
    private Integer status;

    public Narudzba() {
    }

    public Narudzba(Integer id, Integer korisnik_id, LocalDateTime datum_narudzbe, BigDecimal ukupnaCijena, Integer status) {
        this.id = id;
        this.korisnik_id = korisnik_id;
        this.ukupnaCijena = ukupnaCijena;
        this.datum_narudzbe = datum_narudzbe;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getKorisnik_id() {
        return korisnik_id;
    }

    public void setKorisnik_id(Integer korisnik_id) {
        this.korisnik_id = korisnik_id;
    }

    public BigDecimal getUkupnaCijena() {
        return ukupnaCijena;
    }

    public void setUkupnaCijena(BigDecimal ukupnaCijena) {
        this.ukupnaCijena = ukupnaCijena;
    }

    public LocalDateTime getDatum_narudzbe() {
        return datum_narudzbe;
    }

    public void setDatum_narudzbe(LocalDateTime datum_narudzbe) {
        this.datum_narudzbe = datum_narudzbe;
    }
}
