package com.example.AboutMe.Narudzba;

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
        // Postavi ukupnu cijenu na 0 ako nije definirana
        if (narudzba.getUkupnaCijena() == null) {
            narudzba.setUkupnaCijena(BigDecimal.ZERO);
        }
        narudzbaMapper.insertNarudzba(narudzba);
    }

    public Narudzba dohvatiAktivnuNarudzbuZaKorisnika(Long korisnikId) {
        return narudzbaMapper.getNarudzbaByKorisnik(korisnikId);
    }

    @Transactional
    public void azurirajUkupnuCijenu(Long narudzbaId, BigDecimal novaCijena) {
        narudzbaMapper.updateUkupnaCijena(narudzbaId, novaCijena);
    }
}
