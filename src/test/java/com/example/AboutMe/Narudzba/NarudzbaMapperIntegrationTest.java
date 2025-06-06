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
public class NarudzbaMapperIntegrationTest {

    @Autowired
    private NarudzbaMapper narudzbaMapper;

    @Test
    public void testInsertAndUpdateNarudzba() {
        Narudzba narudzba = new Narudzba();
        narudzba.setKorisnik_id(12);
        narudzba.setDatum_narudzbe(LocalDateTime.now());
        narudzba.setUkupnaCijena(BigDecimal.valueOf(123.45));


        narudzbaMapper.insertNarudzba(narudzba);
        assertThat(narudzba.getId()).isNotNull();


        narudzbaMapper.updateUkupnaCijena(narudzba.getId().longValue(), BigDecimal.valueOf(250.00));

        // Assert
        Narudzba fetched = narudzbaMapper.getNarudzbaById(narudzba.getId().longValue());

        assertThat(fetched).isNotNull();
        assertThat(fetched.getUkupnaCijena()).isEqualByComparingTo("250.00");
        assertThat(fetched.getKorisnik_id()).isEqualTo(12L);
    }

    @Test
    public void testGetNarudzbaByKorisnik() {
        // Arrange
        Narudzba narudzba = new Narudzba();
        narudzba.setKorisnik_id(12);
        narudzba.setDatum_narudzbe(LocalDateTime.now());
        narudzba.setUkupnaCijena(BigDecimal.valueOf(10.00));

        // Insert
        narudzbaMapper.insertNarudzba(narudzba);

        // Act
        Narudzba fetched = narudzbaMapper.getNarudzbaByKorisnik(12L);

        // Assert
        assertThat(fetched).isNotNull();
        assertThat(fetched.getKorisnik_id()).isEqualTo(12);
        assertThat(fetched.getUkupnaCijena()).isEqualByComparingTo("89.70");
    }
}
