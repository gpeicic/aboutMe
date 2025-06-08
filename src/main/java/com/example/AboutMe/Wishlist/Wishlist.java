package com.example.AboutMe.Wishlist;

public class Wishlist {
    private Long id;
    private Long korisnik_id;
    private Long proizvod_id;

    public Wishlist(Long id, Long korisnik_id, Long proizvod_id) {
        this.id = id;
        this.korisnik_id = korisnik_id;
        this.proizvod_id = proizvod_id;
    }

    public Wishlist() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getKorisnik_id() {
        return korisnik_id;
    }

    public void setKorisnik_id(Long korisnik_id) {
        this.korisnik_id = korisnik_id;
    }

    public Long getProizvod_id() {
        return proizvod_id;
    }

    public void setProizvod_id(Long proizvod_id) {
        this.proizvod_id = proizvod_id;
    }
}
