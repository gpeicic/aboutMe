package com.example.AboutMe.Narudzba;

import com.example.AboutMe.Proizvod.Proizvod;
import com.example.AboutMe.Proizvod.ProizvodMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ListaNarudzbaService {

    private final ListaNarudzbaMapper listaNarudzbaMapper;
    private final ProizvodMapper proizvodMapper;

    public ListaNarudzbaService(ListaNarudzbaMapper listaNarudzbaMapper, ProizvodMapper proizvodMapper) {
        this.listaNarudzbaMapper = listaNarudzbaMapper;
        this.proizvodMapper = proizvodMapper;
    }

    @Transactional
    public void dodajStavku(ListaNarudzba stavka) {
        listaNarudzbaMapper.insertListaNarudzbi(stavka);
        Proizvod proizvod = proizvodMapper.findById(stavka.getProizvod_id());
        BigDecimal umnozakCijeneIKolicine = proizvod.getCijena().multiply(BigDecimal.valueOf(stavka.getKolicina()));
        listaNarudzbaMapper.dodajNaCijenuNarudzbe(stavka.getNarudzba_id(),umnozakCijeneIKolicine);
    }

    public List<ListaNarudzba> dohvatiStavkeZaNarudzbu(Long narudzbaId) {
        return listaNarudzbaMapper.getByNarudzbaId(narudzbaId);
    }

    @Transactional
    public void azurirajKolicinu(Long id, int novaKolicina) {
        ListaNarudzba stavka = listaNarudzbaMapper.findById(id);
        Long staraKolicina = stavka.getKolicina();

        Proizvod proizvod = proizvodMapper.findById(stavka.getProizvod_id());
        BigDecimal stariIznos = proizvod.getCijena().multiply(BigDecimal.valueOf(staraKolicina));
        BigDecimal noviIznos = proizvod.getCijena().multiply(BigDecimal.valueOf(novaKolicina));
        System.out.println("stara cijena: " + stariIznos + " novi iznos " + noviIznos);
        BigDecimal iznos = noviIznos.subtract(stariIznos);
        System.out.println("razlika " + iznos);
        listaNarudzbaMapper.updateKolicina(id, novaKolicina);
        listaNarudzbaMapper.dodajNaCijenuNarudzbe(stavka.getNarudzba_id(), iznos);
    }

    @Transactional
    public void obrisiStavku(ListaNarudzba stavka) {
        listaNarudzbaMapper.deleteById(stavka.getId());
        Proizvod proizvod = proizvodMapper.findById(stavka.getProizvod_id());
        BigDecimal umnozakCijeneIKolicine = proizvod.getCijena().multiply(BigDecimal.valueOf(stavka.getKolicina()));
        listaNarudzbaMapper.oduzmiOdCijeneNarudzbe(stavka.getNarudzba_id(),umnozakCijeneIKolicine);
    }

    @Transactional
    public void obrisiSveStavkeZaNarudzbu(Long narudzbaId) {
        listaNarudzbaMapper.deleteAllByNarudzbaId(narudzbaId);
    }
}
