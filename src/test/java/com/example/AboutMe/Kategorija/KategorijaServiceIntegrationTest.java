package com.example.AboutMe.Kategorija;

import com.example.AboutMe.Exception.KategorijaNotFoundException;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
public class KategorijaServiceIntegrationTest {

    @Autowired
    private KategorijaService kategorijaService;

    @Test
    void testAddAndGetAll() {
        Kategorija nova = new Kategorija(1L,"Test Kategorija","Muskarci");


        kategorijaService.addKategorija(nova);

        List<Kategorija> sve = kategorijaService.getAll();
        assertFalse(sve.isEmpty());
        assertTrue(sve.stream().anyMatch(k -> "Test Kategorija".equals(k.getNaziv())));
    }

    @Test
    void testGetBySpolIdThrowsIfEmpty() {
        assertThrows(KategorijaNotFoundException.class, () -> {
            kategorijaService.getKategorijeBySpolId(999L);
        });
    }
}
