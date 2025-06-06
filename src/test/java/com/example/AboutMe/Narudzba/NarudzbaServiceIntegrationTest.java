package com.example.AboutMe.Narudzba;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class NarudzbaServiceIntegrationTest {

    @Autowired
    private NarudzbaService narudzbaService;

    @Autowired
    private NarudzbaMapper narudzbaMapper;

    @Test
    void kreirajINadopuniNarudzbu() {
        Narudzba narudzba = new Narudzba();
        narudzba.setKorisnik_id(12);
        narudzba.setDatum_narudzbe(LocalDateTime.now());
        narudzba.setUkupnaCijena(BigDecimal.valueOf(100));

        narudzbaService.kreirajNarudzbu(narudzba);

        assertThat(narudzba.getId()).isNotNull();

        narudzbaService.azurirajUkupnuCijenu(narudzba.getId().longValue(), BigDecimal.valueOf(250));

        Narudzba result = narudzbaMapper.getNarudzbaById(narudzba.getId().longValue());

        assertThat(result).isNotNull();
        assertThat(result.getUkupnaCijena()).isEqualByComparingTo("250.00");
        assertThat(result.getKorisnik_id()).isEqualTo(12);
    }
}
