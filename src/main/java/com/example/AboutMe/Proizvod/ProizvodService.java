package com.example.AboutMe.Proizvod;

import com.example.AboutMe.Exception.ProizvodNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProizvodService {

    private final ProizvodMapper proizvodMapper;

    public ProizvodService(ProizvodMapper proizvodMapper) {
        this.proizvodMapper = proizvodMapper;
    }

    public List<Proizvod> getAll() {
        List<Proizvod> proizvodi = proizvodMapper.findAll();
        if (proizvodi.isEmpty()) {
            throw new ProizvodNotFoundException("Nema dostupnih proizvoda.");
        }
        return proizvodi;
    }

    public Proizvod getById(Long id) {
        Proizvod proizvod = proizvodMapper.findById(id);
        if (proizvod == null) {
            throw new ProizvodNotFoundException("Proizvod s ID-jem " + id + " nije pronađen.");
        }
        return proizvod;
    }

    public List<Proizvod> searchByNazivFuzzy(String query) {
        List<Proizvod> rezultati;
        if (query.length() <= 4) {
            rezultati = proizvodMapper.searchBySubstring(query);
        } else {
            rezultati = proizvodMapper.searchFuzzy(query);
        }
        if (rezultati.isEmpty()) {
            throw new ProizvodNotFoundException("Nema proizvoda koji odgovaraju pretraživanju '" + query + "'.");
        }
        return rezultati;
    }

    public List<Proizvod> findByKategorijaId(Long kategorijaId) {
        List<Proizvod> proizvodi = proizvodMapper.findByKategorijaId(kategorijaId);
        if (proizvodi.isEmpty()) {
            throw new ProizvodNotFoundException("Nema proizvoda za kategoriju s ID-jem " + kategorijaId + ".");
        }
        return proizvodi;
    }

    public List<Proizvod> getProizvodiByPodkategorijaAndSpol(String podkategorijaNaziv, String spol) {
        List<Proizvod> proizvodi = proizvodMapper.findByPodkategorijaNazivAndSpol(podkategorijaNaziv, spol);
        if (proizvodi.isEmpty()) {
            throw new ProizvodNotFoundException("Nema proizvoda za podkategoriju '" + podkategorijaNaziv + "' i spol '" + spol + "'.");
        }
        return proizvodi;
    }

    public List<Proizvod> findByPodKategorijaId(Long podKategorijaId) {
        List<Proizvod> proizvodi = proizvodMapper.findByPodkategorijaId(podKategorijaId);
        if (proizvodi.isEmpty()) {
            throw new ProizvodNotFoundException("Nema proizvoda za podkategoriju s ID-jem " + podKategorijaId + ".");
        }
        return proizvodi;
    }

    public void insert(Proizvod proizvod) {
        proizvodMapper.insert(proizvod);
    }

    public void update(Proizvod proizvod) {
        proizvodMapper.update(proizvod);
    }

    public void delete(Long id) {
        proizvodMapper.delete(id);
    }
}
