package com.example.AboutMe.Proizvod;

import com.example.AboutMe.PodKategorija.PodKategorija;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.BIG_DECIMAL;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProizvodControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static Long createdProizvodId;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/proizvodi";
    }

    @Test
    void testCreateProizvod() {
        Proizvod proizvod = new Proizvod();
        proizvod.setIme("Test Proizvod");
        proizvod.setOpis("Testni opis");
        proizvod.setCijena(BigDecimal.valueOf(99.99));
        proizvod.setPodkategorija(new PodKategorija());


        ResponseEntity<Void> response = restTemplate.postForEntity(getBaseUrl(), proizvod, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testGetAllProizvodi() {
        ResponseEntity<Proizvod[]> response = restTemplate.getForEntity(getBaseUrl(), Proizvod[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();

        createdProizvodId = Arrays.stream(response.getBody())
                .filter(p -> "Adidas Originals Trefoil T-Shirt".equals(p.getIme()))
                .findFirst()
                .map(Proizvod::getId)
                .orElse(null);

        assertThat(createdProizvodId).isNotNull();
    }

    @Test
    void testGetById() {
        ResponseEntity<Proizvod> response = restTemplate.getForEntity(getBaseUrl() + "/" + 1, Proizvod.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getIme()).isEqualTo("Adidas Originals Trefoil T-Shirt");
    }

    @Test
    void testUpdateProizvod() {
        Proizvod updated = new Proizvod();
        updated.setId(createdProizvodId);
        updated.setIme("Ažurirani Proizvod");
        updated.setOpis("Novi opis");
        updated.setCijena(BigDecimal.valueOf(199.99));
        updated.setPodkategorija(new PodKategorija());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Proizvod> entity = new HttpEntity<>(updated, headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                getBaseUrl() + "/" + createdProizvodId,
                HttpMethod.PUT,
                entity,
                Void.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Provjeri ažuriranje
        ResponseEntity<Proizvod> check = restTemplate.getForEntity(getBaseUrl() + "/" + createdProizvodId, Proizvod.class);
        assertThat(check.getBody().getIme()).isEqualTo("Ažurirani Proizvod");
    }

    @Test
    @Order(5)
    void testDeleteProizvod() {
        restTemplate.delete(getBaseUrl() + "/" + createdProizvodId);

        ResponseEntity<Proizvod> response = restTemplate.getForEntity(getBaseUrl() + "/" + createdProizvodId, Proizvod.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR); // or NOT_FOUND if exception handled
    }
}
