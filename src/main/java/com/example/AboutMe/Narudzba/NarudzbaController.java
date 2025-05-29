package com.example.AboutMe.Narudzba;

import com.example.AboutMe.Security.LoggedInUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/narudzba")
public class NarudzbaController {

    private final NarudzbaService narudzbaService;

    public NarudzbaController(NarudzbaService narudzbaService) {
        this.narudzbaService = narudzbaService;
    }

    @PostMapping
    public ResponseEntity<String> kreirajNarudzbu(@RequestBody Narudzba narudzba) {
        narudzbaService.kreirajNarudzbu(narudzba);
        return ResponseEntity.ok("Narudzba uspješno kreirana");
    }

    // Update ukupne cijene narudzbe
    @PutMapping("/{narudzbaId}/ukupna-cijena")
    public ResponseEntity<String> azurirajUkupnuCijenu(@PathVariable Long narudzbaId,
                                                       @RequestParam BigDecimal novaCijena) {
        narudzbaService.azurirajUkupnuCijenu(narudzbaId, novaCijena);
        return ResponseEntity.ok("Ukupna cijena uspješno ažurirana");
    }
    @GetMapping("/aktivna")
    public ResponseEntity<Narudzba> getAktivnaNarudzba(@AuthenticationPrincipal LoggedInUser user) {
        Long korisnikId = user.getId().longValue();

        Narudzba narudzba = narudzbaService.dohvatiAktivnuNarudzbuZaKorisnika(korisnikId);
        System.out.println(narudzba.getUkupnaCijena());
        if (narudzba == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(narudzba);
    }


}
