package com.example.AboutMe.Proizvod;

import org.apache.ibatis.annotations.*;


import java.util.List;

@Mapper
public interface ProizvodMapper {

    @Select("""
        SELECT p.id, p.ime, p.cijena, p.opis, p.marka,
               pk.id AS podkategorija_id, pk.naziv AS podkategorija_naziv, pk.tip_podkategorije, pk.kategorija_id,
               k.id AS kategorija_id, k.naziv AS kategorija_naziv, k.spol
        FROM proizvod p
        JOIN podkategorija pk ON p.podkategorija_id = pk.id
        JOIN kategorija k ON pk.kategorija_id = k.id
    """)
    @Results(id = "ProizvodResultMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "ime", column = "ime"),
            @Result(property = "cijena", column = "cijena"),
            @Result(property = "opis", column = "opis"),
            @Result(property = "marka", column = "marka"),
            @Result(property = "podkategorija.id", column = "podkategorija_id"),
            @Result(property = "podkategorija.naziv", column = "podkategorija_naziv"),
            @Result(property = "podkategorija.tipPodkategorije", column = "tip_podkategorije"),
            @Result(property = "podkategorija.kategorija.id", column = "kategorija_id"),
            @Result(property = "podkategorija.kategorija.naziv", column = "kategorija_naziv"),
            @Result(property = "podkategorija.kategorija.spol", column = "spol")
    })
    List<Proizvod> findAll();

    @Select("""
    SELECT p.id, p.ime, p.cijena, p.opis, p.marka,
           pk.id AS podkategorija_id, pk.naziv AS podkategorija_naziv, pk.tip_podkategorije, pk.kategorija_id,
           k.id AS kategorija_id, k.naziv AS kategorija_naziv, k.spol
    FROM proizvod p
    JOIN podkategorija pk ON p.podkategorija_id = pk.id
    JOIN kategorija k ON pk.kategorija_id = k.id
    WHERE p.ime ILIKE CONCAT('%', #{query}, '%')
    LIMIT 10
""")
    @ResultMap("ProizvodResultMap")
    List<Proizvod> searchFuzzy(@Param("query") String query);
    @Select("""
    SELECT p.id, p.ime, p.cijena, p.opis, p.marka,
           pk.id AS podkategorija_id, pk.naziv AS podkategorija_naziv, pk.tip_podkategorije, pk.kategorija_id,
           k.id AS kategorija_id, k.naziv AS kategorija_naziv, k.spol
    FROM proizvod p
    JOIN podkategorija pk ON p.podkategorija_id = pk.id
    JOIN kategorija k ON pk.kategorija_id = k.id
    WHERE ime ILIKE CONCAT('%', #{query}, '%')
    LIMIT 10
""")
    @ResultMap("ProizvodResultMap")
    List<Proizvod> searchBySubstring(@Param("query") String query);
    @Select("""
        SELECT p.id, p.ime, p.cijena, p.opis, p.marka,
               pk.id AS podkategorija_id, pk.naziv AS podkategorija_naziv, pk.tip_podkategorije, pk.kategorija_id,
               k.id AS kategorija_id, k.naziv AS kategorija_naziv, k.spol
        FROM proizvod p
        JOIN podkategorija pk ON p.podkategorija_id = pk.id
        JOIN kategorija k ON pk.kategorija_id = k.id
        WHERE p.id = #{id}
    """)
    @ResultMap("ProizvodResultMap")
    Proizvod findById(Long id);


    @Select("""
    SELECT
        p.id,
        p.ime,
        p.cijena,
        p.opis,
        p.marka,
        pk.id AS podkategorija_id,
        pk.naziv AS podkategorija_naziv,
        pk.tip_podkategorije,
        pk.kategorija_id,
        k.id AS kategorija_id,
        k.naziv AS kategorija_naziv,
        k.spol
    FROM
        proizvod p
    JOIN
        podkategorija pk ON p.podkategorija_id = pk.id
    JOIN
        kategorija k ON pk.kategorija_id = k.id
    WHERE
        k.id = #{kategorijaId}
""")
    @ResultMap("ProizvodResultMap")
    List<Proizvod> findByKategorijaId(Long kategorijaId);
    @Select("""
        SELECT p.id, p.ime, p.cijena, p.opis, p.marka,
               pk.id AS podkategorija_id, pk.naziv AS podkategorija_naziv, pk.tip_podkategorije, pk.kategorija_id,
               k.id AS kategorija_id, k.naziv AS kategorija_naziv, k.spol
        FROM proizvod p
        JOIN podkategorija pk ON p.podkategorija_id = pk.id
        JOIN kategorija k ON pk.kategorija_id = k.id
        WHERE p.podkategorija_id = #{podkategorijaId}
    """)
    @ResultMap("ProizvodResultMap")
    List<Proizvod> findByPodkategorijaId(Long podkategorijaId);

    @Select("""
    SELECT p.id, p.ime, p.cijena, p.opis, p.marka,
           pk.id AS podkategorija_id, pk.naziv AS podkategorija_naziv, pk.tip_podkategorije, pk.kategorija_id,
           k.id AS kategorija_id, k.naziv AS kategorija_naziv, k.spol
    FROM proizvod p
    JOIN podkategorija pk ON p.podkategorija_id = pk.id
    JOIN kategorija k ON pk.kategorija_id = k.id
    WHERE pk.naziv = #{podkategorijaNaziv}
    AND k.spol = #{spol}
""")
    @ResultMap("ProizvodResultMap")
    List<Proizvod> findByPodkategorijaNazivAndSpol(String podkategorijaNaziv, String spol);
    @Insert("""
        INSERT INTO proizvod (ime, cijena, opis, marka, podkategorija_id, kategorija_id)
        VALUES (#{ime}, #{cijena}, #{opis}, #{marka}, #{podKategorija.id}, #{kategorija.id})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Proizvod proizvod);

    @Update("""
        UPDATE proizvod
        SET ime = #{ime},
            cijena = #{cijena},
            opis = #{opis},
            marka = #{marka},
            podkategorija_id = #{podKategorija.id},
            kategorija_id = #{kategorija.id}
        WHERE id = #{id}
    """)
    void update(Proizvod proizvod);

    @Delete("DELETE FROM proizvod WHERE id = #{id}")
    void delete(Long id);
}
