package com.example.AboutMe.Proizvod;

import com.example.AboutMe.Exception.ProizvodNotFoundException;
import com.example.AboutMe.PodKategorija.PodKategorija;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ProizvodServiceIntegrationTest {

    @Autowired
    private ProizvodService proizvodService;


    private Proizvod testProizvod() {
        Proizvod proizvod = new Proizvod();
        proizvod.setIme("Testni Proizvod");
        proizvod.setCijena(BigDecimal.valueOf(123.45));
        proizvod.setOpis("Opis za test");
        proizvod.setMarka("Test Marka");

        PodKategorija pk = new PodKategorija();
        pk.setId(1L);
        proizvod.setPodkategorija(pk);

        return proizvod;
    }

    @Test
    @Order(1)
    @Rollback
    void testInsertAndGetById() {
        Proizvod proizvod = testProizvod();
        proizvodService.insert(proizvod);

        List<Proizvod> svi = proizvodService.getAll();
        assertThat(svi).isNotEmpty();

        Proizvod inserted = svi.stream()
                .filter(p -> p.getIme().equals("Testni Proizvod"))
                .findFirst()
                .orElse(null);

        assertThat(inserted).isNotNull();
        assertThat(inserted.getOpis()).isEqualTo("Opis za test");
    }

    @Test
    @Order(2)
    @Rollback
    void testUpdate() {
        Proizvod proizvod = testProizvod();
        proizvodService.insert(proizvod);

        Proizvod original = proizvodService.getAll().stream()
                .filter(p -> p.getIme().equals("Testni Proizvod"))
                .findFirst()
                .orElseThrow();

        original.setOpis("Ažuriran opis");
        proizvodService.update(original);

        Proizvod updated = proizvodService.getById(original.getId());
        assertThat(updated.getOpis()).isEqualTo("Ažuriran opis");
    }

    @Test
    @Order(3)
    @Rollback
    void testDelete() {
        Proizvod proizvod = testProizvod();
        proizvodService.insert(proizvod);

        Proizvod inserted = proizvodService.getAll().stream()
                .filter(p -> p.getIme().equals("Testni Proizvod"))
                .findFirst()
                .orElseThrow();

        proizvodService.delete(inserted.getId());

        Assertions.assertThrows(ProizvodNotFoundException.class, () -> {
            proizvodService.getById(inserted.getId());
        });
    }
}
