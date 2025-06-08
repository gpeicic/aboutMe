package com.example.AboutMe.Wishlist;

import com.example.AboutMe.Proizvod.Proizvod;
import com.example.AboutMe.Security.LoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    @Autowired
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping
    public ResponseEntity<List<Proizvod>> getAllByKorisnikId(@AuthenticationPrincipal LoggedInUser user) {
        Long korisnikId = user.getId().longValue();
        List<Proizvod> wishlist = wishlistService.getAllByKorisnikId(korisnikId);
        return ResponseEntity.ok(wishlist);
    }

    @PostMapping
    public ResponseEntity<Void> createWishlist(@RequestParam Integer korisnik_id) {
        wishlistService.createWishlist(korisnik_id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProizvodToWishlist(@AuthenticationPrincipal LoggedInUser user , @RequestParam Long proizvodId) {
        Long korisnikId = user.getId().longValue();
        wishlistService.addProizvodToWishlist(korisnikId, proizvodId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Proizvod dodan u wishlistu.");
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteProizvodFromWishlist(@AuthenticationPrincipal LoggedInUser user, @RequestParam Long proizvodId){
        Long korisnikId = user.getId().longValue();
        wishlistService.deleteProizvodFromWishlist(korisnikId,proizvodId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
