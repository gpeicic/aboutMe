package com.example.AboutMe.Wishlist;

import com.example.AboutMe.Exception.WishlistNotFoundException;
import com.example.AboutMe.Proizvod.Proizvod;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    private final WishlistMapper wishlistMapper;

    public WishlistService(WishlistMapper wishlistMapper) {
        this.wishlistMapper = wishlistMapper;
    }

    public List<Proizvod> getAllByKorisnikId(Long korisnik_id) {
        List<Proizvod> wishlists = wishlistMapper.getAllByKorisnikId(korisnik_id);
        if (wishlists == null || wishlists.isEmpty()) {
            throw new WishlistNotFoundException("Wishlist je prazan ili nije pronađen.");
        }
        return wishlists;
    }
    public void deleteProizvodFromWishlist(Long korisnik_id,Long proizvod_id){
        wishlistMapper.deleteProizvodFromWishlist(korisnik_id,proizvod_id);
    }

    public void createWishlist(Integer korisnik_id) {
        if (korisnik_id == null) {
            throw new WishlistNotFoundException("Wishlist mora imati korisnik_id ");
        }else{
            wishlistMapper.createWishlist(korisnik_id);
        }

    }

    public void addProizvodToWishlist(Long korisnik_id, Long proizvod_id) {
        try {
            wishlistMapper.addProizvodToWishlist(korisnik_id, proizvod_id);
        } catch (Exception ex) {

            if (ex.getMessage().toLowerCase().contains("duplicate") || ex.getMessage().toLowerCase().contains("unique")) {
                throw new WishlistNotFoundException("Proizvod već postoji u wishlisti korisnika.");
            }
            throw ex;
        }
    }
}
