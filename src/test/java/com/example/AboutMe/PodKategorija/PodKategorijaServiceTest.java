package com.example.AboutMe.PodKategorija;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.AboutMe.Exception.PodKategorijaNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

class PodKategorijaServiceTest {

    @Mock
    private PodKategorijaMapper podKategorijaMapper;

    @InjectMocks
    private PodKategorijaService podKategorijaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll_ReturnsList_WhenNotEmpty() {
        List<PodKategorija> podkategorije = List.of(new PodKategorija(), new PodKategorija());
        when(podKategorijaMapper.findAll()).thenReturn(podkategorije);

        List<PodKategorija> result = podKategorijaService.getAll();

        assertEquals(2, result.size());
        verify(podKategorijaMapper).findAll();
    }

    @Test
    void getAll_ThrowsException_WhenEmpty() {
        when(podKategorijaMapper.findAll()).thenReturn(Collections.emptyList());

        PodKategorijaNotFoundException ex = assertThrows(PodKategorijaNotFoundException.class,
                () -> podKategorijaService.getAll());

        assertEquals("Nema dostupnih podkategorija.", ex.getMessage());
    }

    @Test
    void getById_ReturnsPodKategorija_WhenFound() {
        PodKategorija podkategorija = new PodKategorija();
        when(podKategorijaMapper.findById(1L)).thenReturn(podkategorija);

        PodKategorija result = podKategorijaService.getById(1L);

        assertNotNull(result);
        verify(podKategorijaMapper).findById(1L);
    }

    @Test
    void getById_ThrowsException_WhenNotFound() {
        when(podKategorijaMapper.findById(1L)).thenReturn(null);

        PodKategorijaNotFoundException ex = assertThrows(PodKategorijaNotFoundException.class,
                () -> podKategorijaService.getById(1L));

        assertEquals("Podkategorija s ID-jem 1 nije pronađena.", ex.getMessage());
    }

    @Test
    void getBySpol_ReturnsList_WhenNotEmpty() {
        List<PodKategorija> podkategorije = List.of(new PodKategorija());
        when(podKategorijaMapper.findBySpol("M", 2L)).thenReturn(podkategorije);

        List<PodKategorija> result = podKategorijaService.getBySpol("M", 2L);

        assertEquals(1, result.size());
        verify(podKategorijaMapper).findBySpol("M", 2L);
    }

    @Test
    void getBySpol_ThrowsException_WhenEmpty() {
        when(podKategorijaMapper.findBySpol("F", 2L)).thenReturn(Collections.emptyList());

        PodKategorijaNotFoundException ex = assertThrows(PodKategorijaNotFoundException.class,
                () -> podKategorijaService.getBySpol("F", 2L));

        assertEquals("Podkategorije za spol 'F' i kategoriju 2 nisu pronađene.", ex.getMessage());
    }

    @Test
    void getByKategorijaId_ReturnsList_WhenNotEmpty() {
        List<PodKategorija> podkategorije = List.of(new PodKategorija());
        when(podKategorijaMapper.findByKategorijaId(3L)).thenReturn(podkategorije);

        List<PodKategorija> result = podKategorijaService.getByKategorijaId(3L);

        assertEquals(1, result.size());
        verify(podKategorijaMapper).findByKategorijaId(3L);
    }

    @Test
    void getByKategorijaId_ThrowsException_WhenEmpty() {
        when(podKategorijaMapper.findByKategorijaId(3L)).thenReturn(Collections.emptyList());

        PodKategorijaNotFoundException ex = assertThrows(PodKategorijaNotFoundException.class,
                () -> podKategorijaService.getByKategorijaId(3L));

        assertEquals("Podkategorije za kategoriju s ID-jem 3 nisu pronađene.", ex.getMessage());
    }

    @Test
    void getByNaziv_ReturnsId_WhenFound() {
        when(podKategorijaMapper.findIdByNaziv("naziv")).thenReturn(5L);

        Long id = podKategorijaService.getByNaziv("naziv");

        assertEquals(5L, id);
        verify(podKategorijaMapper).findIdByNaziv("naziv");
    }

    @Test
    void getByNaziv_ThrowsException_WhenNotFound() {
        when(podKategorijaMapper.findIdByNaziv("nepostojeci")).thenReturn(null);

        PodKategorijaNotFoundException ex = assertThrows(PodKategorijaNotFoundException.class,
                () -> podKategorijaService.getByNaziv("nepostojeci"));

        assertEquals("Podkategorija s nazivom 'nepostojeci' nije pronađena.", ex.getMessage());
    }

    @Test
    void insert_CallsMapperInsert() {
        PodKategorija podkategorija = new PodKategorija();

        podKategorijaService.insert(podkategorija);

        verify(podKategorijaMapper).insert(podkategorija);
    }

    @Test
    void update_CallsMapperUpdate() {
        PodKategorija podkategorija = new PodKategorija();

        podKategorijaService.update(podkategorija);

        verify(podKategorijaMapper).update(podkategorija);
    }

    @Test
    void delete_CallsMapperDelete() {
        podKategorijaService.delete(10L);

        verify(podKategorijaMapper).delete(10L);
    }
}
