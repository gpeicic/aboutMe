package com.example.AboutMe.Narudzba;

import org.apache.ibatis.annotations.*;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface ListaNarudzbaMapper {
    @Insert("INSERT INTO lista_narudzbi (narudzba_id, proizvod_id, kolicina) " +
            "VALUES (#{narudzba_id}, #{proizvod_id}, #{kolicina})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertListaNarudzbi(ListaNarudzba stavka);

    @Update("UPDATE lista_narudzbi SET kolicina = #{kolicina} WHERE id = #{id}")
    void updateKolicina(@Param("id") Long id, @Param("kolicina") int kolicina);

    @Update("UPDATE narudzba SET ukupna_cijena = ukupna_cijena + #{iznos} WHERE id = #{id}")
    void dodajNaCijenuNarudzbe(@Param("id") Long id, @Param("iznos") BigDecimal iznos);

    @Update("UPDATE narudzba SET ukupna_cijena = ukupna_cijena - #{iznos} WHERE id = #{id}")
    void oduzmiOdCijeneNarudzbe(@Param("id") Long id, @Param("iznos") BigDecimal iznos);

    @Delete("DELETE FROM lista_narudzbi WHERE id = #{id}")
    void deleteById(@Param("id") Long id);

    @Delete("DELETE FROM lista_narudzbi WHERE narudzba_id = #{narudzba_id}")
    void deleteAllByNarudzbaId(@Param("narudzbaId") Long narudzbaId);
    @Select("SELECT * FROM lista_narudzbi WHERE id = #{id}")
    ListaNarudzba findById(@Param("id") Long id);
    @Select("SELECT * FROM lista_narudzbi WHERE narudzba_id = #{narudzba_id} ORDER BY id ASC")
    @Results(id = "listaNarudzbiResult", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "narudzba_id", column = "narudzba_id"),
            @Result(property = "proizvod_id", column = "proizvod_id"),
            @Result(property = "kolicina", column = "kolicina")
    })
    List<ListaNarudzba> getByNarudzbaId(@Param("narudzba_id") Long narudzbaId);
}
