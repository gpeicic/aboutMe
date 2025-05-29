package com.example.AboutMe.PodKategorija;

import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface PodKategorijaMapper {

    @Select("SELECT p.id, p.naziv, p.tip_podkategorije, k.id AS kategorija_id, k.naziv AS kategorija_naziv, k.spol AS kategorija_spol " +
            "FROM podkategorija p " +
            "JOIN kategorija k ON p.kategorija_id = k.id")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "naziv", column = "naziv"),
            @Result(property = "tip_podkategorije", column = "tip_podkategorije"),
            @Result(property = "kategorija.id", column = "kategorija_id"),
            @Result(property = "kategorija.naziv", column = "kategorija_naziv"),
            @Result(property = "kategorija.spol", column = "kategorija_spol")
    })
    List<PodKategorija> findAll();

        @Select("SELECT id FROM podkategorija WHERE naziv = #{naziv}")
        Long findIdByNaziv(String naziv);



    @Select("SELECT p.id, p.naziv, p.tip_podkategorije, " +
            "k.id AS kategorija_id, k.naziv AS kategorija_naziv, k.spol AS kategorija_spol " +
            "FROM podkategorija p " +
            "JOIN kategorija k ON p.kategorija_id = k.id " +
            "WHERE LOWER(k.spol) = LOWER(#{spol}) AND k.id = #{kategorijaId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "naziv", column = "naziv"),
            @Result(property = "tip_podkategorije", column = "tip_podkategorije"),
            @Result(property = "kategorija.id", column = "kategorija_id"),
            @Result(property = "kategorija.naziv", column = "kategorija_naziv"),
            @Result(property = "kategorija.spol", column = "kategorija_spol")
    })
    List<PodKategorija> findBySpol(@Param("spol") String spol, @Param("kategorijaId") Long kategorijaId);


    @Select("SELECT p.id, p.naziv, p.tip_podkategorije, k.id AS kategorija_id, k.naziv AS kategorija_naziv, k.spol AS kategorija_spol " +
            "FROM podkategorija p " +
            "JOIN kategorija k ON p.kategorija_id = k.id " +
            "WHERE p.id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "naziv", column = "naziv"),
            @Result(property = "tip_podkategorije", column = "tip_podkategorije"),
            @Result(property = "kategorija.id", column = "kategorija_id"),
            @Result(property = "kategorija.naziv", column = "kategorija_naziv"),
            @Result(property = "kategorija.spol", column = "kategorija_spol")  // Dodano za spol
    })
    PodKategorija findById(Long id);

    @Insert("INSERT INTO podkategorija (naziv, tip_podkategorije, kategorija_id) " +
            "VALUES (#{naziv}, #{tip_podkategorije}, #{kategorija.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(PodKategorija podKategorija);

    @Update("UPDATE podkategorija SET naziv = #{naziv}, tip_podkategorije = #{tip_podkategorije}, kategorija_id = #{kategorija.id} " +
            "WHERE id = #{id}")
    void update(PodKategorija podKategorija);

    @Select("SELECT p.id, p.naziv, p.tip_podkategorije, " +
            "k.id AS kategorija_id, k.naziv AS kategorija_naziv, k.spol AS kategorija_spol " +
            "FROM podkategorija p " +
            "JOIN kategorija k ON p.kategorija_id = k.id " +
            "WHERE k.id = #{kategorijaId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "naziv", column = "naziv"),
            @Result(property = "tip_podkategorije", column = "tip_podkategorije"),
            @Result(property = "kategorija.id", column = "kategorija_id"),
            @Result(property = "kategorija.naziv", column = "kategorija_naziv"),
            @Result(property = "kategorija.spol", column = "kategorija_spol")  // Dodano za spol
    })
    List<PodKategorija> findByKategorijaId(Long kategorijaId);

    @Delete("DELETE FROM podkategorija WHERE id = #{id}")
    void delete(Long id);
}
