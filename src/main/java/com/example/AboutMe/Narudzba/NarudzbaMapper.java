package com.example.AboutMe.Narudzba;

import org.apache.ibatis.annotations.*;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;

@Mapper
public interface NarudzbaMapper {

    @Insert("INSERT INTO Narudzba (korisnik_id, datum_narudzbe, ukupna_cijena) " +
            "VALUES (#{korisnik_id}, #{datum_narudzbe}, #{ukupnaCijena})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertNarudzba(Narudzba narudzba);

    @Update("UPDATE Narudzba SET ukupna_cijena = #{ukupnaCijena} WHERE id = #{id}")
    void updateUkupnaCijena(@Param("id") Long id, @Param("ukupnaCijena") BigDecimal ukupnaCijena);

    @Select("SELECT * FROM Narudzba WHERE korisnik_id = #{korisnikId} LIMIT 1")
    @Results(id = "narudzbaResult", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "korisnik_id", column = "korisnik_id"),
            @Result(property = "datum_narudzbe", column = "datum_narudzbe"),
            @Result(property = "ukupnaCijena", column = "ukupna_cijena")
    })
    Narudzba getNarudzbaByKorisnik(@Param("korisnikId") Long korisnikId);

    @Select("SELECT * FROM Narudzba WHERE id = #{id}")
    @ResultMap("narudzbaResult")
    Narudzba getNarudzbaById(@Param("id") Long id);
}