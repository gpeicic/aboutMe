package com.example.AboutMe.Narudzba;

import com.example.AboutMe.Security.LoggedInUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/listaNarudzba")
public class ListaNarudzbaController {

    private final ListaNarudzbaService listaNarudzbaService;

    public ListaNarudzbaController(ListaNarudzbaService listaNarudzbaService) {
        this.listaNarudzbaService = listaNarudzbaService;
    }


    @PostMapping
    public ResponseEntity<String> dodajStavku(@RequestBody ListaNarudzba stavka) {
        listaNarudzbaService.dodajStavku(stavka);
        return ResponseEntity.ok("Stavka uspješno dodana");
    }

    // Dohvati sve stavke za određenu narudzbu
    @GetMapping("/{narudzbaId}")
    public ResponseEntity<List<ListaNarudzba>> dohvatiStavke(@PathVariable Long narudzbaId, @AuthenticationPrincipal LoggedInUser user) {
        Long korisnikId = user.getId().longValue();
        List<ListaNarudzba> stavke = listaNarudzbaService.dohvatiStavkeZaNarudzbu(narudzbaId);
        return ResponseEntity.ok(stavke);
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> azurirajKolicinu(@PathVariable Long id, @RequestBody int novaKolicina) {
        listaNarudzbaService.azurirajKolicinu(id, novaKolicina);
        return ResponseEntity.ok("Količina uspješno ažurirana");
    }


    @DeleteMapping
    public ResponseEntity<String> obrisiStavku(@RequestBody ListaNarudzba stavka) {
        listaNarudzbaService.obrisiStavku(stavka);
        return ResponseEntity.ok("Stavka uspješno obrisana");
    }


    @DeleteMapping("/narudzba/{narudzbaId}")
    public ResponseEntity<String> obrisiSveStavke(@PathVariable Long narudzbaId, @AuthenticationPrincipal LoggedInUser user) {
        Long korisnikId = user.getId().longValue();
        listaNarudzbaService.obrisiSveStavkeZaNarudzbu(narudzbaId);
        return ResponseEntity.ok("Sve stavke uspješno obrisane");
    }
}
