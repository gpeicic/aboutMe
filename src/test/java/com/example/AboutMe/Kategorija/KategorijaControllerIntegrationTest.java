package com.example.AboutMe.Kategorija;

import com.example.AboutMe.AboutMeApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AboutMeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class KategorijaControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetAll_ReturnsKategorije() {
        ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:" + port + "/kategorija", List.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

    }

    @Test
    public void testAddKategorija_CreatesNewKategorija() {
        Kategorija nova = new Kategorija();
        nova.setNaziv("Test kategorija");
        nova.setSpol("M");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Kategorija> request = new HttpEntity<>(nova, headers);

        ResponseEntity<Void> response = restTemplate.postForEntity("http://localhost:" + port + "/kategorija", request, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }
}
