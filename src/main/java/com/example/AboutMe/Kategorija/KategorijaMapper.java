package com.example.AboutMe.Kategorija;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface KategorijaMapper {

    @Select("""
        SELECT k.id as k_id, k.naziv as k_naziv,
               k.spol as k_spol
        FROM kategorija k
    """)
    @Results({
            @Result(property = "id", column = "k_id"),
            @Result(property = "naziv", column = "k_naziv"),
            @Result(property = "spol", column = "k_spol")
    })
    List<Kategorija> getAll();

    @Select("""
    SELECT k.id as k_id, k.naziv as k_naziv,
           k.spol as k_spol
    FROM kategorija k
    WHERE k.id = #{id}
    """)
    @Results({
            @Result(property = "id", column = "k_id"),
            @Result(property = "naziv", column = "k_naziv"),
            @Result(property = "spol", column = "k_spol")
    })
    List<Kategorija> getById(Long id);

    @Insert("INSERT INTO kategorija (naziv, spol) VALUES (#{naziv}, #{spol})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Kategorija kategorija);

    @Update("UPDATE kategorija SET naziv = #{naziv}, spol = #{spol} WHERE id = #{id}")
    void update(Kategorija kategorija);

    @Delete("DELETE FROM kategorija WHERE id = #{id}")
    void delete(Long id);
}
