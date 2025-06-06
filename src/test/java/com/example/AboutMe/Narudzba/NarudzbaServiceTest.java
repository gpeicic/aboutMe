package com.example.AboutMe.Narudzba;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import com.example.AboutMe.Exception.NarudzbaNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;


public class NarudzbaServiceTest {

    @Mock
    private NarudzbaMapper narudzbaMapper;

    private NarudzbaService narudzbaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        narudzbaService = new NarudzbaService(narudzbaMapper);
    }

    @Test
    public void testKreirajNarudzbu_UkupnaCijenaNull_SetsZero() {
        Narudzba narudzba = new Narudzba();
        narudzba.setUkupnaCijena(null);

        narudzbaService.kreirajNarudzbu(narudzba);

        assertEquals(BigDecimal.ZERO, narudzba.getUkupnaCijena());
        verify(narudzbaMapper).insertNarudzba(narudzba);
    }

    @Test
    public void testKreirajNarudzbu_UkupnaCijenaNotNull_DoesNotChange() {
        Narudzba narudzba = new Narudzba();
        BigDecimal cijena = BigDecimal.valueOf(123.45);
        narudzba.setUkupnaCijena(cijena);

        narudzbaService.kreirajNarudzbu(narudzba);

        assertEquals(cijena, narudzba.getUkupnaCijena());
        verify(narudzbaMapper).insertNarudzba(narudzba);
    }

    @Test
    public void testDohvatiAktivnuNarudzbuZaKorisnika_NarudzbaExists_ReturnsNarudzba() {
        Long korisnikId = 1L;
        Narudzba narudzba = new Narudzba();
        when(narudzbaMapper.getNarudzbaByKorisnik(korisnikId)).thenReturn(narudzba);

        Narudzba result = narudzbaService.dohvatiAktivnuNarudzbuZaKorisnika(korisnikId);

        assertNotNull(result);
        assertEquals(narudzba, result);
        verify(narudzbaMapper).getNarudzbaByKorisnik(korisnikId);
    }

    @Test
    public void testDohvatiAktivnuNarudzbuZaKorisnika_NarudzbaNotFound_ThrowsException() {
        Long korisnikId = 2L;
        when(narudzbaMapper.getNarudzbaByKorisnik(korisnikId)).thenReturn(null);

        assertThrows(NarudzbaNotFoundException.class, () -> {
            narudzbaService.dohvatiAktivnuNarudzbuZaKorisnika(korisnikId);
        });

        verify(narudzbaMapper).getNarudzbaByKorisnik(korisnikId);
    }

    @Test
    public void testAzurirajUkupnuCijenu_NarudzbaExists_UpdatesPrice() {
        Long narudzbaId = 1L;
        BigDecimal novaCijena = BigDecimal.valueOf(500);
        Narudzba narudzba = new Narudzba();

        when(narudzbaMapper.getNarudzbaById(narudzbaId)).thenReturn(narudzba);

        narudzbaService.azurirajUkupnuCijenu(narudzbaId, novaCijena);

        verify(narudzbaMapper).getNarudzbaById(narudzbaId);
        verify(narudzbaMapper).updateUkupnaCijena(narudzbaId, novaCijena);
    }

    @Test
    public void testAzurirajUkupnuCijenu_NarudzbaNotFound_ThrowsException() {
        Long narudzbaId = 2L;
        BigDecimal novaCijena = BigDecimal.valueOf(100);

        when(narudzbaMapper.getNarudzbaById(narudzbaId)).thenReturn(null);

        assertThrows(NarudzbaNotFoundException.class, () -> {
            narudzbaService.azurirajUkupnuCijenu(narudzbaId, novaCijena);
        });

        verify(narudzbaMapper).getNarudzbaById(narudzbaId);
        verify(narudzbaMapper, never()).updateUkupnaCijena(anyLong(), any());
    }
}