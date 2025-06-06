package com.example.AboutMe.Korisnik;

import com.example.AboutMe.Exception.KorisnikAuthenticationException;
import com.example.AboutMe.Exception.KorisnikNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KorisnikServiceTest {

    @Mock
    private KorisnikMapper korisnikMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private KorisnikService korisnikService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetById_WhenUserExists_ReturnsKorisnik() {
        Korisnik korisnik = new Korisnik();
        korisnik.setId(1);
        when(korisnikMapper.findById(1)).thenReturn(korisnik);

        Korisnik result = korisnikService.getById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(korisnikMapper, times(1)).findById(1);
    }

    @Test
    void testGetById_WhenUserDoesNotExist_ThrowsException() {
        when(korisnikMapper.findById(1)).thenReturn(null);

        assertThrows(KorisnikNotFoundException.class, () -> korisnikService.getById(1));
        verify(korisnikMapper, times(1)).findById(1);
    }

    @Test
    void testCreate_EncodesPasswordAndInsertsUser() {
        Korisnik korisnik = new Korisnik();
        korisnik.setPassword("rawPassword");
        korisnik.setId(1);

        when(passwordEncoder.encode("rawPassword")).thenReturn("hashedPassword");

        korisnikService.create(korisnik);

        assertEquals("hashedPassword", korisnik.getPassword());
        verify(passwordEncoder, times(1)).encode("rawPassword");
        verify(korisnikMapper, times(1)).insert(korisnik);
        verify(korisnikMapper, times(1)).insertNarudzba(korisnik.getId());
    }

    @Test
    void testAuthenticateUser_WhenUserNotFound_ThrowsException() {
        Korisnik inputUser = new Korisnik();
        inputUser.setEmail("email@example.com");
        inputUser.setPassword("password");

        when(korisnikMapper.findByEmail("email@example.com")).thenReturn(null);

        assertThrows(KorisnikAuthenticationException.class, () -> korisnikService.authenticateUser(inputUser));
        verify(korisnikMapper, times(1)).findByEmail("email@example.com");
    }

    @Test
    void testAuthenticateUser_WhenPasswordIncorrect_ThrowsException() {
        Korisnik inputUser = new Korisnik();
        inputUser.setEmail("email@example.com");
        inputUser.setPassword("wrongPassword");

        Korisnik foundUser = new Korisnik();
        foundUser.setPassword("hashedPassword");

        when(korisnikMapper.findByEmail("email@example.com")).thenReturn(foundUser);
        when(passwordEncoder.matches("wrongPassword", "hashedPassword")).thenReturn(false);

        assertThrows(KorisnikAuthenticationException.class, () -> korisnikService.authenticateUser(inputUser));
        verify(korisnikMapper, times(1)).findByEmail("email@example.com");
        verify(passwordEncoder, times(1)).matches("wrongPassword", "hashedPassword");
    }

    @Test
    void testAuthenticateUser_WhenPasswordCorrect_ReturnsUser() {
        Korisnik inputUser = new Korisnik();
        inputUser.setEmail("email@example.com");
        inputUser.setPassword("correctPassword");

        Korisnik foundUser = new Korisnik();
        foundUser.setPassword("hashedPassword");

        when(korisnikMapper.findByEmail("email@example.com")).thenReturn(foundUser);
        when(passwordEncoder.matches("correctPassword", "hashedPassword")).thenReturn(true);

        Korisnik result = korisnikService.authenticateUser(inputUser);

        assertNotNull(result);
        assertEquals(foundUser, result);
        verify(korisnikMapper, times(1)).findByEmail("email@example.com");
        verify(passwordEncoder, times(1)).matches("correctPassword", "hashedPassword");
    }

    @Test
    void testFindByEmail_ReturnsUser() {
        Korisnik korisnik = new Korisnik();
        korisnik.setEmail("email@example.com");

        when(korisnikMapper.findByEmail("email@example.com")).thenReturn(korisnik);

        Korisnik result = korisnikService.findByEmail("email@example.com");

        assertNotNull(result);
        assertEquals("email@example.com", result.getEmail());
        verify(korisnikMapper, times(1)).findByEmail("email@example.com");
    }

    @Test
    void testDeleteById_WhenUserExists_DeletesUser() {
        Korisnik korisnik = new Korisnik();
        korisnik.setId(1);

        when(korisnikMapper.findById(1)).thenReturn(korisnik);

        korisnikService.deleteById(1);

        verify(korisnikMapper, times(1)).deleteById(1);
    }

    @Test
    void testDeleteById_WhenUserDoesNotExist_ThrowsException() {
        when(korisnikMapper.findById(1)).thenReturn(null);

        assertThrows(KorisnikNotFoundException.class, () -> korisnikService.deleteById(1));
        verify(korisnikMapper, times(1)).findById(1);
    }
}