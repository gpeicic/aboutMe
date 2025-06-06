package com.example.AboutMe.Korisnik;

import com.example.AboutMe.Exception.KorisnikAuthenticationException;
import com.example.AboutMe.Exception.KorisnikNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class KorisnikService {

    private final KorisnikMapper korisnikMapper;
    private final PasswordEncoder passwordEncoder;

    public KorisnikService(KorisnikMapper korisnikMapper, PasswordEncoder passwordEncoder) {
        this.korisnikMapper = korisnikMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public Korisnik getById(Integer id){
        Korisnik korisnik = korisnikMapper.findById(id);
        if(korisnik == null){
            throw new KorisnikNotFoundException(id);
        }
        return korisnik;
    }

    public void create(Korisnik korisnik){
        String hashedPassword = passwordEncoder.encode(korisnik.getPassword());
        korisnik.setPassword(hashedPassword);
        korisnikMapper.insert(korisnik);
        korisnikMapper.insertNarudzba(korisnik.getId());
    }

    public Korisnik authenticateUser(Korisnik korisnik){
        Korisnik foundUser = korisnikMapper.findByEmail(korisnik.getEmail());

        if(foundUser == null){
            throw new KorisnikAuthenticationException();
        }

        boolean lozinkaTocna = passwordEncoder.matches(korisnik.getPassword(), foundUser.getPassword());

        if(!lozinkaTocna){
            throw new KorisnikAuthenticationException();
        }

        return foundUser;
    }

    public Korisnik findByEmail(String email){
        return korisnikMapper.findByEmail(email);
    }

    public void deleteById(Integer id){
        Korisnik korisnik = korisnikMapper.findById(id);
        if(korisnik == null){
            throw new KorisnikNotFoundException(id);
        }
        korisnikMapper.deleteById(id);
    }
}
