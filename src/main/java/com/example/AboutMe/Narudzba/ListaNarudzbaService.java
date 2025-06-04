package com.example.AboutMe.Narudzba;

import com.example.AboutMe.Exception.ProizvodNotFoundException;
import com.example.AboutMe.Exception.StavkaNotFoundException;
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
        Proizvod proizvod = proizvodMapper.findById(stavka.getProizvod_id());
        if (proizvod == null) {
            throw new ProizvodNotFoundException("Proivod sa id-om: " + stavka.getProizvod_id() + " nije pronađen.");
        }
        listaNarudzbaMapper.insertListaNarudzbi(stavka);
        BigDecimal umnozakCijeneIKolicine = proizvod.getCijena().multiply(BigDecimal.valueOf(stavka.getKolicina()));
        listaNarudzbaMapper.dodajNaCijenuNarudzbe(stavka.getNarudzba_id(), umnozakCijeneIKolicine);
    }

    public List<ListaNarudzba> dohvatiStavkeZaNarudzbu(Long narudzbaId) {
        return listaNarudzbaMapper.getByNarudzbaId(narudzbaId);
    }

    @Transactional
    public void azurirajKolicinu(Long id, int novaKolicina) {
        ListaNarudzba stavka = listaNarudzbaMapper.findById(id);
        if (stavka == null) {
            throw new StavkaNotFoundException(id);
        }
        Proizvod proizvod = proizvodMapper.findById(stavka.getProizvod_id());
        if (proizvod == null) {
            throw new ProizvodNotFoundException("Proizvod sa id-om: " + stavka.getProizvod_id() + " nije pronađen.");
        }

        Long staraKolicina = stavka.getKolicina();
        BigDecimal stariIznos = proizvod.getCijena().multiply(BigDecimal.valueOf(staraKolicina));
        BigDecimal noviIznos = proizvod.getCijena().multiply(BigDecimal.valueOf(novaKolicina));
        BigDecimal iznos = noviIznos.subtract(stariIznos);

        listaNarudzbaMapper.updateKolicina(id, novaKolicina);
        listaNarudzbaMapper.dodajNaCijenuNarudzbe(stavka.getNarudzba_id(), iznos);
    }

    @Transactional
    public void obrisiStavku(ListaNarudzba stavka) {
        ListaNarudzba postojećaStavka = listaNarudzbaMapper.findById(stavka.getId());
        if (postojećaStavka == null) {
            throw new StavkaNotFoundException(stavka.getId());
        }
        Proizvod proizvod = proizvodMapper.findById(stavka.getProizvod_id());
        if (proizvod == null) {
            throw new ProizvodNotFoundException("Proizvod sa id-om: " + stavka.getProizvod_id() + " nije pronađen.");
        }

        listaNarudzbaMapper.deleteById(stavka.getId());
        BigDecimal umnozakCijeneIKolicine = proizvod.getCijena().multiply(BigDecimal.valueOf(stavka.getKolicina()));
        listaNarudzbaMapper.oduzmiOdCijeneNarudzbe(stavka.getNarudzba_id(), umnozakCijeneIKolicine);
    }

    @Transactional
    public void obrisiSveStavkeZaNarudzbu(Long narudzbaId) {
        listaNarudzbaMapper.deleteAllByNarudzbaId(narudzbaId);
    }
}
