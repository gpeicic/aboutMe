package com.example.AboutMe.Proizvod;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProizvodService {

    private final ProizvodMapper proizvodMapper;

    public ProizvodService(ProizvodMapper proizvodMapper) {
        this.proizvodMapper = proizvodMapper;
    }

    public List<Proizvod> getAll(){
        return proizvodMapper.findAll();
    }
    public Proizvod getById(Long id){
        return proizvodMapper.findById(id);
    }
    public List<Proizvod> searchByNazivFuzzy(String query) {
        if(query.length() <= 4){
            return proizvodMapper.searchBySubstring(query);
        }else{
            return proizvodMapper.searchFuzzy(query);
        }

    }
    public List<Proizvod> findByKategorijaId(Long kategorijaId){
        return proizvodMapper.findByKategorijaId(kategorijaId);
    }

    public List<Proizvod> getProizvodiByPodkategorijaAndSpol(String podkategorijaNaziv, String spol) {
        return proizvodMapper.findByPodkategorijaNazivAndSpol(podkategorijaNaziv, spol);
    }

    public List<Proizvod> findByPodKategorijaId(Long podKategorijaId){
        return proizvodMapper.findByPodkategorijaId(podKategorijaId);
    }
    public void insert(Proizvod proizvod){
        proizvodMapper.insert(proizvod);
    }

    public void update(Proizvod proizvod){
        proizvodMapper.update(proizvod);
    }

    public void delete(Long id){
        proizvodMapper.delete(id);
    }
}
