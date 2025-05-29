package com.example.AboutMe.Korisnik;

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
        return korisnikMapper.findById(id);
    }

    public void create(Korisnik korisnik){
        String hashedPassword = passwordEncoder.encode(korisnik.getPassword());
        korisnik.setPassword(hashedPassword);
        korisnikMapper.insert(korisnik);
        korisnikMapper.insertNarudzba(korisnik.getId());
    }

    public Korisnik authenticateUser(Korisnik korisnik){
        Korisnik foundUser = korisnikMapper.findByEmailAndPassword(korisnik.getEmail());

        if(foundUser == null){
            return null;
        }
        boolean lozinkaTocna = BCrypt.checkpw(korisnik.getPassword(),foundUser.getPassword());
        return lozinkaTocna ? foundUser : null;
    }

    public Korisnik findByEmail(String email){
        return korisnikMapper.findByEmail(email);
    }

    public void deleteById(Integer id){
        korisnikMapper.deleteById(id);
    }
}
