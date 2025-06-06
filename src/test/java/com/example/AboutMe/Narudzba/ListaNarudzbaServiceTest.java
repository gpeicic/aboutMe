package com.example.AboutMe.Narudzba;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

import com.example.AboutMe.Exception.ProizvodNotFoundException;
import com.example.AboutMe.Exception.StavkaNotFoundException;
import com.example.AboutMe.Proizvod.Proizvod;
import com.example.AboutMe.Proizvod.ProizvodMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

public class ListaNarudzbaServiceTest {

    @Mock
    private ListaNarudzbaMapper listaNarudzbaMapper;

    @Mock
    private ProizvodMapper proizvodMapper;

    private ListaNarudzbaService listaNarudzbaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        listaNarudzbaService = new ListaNarudzbaService(listaNarudzbaMapper, proizvodMapper);
    }

    @Test
    public void testDodajStavku_ProizvodExists_InsertsAndUpdatesPrice() {
        ListaNarudzba stavka = new ListaNarudzba(1L,1L,1L,3L);


        Proizvod proizvod = new Proizvod();
        proizvod.setCijena(BigDecimal.valueOf(100));

        when(proizvodMapper.findById(1L)).thenReturn(proizvod);

        listaNarudzbaService.dodajStavku(stavka);

        verify(proizvodMapper).findById(1L);
        verify(listaNarudzbaMapper).insertListaNarudzbi(stavka);
        verify(listaNarudzbaMapper).dodajNaCijenuNarudzbe(1L, BigDecimal.valueOf(300));
    }

    @Test
    public void testDodajStavku_ProizvodNotFound_ThrowsException() {
        ListaNarudzba stavka = new ListaNarudzba(1L,1L,10L,3L);
        stavka.setProizvod_id(99L);

        when(proizvodMapper.findById(99L)).thenReturn(null);

        ProizvodNotFoundException ex = assertThrows(ProizvodNotFoundException.class, () -> listaNarudzbaService.dodajStavku(stavka));

        assertTrue(ex.getMessage().contains("99"));
        verify(proizvodMapper).findById(99L);
        verifyNoMoreInteractions(listaNarudzbaMapper);
    }

    @Test
    public void testDohvatiStavkeZaNarudzbu_ReturnsList() {
        Long narudzbaId = 5L;
        List<ListaNarudzba> stavke = new ArrayList<>();
        stavke.add(new ListaNarudzba(1L,5L,10L,3L));
        when(listaNarudzbaMapper.getByNarudzbaId(narudzbaId)).thenReturn(stavke);

        List<ListaNarudzba> result = listaNarudzbaService.dohvatiStavkeZaNarudzbu(narudzbaId);

        assertEquals(1, result.size());
        verify(listaNarudzbaMapper).getByNarudzbaId(narudzbaId);
    }

    @Test
    public void testAzurirajKolicinu_ValidData_UpdatesQuantityAndPrice() {
        Long stavkaId = 1L;
        int novaKolicina = 5;

        ListaNarudzba stavka = new ListaNarudzba(1L,1L,10L,3L);
        stavka.setId(stavkaId);
        stavka.setProizvod_id(2L);
        stavka.setNarudzba_id(10L);
        stavka.setKolicina(3L);

        Proizvod proizvod = new Proizvod();
        proizvod.setCijena(BigDecimal.valueOf(100));

        when(listaNarudzbaMapper.findById(stavkaId)).thenReturn(stavka);
        when(proizvodMapper.findById(2L)).thenReturn(proizvod);

        listaNarudzbaService.azurirajKolicinu(stavkaId, novaKolicina);

        verify(listaNarudzbaMapper).findById(stavkaId);
        verify(proizvodMapper).findById(2L);
        verify(listaNarudzbaMapper).updateKolicina(stavkaId, novaKolicina);


        verify(listaNarudzbaMapper).dodajNaCijenuNarudzbe(10L, BigDecimal.valueOf(200));
    }

    @Test
    public void testAzurirajKolicinu_StavkaNotFound_ThrowsException() {
        Long stavkaId = 10L;

        when(listaNarudzbaMapper.findById(stavkaId)).thenReturn(null);

        assertThrows(StavkaNotFoundException.class, () -> listaNarudzbaService.azurirajKolicinu(stavkaId, 5));

        verify(listaNarudzbaMapper).findById(stavkaId);
        verifyNoMoreInteractions(proizvodMapper);
    }

    @Test
    public void testAzurirajKolicinu_ProizvodNotFound_ThrowsException() {
        Long stavkaId = 1L;
        ListaNarudzba stavka =new ListaNarudzba(1L,1L,10L,3L);
        stavka.setProizvod_id(2L);

        when(listaNarudzbaMapper.findById(stavkaId)).thenReturn(stavka);
        when(proizvodMapper.findById(2L)).thenReturn(null);

        ProizvodNotFoundException ex = assertThrows(ProizvodNotFoundException.class, () -> listaNarudzbaService.azurirajKolicinu(stavkaId, 5));

        assertTrue(ex.getMessage().contains("2"));
        verify(listaNarudzbaMapper).findById(stavkaId);
        verify(proizvodMapper).findById(2L);
    }

    @Test
    public void testObrisiStavku_ValidData_DeletesAndUpdatesPrice() {
        ListaNarudzba stavka =new ListaNarudzba(1L,1L,10L,3L);
        stavka.setId(1L);
        stavka.setProizvod_id(2L);
        stavka.setNarudzba_id(3L);
        stavka.setKolicina(4L);

        Proizvod proizvod = new Proizvod();
        proizvod.setCijena(BigDecimal.valueOf(50));

        when(listaNarudzbaMapper.findById(1L)).thenReturn(stavka);
        when(proizvodMapper.findById(2L)).thenReturn(proizvod);

        listaNarudzbaService.obrisiStavku(stavka);

        verify(listaNarudzbaMapper).findById(1L);
        verify(proizvodMapper).findById(2L);
        verify(listaNarudzbaMapper).deleteById(1L);

        BigDecimal oduzmi = BigDecimal.valueOf(50).multiply(BigDecimal.valueOf(4));
        verify(listaNarudzbaMapper).oduzmiOdCijeneNarudzbe(3L, oduzmi);
    }

    @Test
    public void testObrisiStavku_StavkaNotFound_ThrowsException() {
        ListaNarudzba stavka = new ListaNarudzba(1L,1L,10L,3L);
        stavka.setId(99L);

        when(listaNarudzbaMapper.findById(99L)).thenReturn(null);

        assertThrows(StavkaNotFoundException.class, () -> listaNarudzbaService.obrisiStavku(stavka));

        verify(listaNarudzbaMapper).findById(99L);
        verifyNoMoreInteractions(proizvodMapper);
    }

    @Test
    public void testObrisiStavku_ProizvodNotFound_ThrowsException() {
        ListaNarudzba stavka = new ListaNarudzba(1L,1L,10L,3L);
        stavka.setId(1L);
        stavka.setProizvod_id(2L);

        when(listaNarudzbaMapper.findById(1L)).thenReturn(stavka);
        when(proizvodMapper.findById(2L)).thenReturn(null);

        assertThrows(ProizvodNotFoundException.class, () -> listaNarudzbaService.obrisiStavku(stavka));

        verify(listaNarudzbaMapper).findById(1L);
        verify(proizvodMapper).findById(2L);
    }

    @Test
    public void testObrisiSveStavkeZaNarudzbu_CallsMapper() {
        Long narudzbaId = 7L;

        listaNarudzbaService.obrisiSveStavkeZaNarudzbu(narudzbaId);

        verify(listaNarudzbaMapper).deleteAllByNarudzbaId(narudzbaId);
    }
}