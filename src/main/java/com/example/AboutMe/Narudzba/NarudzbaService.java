package com.example.AboutMe.Narudzba;

import com.example.AboutMe.Exception.NarudzbaNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
@Service
public class NarudzbaService {
    private final NarudzbaMapper narudzbaMapper;

    public NarudzbaService(NarudzbaMapper narudzbaMapper) {
        this.narudzbaMapper = narudzbaMapper;
    }

    @Transactional
    public void kreirajNarudzbu(Narudzba narudzba) {
        if (narudzba.getUkupnaCijena() == null) {
            narudzba.setUkupnaCijena(BigDecimal.ZERO);
        }
        narudzbaMapper.insertNarudzba(narudzba);
    }

    public Narudzba dohvatiAktivnuNarudzbuZaKorisnika(Long korisnikId) {
        Narudzba narudzba = narudzbaMapper.getNarudzbaByKorisnik(korisnikId);
        if (narudzba == null) {
            throw new NarudzbaNotFoundException(korisnikId);
        }
        return narudzba;
    }


    @Transactional
    public void azurirajUkupnuCijenu(Long narudzbaId, BigDecimal novaCijena) {
        Narudzba narudzba = narudzbaMapper.getNarudzbaById(narudzbaId);
        if (narudzba == null) {
            throw new NarudzbaNotFoundException(narudzbaId);
        }
        narudzbaMapper.updateUkupnaCijena(narudzbaId, novaCijena);
    }
}
