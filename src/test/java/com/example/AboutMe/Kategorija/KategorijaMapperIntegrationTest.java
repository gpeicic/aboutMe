package com.example.AboutMe.Kategorija;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class KategorijaMapperIntegrationTest {

    @Autowired
    private KategorijaMapper kategorijaMapper;

    @Test
    void testInsertAndGetAll() {
        Kategorija nova = new Kategorija();
        nova.setNaziv("Test Kategorija");
        nova.setSpol("Muskarci");

        kategorijaMapper.insert(nova);
        assertNotNull(nova.getId(), "ID should be generated after insert");

        List<Kategorija> sve = kategorijaMapper.getAll();
        assertFalse(sve.isEmpty(), "Lista kategorija ne smije biti prazna");

        boolean found = sve.stream().anyMatch(k -> k.getId().equals(nova.getId()));
        assertTrue(found, "U listi mora biti ubačena kategorija");
    }

    @Test
    void testGetById() {
        Kategorija nova = new Kategorija();
        nova.setNaziv("Test Kategorija");
        nova.setSpol("Muskarci");

        kategorijaMapper.insert(nova);
        Long id = nova.getId();

        List<Kategorija> result = kategorijaMapper.getById(id);
        assertFalse(result.isEmpty(), "Rezultat ne smije biti prazan");
        assertEquals(id, result.get(0).getId());
    }

    @Test
    void testUpdate() {
        Kategorija nova = new Kategorija();
        nova.setNaziv("Stara Kategorija");
        nova.setSpol("Muskarci");

        kategorijaMapper.insert(nova);
        Long id = nova.getId();

        nova.setNaziv("Nova Kategorija");
        kategorijaMapper.update(nova);

        List<Kategorija> result = kategorijaMapper.getById(id);
        assertEquals("Nova Kategorija", result.get(0).getNaziv());
    }

    @Test
    void testDelete() {
        Kategorija nova = new Kategorija();
        nova.setNaziv("Za brisanje");
        nova.setSpol("Zene");

        kategorijaMapper.insert(nova);
        Long id = nova.getId();

        kategorijaMapper.delete(id);

        List<Kategorija> result = kategorijaMapper.getById(id);
        assertTrue(result.isEmpty(), "Nakon brisanja, lista mora biti prazna");
    }
}