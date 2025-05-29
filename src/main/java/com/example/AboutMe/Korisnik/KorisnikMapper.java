package com.example.AboutMe.Korisnik;

import org.apache.ibatis.annotations.*;

@Mapper
public interface KorisnikMapper {

    @Select("SELECT * FROM korisnik WHERE id = #{id}")
    Korisnik findById(@Param("id") Integer id);

    @Select("SELECT * FROM korisnik WHERE email = #{email}")
    Korisnik findByEmailAndPassword(@Param("email") String email);

    @Select("SELECT * FROM korisnik WHERE email = #{email}")
    Korisnik findByEmail(@Param("email") String email);

    @Insert("INSERT INTO korisnik (email, password) VALUES (#{email}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Korisnik korisnik);
    @Insert("""
    INSERT INTO narudzba (korisnik_id, ukupna_cijena, datum_narudzbe, status)
    VALUES (#{korisnikId}, 0, NOW(), 0)
""")
    void insertNarudzba(@Param("korisnikId") Integer korisnikId);

    @Delete("DELETE FROM korisnik WHERE id = #{id}")
    void deleteById(@Param("id") Integer id);
}
