package com.example.AboutMe.Kategorija;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/kategorija")
public class KategorijaController {
    private final KategorijaService kategorijaService;
    @Autowired
    public KategorijaController(KategorijaService kategorijaService) {
        this.kategorijaService = kategorijaService;
    }
    @GetMapping
    public ResponseEntity<List<Kategorija>> getAll() {
        List<Kategorija> kategorije = kategorijaService.getAll();
        return ResponseEntity.ok(kategorije);
    }

    @GetMapping("/{spolId}")
    public ResponseEntity<List<Kategorija>> getKategorijeBySpolId(@PathVariable Long spolId) {
        List<Kategorija> kategorije = kategorijaService.getKategorijeBySpolId(spolId);
        return ResponseEntity.ok(kategorije);
    }

    @PostMapping
    public ResponseEntity<Void> addKategorija(@RequestBody Kategorija kategorija) {
        kategorijaService.addKategorija(kategorija);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
