package com.example.AboutMe.Wishlist;

import com.example.AboutMe.Proizvod.Proizvod;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WishlistMapper {

    @Select("""
    SELECT p.id, p.ime, p.cijena, p.opis, p.marka,
           pk.id AS pk_id, pk.naziv AS pk_naziv
    FROM wishlist w
    JOIN proizvod p ON w.proizvod_id = p.id
    LEFT JOIN podkategorija pk ON p.podkategorija_id = pk.id
    WHERE w.korisnik_id = #{korinik_id}
""")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "ime", column = "ime"),
            @Result(property = "cijena", column = "cijena"),
            @Result(property = "opis", column = "opis"),
            @Result(property = "marka", column = "marka"),
            @Result(property = "podkategorija.id", column = "pk_id"),
            @Result(property = "podkategorija.naziv", column = "pk_naziv")
    })
    List<Proizvod> getAllByKorisnikId(@Param("korinik_id") Long korinik_id);


    @Insert("INSERT INTO wishlist (korisnik_id) VALUES (#{korisnik_id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void createWishlist(@Param("korisnikId") Integer korisnik_id);

    @Delete("DELETE FROM wishlist WHERE korisnik_id = #{korisnik_id} AND proizvod_id = #{proizvod_id}")
    void deleteProizvodFromWishlist(@Param("korisnik_id") Long korisnik_id, @Param("proizvod_id") Long proizvod_id);

    @Insert("INSERT INTO wishlist (korisnik_id, proizvod_id) VALUES (#{korisnik_id}, #{proizvod_id})")
    void addProizvodToWishlist(@Param("korisnik_id") Long korisnik_id, @Param("proizvod_id") Long proizvod_id);
}