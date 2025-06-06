package com.example.AboutMe.Narudzba;

public class ListaNarudzba {
    private Long id;
    private Long narudzba_id;
    private Long proizvod_id;
    private Long kolicina;



    public ListaNarudzba(Long id, Long narudzba_id, Long proizvod_id, Long kolicina) {
        this.id = id;
        this.narudzba_id = narudzba_id;
        this.proizvod_id = proizvod_id;
        this.kolicina = kolicina;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNarudzba_id() {
        return narudzba_id;
    }

    public void setNarudzba_id(Long narudzba_id) {
        this.narudzba_id = narudzba_id;
    }

    public Long getProizvod_id() {
        return proizvod_id;
    }

    public void setProizvod_id(Long proizvod_id) {
        this.proizvod_id = proizvod_id;
    }

    public Long getKolicina() {
        return kolicina;
    }

    public void setKolicina(Long kolicina) {
        this.kolicina = kolicina;
    }
}
