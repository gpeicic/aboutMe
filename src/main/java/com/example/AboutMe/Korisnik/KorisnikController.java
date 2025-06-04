package com.example.AboutMe.Korisnik;

import com.example.AboutMe.Security.JwtUtil;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/korisnik")
public class KorisnikController {

    private final KorisnikService korisnikService;

    @Autowired
    public KorisnikController(KorisnikService korisnikService) {
        this.korisnikService = korisnikService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Korisnik> getById(@PathVariable Integer id){
        Korisnik korisnik = korisnikService.getById(id);
        return ResponseEntity.ok(korisnik);
    }
    @PostMapping("/prijava")
    public ResponseEntity<?> loginUser(@RequestBody Korisnik korisnik){
        Korisnik authenticatedUser = korisnikService.authenticateUser(korisnik);

        String token = JwtUtil.generateToken(authenticatedUser.getId(), authenticatedUser.getEmail());
        Map<String,Object> responseBody = new HashMap<>();
        responseBody.put("token", token);
        responseBody.put("email", authenticatedUser.getEmail());
        responseBody.put("id", authenticatedUser.getId());
        return ResponseEntity.ok(responseBody);
    }
    @PostMapping
    public ResponseEntity<Void> createKorisnik(@RequestBody Korisnik korisnik){
        korisnikService.create(korisnik);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteKorisnik(@PathVariable Integer id){
        korisnikService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
