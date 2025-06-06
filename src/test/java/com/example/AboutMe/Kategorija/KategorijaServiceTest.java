package com.example.AboutMe.Kategorija;

import com.example.AboutMe.Exception.KategorijaNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KategorijaServiceTest {

    private KategorijaMapper kategorijaMapper;
    private KategorijaService kategorijaService;

    @BeforeEach
    void setUp() {
        kategorijaMapper = mock(KategorijaMapper.class);
        kategorijaService = new KategorijaService(kategorijaMapper);
    }

    @Test
    void testGetAll_ReturnsKategorije() {
        Kategorija kat1 = new Kategorija(1L, "Odjeća","Muškarci");
        Kategorija kat2 = new Kategorija(2L, "Obuća","Zene");


        when(kategorijaMapper.getAll()).thenReturn(Arrays.asList(kat1, kat2));

        List<Kategorija> result = kategorijaService.getAll();

        assertEquals(2, result.size());
        assertEquals("Odjeća", result.get(0).getNaziv());
        verify(kategorijaMapper, times(1)).getAll();
    }

    @Test
    void testGetAll_ThrowsExceptionIfEmpty() {
        when(kategorijaMapper.getAll()).thenReturn(Collections.emptyList());

        assertThrows(KategorijaNotFoundException.class, () -> kategorijaService.getAll());

        verify(kategorijaMapper, times(1)).getAll();
    }

    @Test
    void testGetKategorijeBySpolId_ReturnsKategorije() {
        Long spolId = 1L;
        Kategorija kat1 = new Kategorija(1L, "Odjeća","Muškarci");

        when(kategorijaMapper.getById(spolId)).thenReturn(List.of(kat1));

        List<Kategorija> result = kategorijaService.getKategorijeBySpolId(spolId);

        assertEquals(1, result.size());
        assertEquals("Odjeća", result.get(0).getNaziv());
        verify(kategorijaMapper, times(1)).getById(spolId);
    }

    @Test
    void testGetKategorijeBySpolId_ThrowsExceptionIfEmpty() {
        Long spolId = 2L;
        when(kategorijaMapper.getById(spolId)).thenReturn(Collections.emptyList());

        assertThrows(KategorijaNotFoundException.class, () -> kategorijaService.getKategorijeBySpolId(spolId));

        verify(kategorijaMapper, times(1)).getById(spolId);
    }

    @Test
    void testAddKategorija_CallsMapperInsert() {
        Kategorija nova = new Kategorija(3L, "Obuca","Djeca");

        kategorijaService.addKategorija(nova);

        verify(kategorijaMapper, times(1)).insert(nova);
    }
}