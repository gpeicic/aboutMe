package com.example.AboutMe.Proizvod;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/proizvodi")
public class ProizvodController {

    private final ProizvodService proizvodService;


    @Autowired
    public ProizvodController(ProizvodService proizvodService) {
        this.proizvodService = proizvodService;
    }

    @GetMapping
    public ResponseEntity<List<Proizvod>> getAll() {
        List<Proizvod> proizvodi = proizvodService.getAll();
        return ResponseEntity.ok(proizvodi);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proizvod> getById(@PathVariable Long id) {
        Proizvod proizvod = proizvodService.getById(id);
        return ResponseEntity.ok(proizvod);
    }

    @GetMapping("/kategorija/{kategorijaId}")
    public ResponseEntity<List<Proizvod>> getByKategorija(@PathVariable Long kategorijaId) {
        List<Proizvod> proizvodi = proizvodService.findByKategorijaId(kategorijaId);
        return ResponseEntity.ok(proizvodi);
    }

    @GetMapping("/podkategorija/{podKategorijaId}")
    public ResponseEntity<List<Proizvod>> getByPodKategorija(@PathVariable Long podKategorijaId) {
        List<Proizvod> proizvodi = proizvodService.findByPodKategorijaId(podKategorijaId);
        return ResponseEntity.ok(proizvodi);
    }

    @GetMapping("/{spol}/{kategorija}/{podkategorija}")
    public ResponseEntity<List<Proizvod>> getProizvodiByPodkategorijaAndSpol(
            @PathVariable String spol,
            @PathVariable String kategorija,
            @PathVariable String podkategorija) {
        List<Proizvod> proizvodi = proizvodService.getProizvodiByPodkategorijaAndSpol(podkategorija, spol);
        return ResponseEntity.ok(proizvodi);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Proizvod>> searchProizvodi(@RequestParam String query) {
        if (query == null || query.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        List<Proizvod> rezultati = proizvodService.searchByNazivFuzzy(query);
        return ResponseEntity.ok(rezultati);
    }

    @PostMapping
    public void create(@RequestBody Proizvod proizvod) {
        proizvodService.insert(proizvod);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody Proizvod proizvod) {
        proizvod.setId(id);
        proizvodService.update(proizvod);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        proizvodService.delete(id);
    }
}
