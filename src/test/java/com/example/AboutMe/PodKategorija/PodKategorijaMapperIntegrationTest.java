package com.example.AboutMe.PodKategorija;

import com.example.AboutMe.Kategorija.Kategorija;
import com.example.AboutMe.Kategorija.KategorijaMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class PodKategorijaMapperIntegrationTest {

    @Autowired
    private PodKategorijaMapper podKategorijaMapper;

    @Autowired
    private KategorijaMapper kategorijaMapper;

    private Long testKategorijaId;

    @BeforeEach
    void setup() {
        Kategorija kategorija = new Kategorija();
        kategorija.setNaziv("Odjeca");
        kategorija.setSpol("Muškarci");

        kategorijaMapper.insert(kategorija);
        testKategorijaId = kategorija.getId();
    }

    @Test
    void testInsertAndFindById() {
        PodKategorija pod = new PodKategorija();
        pod.setNaziv("Test Podkategorija");
        pod.setTip_podkategorije("tip1");

        Kategorija kategorija = new Kategorija();
        kategorija.setId(testKategorijaId);
        pod.setKategorija(kategorija);

        podKategorijaMapper.insert(pod);

        PodKategorija fetched = podKategorijaMapper.findById(pod.getId());

        assertThat(fetched).isNotNull();
        assertThat(fetched.getNaziv()).isEqualTo("Test Podkategorija");
        assertThat(fetched.getKategorija().getId()).isEqualTo(testKategorijaId);
    }

    @Test
    void testFindBySpol() {
        PodKategorija pod = new PodKategorija();
        pod.setNaziv("Majice");
        pod.setTip_podkategorije("");
        Kategorija kategorija = new Kategorija();
        kategorija.setId(testKategorijaId);
        pod.setKategorija(kategorija);

        podKategorijaMapper.insert(pod);

        List<PodKategorija> result = podKategorijaMapper.findBySpol("Muškarci", testKategorijaId);

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getKategorija().getSpol()).isEqualTo("Muškarci");
    }

    @Test
    void testUpdate() {
        PodKategorija pod = new PodKategorija();
        pod.setNaziv("Za update");
        pod.setTip_podkategorije("tip3");

        Kategorija k = new Kategorija();
        k.setId(testKategorijaId);
        pod.setKategorija(k);

        podKategorijaMapper.insert(pod);

        pod.setNaziv("Novi Naziv");
        podKategorijaMapper.update(pod);

        PodKategorija updated = podKategorijaMapper.findById(pod.getId());
        assertThat(updated.getNaziv()).isEqualTo("Novi Naziv");
    }

    @Test
    void testDelete() {
        PodKategorija pod = new PodKategorija();
        pod.setNaziv("Za brisanje");
        pod.setTip_podkategorije("tip4");

        Kategorija k = new Kategorija();
        k.setId(testKategorijaId);
        pod.setKategorija(k);

        podKategorijaMapper.insert(pod);
        Long id = pod.getId();

        podKategorijaMapper.delete(id);

        PodKategorija deleted = podKategorijaMapper.findById(id);
        assertThat(deleted).isNull();
    }
}
