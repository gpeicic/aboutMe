package com.example.AboutMe.Kategorija;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KategorijaService {
    private final KategorijaMapper kategorijaMapper;

    public KategorijaService(KategorijaMapper kategorijaMapper) {
        this.kategorijaMapper = kategorijaMapper;
    }

    public List<Kategorija> getAll(){
        return kategorijaMapper.getAll();
    }

    public List<Kategorija> getKategorijeBySpolId(Long spolId) {
        return kategorijaMapper.getById(spolId);
    }
    public void addKategorija(Kategorija kategorija){
        kategorijaMapper.insert(kategorija);
    }
}
