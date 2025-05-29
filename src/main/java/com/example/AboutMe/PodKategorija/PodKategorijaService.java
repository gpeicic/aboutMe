package com.example.AboutMe.PodKategorija;

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

    public List<PodKategorija> getAll(){
        return podKategorijaMapper.findAll();
    }

    public PodKategorija getById(Long id){
        return podKategorijaMapper.findById(id);
    }
    public List<PodKategorija> getBySpol(String spol,Long id) {
        return podKategorijaMapper.findBySpol(spol,id);
    }
    public List<PodKategorija> getByKategorijaId(Long id){
        return podKategorijaMapper.findByKategorijaId(id);
    }
    public Long getByNaziv(String naziv){return podKategorijaMapper.findIdByNaziv(naziv);}
    public void insert(PodKategorija podKategorija){
        podKategorijaMapper.insert(podKategorija);
    }
    public void update(PodKategorija podKategorija){
        podKategorijaMapper.update(podKategorija);
    }
    public void delete(Long id){
        podKategorijaMapper.delete(id);
    }
}
