package com.example.AboutMe.Proizvod;

import com.example.AboutMe.PodKategorija.PodKategorija;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ProizvodMapperIntegrationTest {

    @Autowired
    private ProizvodMapper proizvodMapper;

    @Test
    void testInsertAndFindById() {
        Proizvod novi = new Proizvod();
        novi.setIme("Test Proizvod");
        novi.setCijena(BigDecimal.valueOf(100.00));
        novi.setOpis("Opis test proizvoda");
        novi.setMarka("Test Marka");

        PodKategorija podkategorija = new PodKategorija();
        podkategorija.setId(1L);
        novi.setPodkategorija(podkategorija);

        proizvodMapper.insert(novi);
        assertThat(novi.getId()).isNotNull();

        Proizvod izBaze = proizvodMapper.findById(novi.getId());
        assertThat(izBaze).isNotNull();
        assertThat(izBaze.getIme()).isEqualTo("Test Proizvod");
    }

    @Test
    void testFindAll() {
        List<Proizvod> svi = proizvodMapper.findAll();
        assertThat(svi).isNotNull();
        assertThat(svi.size()).isGreaterThan(0);
    }

    @Test
    void testSearchBySubstring() {
        List<Proizvod> results = proizvodMapper.searchBySubstring("Test");
        assertThat(results).isNotNull();
    }


}
