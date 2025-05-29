package com.example.AboutMe.PodKategorija;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/podkategorija")
public class PodKategorijaController {

    private final PodKategorijaService podKategorijaService;

    public PodKategorijaController(PodKategorijaService podKategorijaService) {
        this.podKategorijaService = podKategorijaService;
    }
    @GetMapping("/{spol}/{kategorijaId}")
    public ResponseEntity<List<PodKategorija>> getBySpolAndKategorija(
            @PathVariable String spol,
            @PathVariable Long kategorijaId) {

        List<PodKategorija> podkategorije = podKategorijaService.getBySpol(spol, kategorijaId);

        if (podkategorije.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(podkategorije);
        }
    }


    @GetMapping
    public ResponseEntity<List<PodKategorija>> getAll(){
        List<PodKategorija> podKategorije = podKategorijaService.getAll();
        if(podKategorije.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.ok(podKategorije);
        }
    }
    @GetMapping("/kategorija/{kategorijaId}")
    public ResponseEntity<List<PodKategorija>> getByKategorijaId(@PathVariable Long kategorijaId){
        List<PodKategorija> podKategorije = podKategorijaService.getByKategorijaId(kategorijaId);
        if(podKategorije.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.ok(podKategorije);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PodKategorija> getById(@PathVariable Long id){
        PodKategorija podKategorija = podKategorijaService.getById(id);
        if(podKategorija.equals(null)){
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.ok(podKategorija);
        }
    }

}
