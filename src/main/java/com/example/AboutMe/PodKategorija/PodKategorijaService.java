package com.example.AboutMe.PodKategorija;

import com.example.AboutMe.Exception.PodKategorijaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PodKategorijaService {

    private final PodKategorijaMapper podKategorijaMapper;
    @Autowired
    public PodKategorijaService(PodKategorijaMapper podKategorijaMapper) {

        this.podKategorijaMapper = podKategorijaMapper;
    }

    public List<PodKategorija> getAll() {
        List<PodKategorija> podKategorije = podKategorijaMapper.findAll();
        if (podKategorije.isEmpty()) {
            throw new PodKategorijaNotFoundException("Nema dostupnih podkategorija.");
        }
        return podKategorije;
    }

    public PodKategorija getById(Long id) {
        PodKategorija podKategorija = podKategorijaMapper.findById(id);
        if (podKategorija == null) {
            throw new PodKategorijaNotFoundException("Podkategorija s ID-jem " + id + " nije pronađena.");
        }
        return podKategorija;
    }

    public List<PodKategorija> getBySpol(String spol, Long id) {
        List<PodKategorija> podKategorije = podKategorijaMapper.findBySpol(spol, id);
        if (podKategorije.isEmpty()) {
            throw new PodKategorijaNotFoundException("Podkategorije za spol '" + spol + "' i kategoriju " + id + " nisu pronađene.");
        }
        return podKategorije;
    }

    public List<PodKategorija> getByKategorijaId(Long id) {
        List<PodKategorija> podKategorije = podKategorijaMapper.findByKategorijaId(id);
        if (podKategorije.isEmpty()) {
            throw new PodKategorijaNotFoundException("Podkategorije za kategoriju s ID-jem " + id + " nisu pronađene.");
        }
        return podKategorije;
    }

    public Long getByNaziv(String naziv) {
        Long id = podKategorijaMapper.findIdByNaziv(naziv);
        if (id == null) {
            throw new PodKategorijaNotFoundException("Podkategorija s nazivom '" + naziv + "' nije pronađena.");
        }
        return id;
    }

    public void insert(PodKategorija podKategorija) {
        podKategorijaMapper.insert(podKategorija);
    }

    public void update(PodKategorija podKategorija) {
        podKategorijaMapper.update(podKategorija);
    }

    public void delete(Long id) {
        podKategorijaMapper.delete(id);
    }
}
