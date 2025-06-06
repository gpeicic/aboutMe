package com.example.AboutMe.Proizvod;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.AboutMe.Exception.ProizvodNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

class ProizvodServiceTest {

    @Mock
    private ProizvodMapper proizvodMapper;

    @InjectMocks
    private ProizvodService proizvodService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll_ReturnsList_WhenNotEmpty() {
        List<Proizvod> proizvodi = List.of(new Proizvod(), new Proizvod());
        when(proizvodMapper.findAll()).thenReturn(proizvodi);

        List<Proizvod> result = proizvodService.getAll();

        assertEquals(2, result.size());
        verify(proizvodMapper).findAll();
    }

    @Test
    void getAll_ThrowsException_WhenEmpty() {
        when(proizvodMapper.findAll()).thenReturn(Collections.emptyList());

        ProizvodNotFoundException ex = assertThrows(ProizvodNotFoundException.class,
                () -> proizvodService.getAll());

        assertEquals("Nema dostupnih proizvoda.", ex.getMessage());
    }

    @Test
    void getById_ReturnsProizvod_WhenFound() {
        Proizvod proizvod = new Proizvod();
        when(proizvodMapper.findById(1L)).thenReturn(proizvod);

        Proizvod result = proizvodService.getById(1L);

        assertNotNull(result);
        verify(proizvodMapper).findById(1L);
    }

    @Test
    void getById_ThrowsException_WhenNotFound() {
        when(proizvodMapper.findById(1L)).thenReturn(null);

        ProizvodNotFoundException ex = assertThrows(ProizvodNotFoundException.class,
                () -> proizvodService.getById(1L));

        assertEquals("Proizvod s ID-jem 1 nije pronađen.", ex.getMessage());
    }

    @Test
    void searchByNazivFuzzy_UsesSubstring_WhenQueryLength4OrLess() {
        List<Proizvod> proizvodi = List.of(new Proizvod());
        String query = "test";
        when(proizvodMapper.searchBySubstring(query)).thenReturn(proizvodi);

        List<Proizvod> result = proizvodService.searchByNazivFuzzy(query);

        assertEquals(1, result.size());
        verify(proizvodMapper).searchBySubstring(query);
        verify(proizvodMapper, never()).searchFuzzy(anyString());
    }

    @Test
    void searchByNazivFuzzy_UsesFuzzy_WhenQueryLengthGreaterThan4() {
        List<Proizvod> proizvodi = List.of(new Proizvod());
        String query = "testing";
        when(proizvodMapper.searchFuzzy(query)).thenReturn(proizvodi);

        List<Proizvod> result = proizvodService.searchByNazivFuzzy(query);

        assertEquals(1, result.size());
        verify(proizvodMapper).searchFuzzy(query);
        verify(proizvodMapper, never()).searchBySubstring(anyString());
    }

    @Test
    void searchByNazivFuzzy_ThrowsException_WhenEmptyResult() {
        String query = "anything";
        when(proizvodMapper.searchFuzzy(query)).thenReturn(Collections.emptyList());

        ProizvodNotFoundException ex = assertThrows(ProizvodNotFoundException.class,
                () -> proizvodService.searchByNazivFuzzy(query));

        assertEquals("Nema proizvoda koji odgovaraju pretraživanju 'anything'.", ex.getMessage());
    }

    @Test
    void findByKategorijaId_ReturnsList_WhenNotEmpty() {
        List<Proizvod> proizvodi = List.of(new Proizvod());
        when(proizvodMapper.findByKategorijaId(2L)).thenReturn(proizvodi);

        List<Proizvod> result = proizvodService.findByKategorijaId(2L);

        assertEquals(1, result.size());
        verify(proizvodMapper).findByKategorijaId(2L);
    }

    @Test
    void findByKategorijaId_ThrowsException_WhenEmpty() {
        when(proizvodMapper.findByKategorijaId(2L)).thenReturn(Collections.emptyList());

        ProizvodNotFoundException ex = assertThrows(ProizvodNotFoundException.class,
                () -> proizvodService.findByKategorijaId(2L));

        assertEquals("Nema proizvoda za kategoriju s ID-jem 2.", ex.getMessage());
    }

    @Test
    void getProizvodiByPodkategorijaAndSpol_ReturnsList_WhenNotEmpty() {
        List<Proizvod> proizvodi = List.of(new Proizvod());
        when(proizvodMapper.findByPodkategorijaNazivAndSpol("podkategorija", "M")).thenReturn(proizvodi);

        List<Proizvod> result = proizvodService.getProizvodiByPodkategorijaAndSpol("podkategorija", "M");

        assertEquals(1, result.size());
        verify(proizvodMapper).findByPodkategorijaNazivAndSpol("podkategorija", "M");
    }

    @Test
    void getProizvodiByPodkategorijaAndSpol_ThrowsException_WhenEmpty() {
        when(proizvodMapper.findByPodkategorijaNazivAndSpol("podkategorija", "F")).thenReturn(Collections.emptyList());

        ProizvodNotFoundException ex = assertThrows(ProizvodNotFoundException.class,
                () -> proizvodService.getProizvodiByPodkategorijaAndSpol("podkategorija", "F"));

        assertEquals("Nema proizvoda za podkategoriju 'podkategorija' i spol 'F'.", ex.getMessage());
    }

    @Test
    void findByPodKategorijaId_ReturnsList_WhenNotEmpty() {
        List<Proizvod> proizvodi = List.of(new Proizvod());
        when(proizvodMapper.findByPodkategorijaId(3L)).thenReturn(proizvodi);

        List<Proizvod> result = proizvodService.findByPodKategorijaId(3L);

        assertEquals(1, result.size());
        verify(proizvodMapper).findByPodkategorijaId(3L);
    }

    @Test
    void findByPodKategorijaId_ThrowsException_WhenEmpty() {
        when(proizvodMapper.findByPodkategorijaId(3L)).thenReturn(Collections.emptyList());

        ProizvodNotFoundException ex = assertThrows(ProizvodNotFoundException.class,
                () -> proizvodService.findByPodKategorijaId(3L));

        assertEquals("Nema proizvoda za podkategoriju s ID-jem 3.", ex.getMessage());
    }

    @Test
    void insert_CallsMapperInsert() {
        Proizvod proizvod = new Proizvod();

        proizvodService.insert(proizvod);

        verify(proizvodMapper).insert(proizvod);
    }

    @Test
    void update_CallsMapperUpdate() {
        Proizvod proizvod = new Proizvod();

        proizvodService.update(proizvod);

        verify(proizvodMapper).update(proizvod);
    }

    @Test
    void delete_CallsMapperDelete() {
        proizvodService.delete(10L);

        verify(proizvodMapper).delete(10L);
    }
}