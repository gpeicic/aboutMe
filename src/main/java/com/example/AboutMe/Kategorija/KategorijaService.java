package com.example.AboutMe.Kategorija;

import com.example.AboutMe.Exception.KategorijaNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KategorijaService {
    private final KategorijaMapper kategorijaMapper;

    public KategorijaService(KategorijaMapper kategorijaMapper) {
        this.kategorijaMapper = kategorijaMapper;
    }

    public List<Kategorija> getAll() {
        List<Kategorija> kategorije = kategorijaMapper.getAll();
        if (kategorije.isEmpty()) {
            throw new KategorijaNotFoundException("Nema dostupnih kategorija.");
        }
        return kategorije;
    }

    public List<Kategorija> getKategorijeBySpolId(Long spolId) {
        List<Kategorija> kategorije = kategorijaMapper.getById(spolId);
        if (kategorije.isEmpty()) {
            throw new KategorijaNotFoundException("Kategorije za spolId " + spolId + " nisu pronađene.");
        }
        return kategorije;
    }
    public void addKategorija(Kategorija kategorija){
        kategorijaMapper.insert(kategorija);
    }
}
