package com.example.AboutMe.Security;

import com.example.AboutMe.Korisnik.Korisnik;
import com.example.AboutMe.Korisnik.KorisnikMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
@Service
public class CustomUserDetailService implements UserDetailsService {
    private final KorisnikMapper korisnikMapper;

    public CustomUserDetailService(KorisnikMapper korisnikMapper) {
        this.korisnikMapper = korisnikMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Korisnik user = korisnikMapper.findByEmail(email);
        if(user.equals(null)){
            throw new UsernameNotFoundException("User not found!");
        }
        return new LoggedInUser(user.getId(), user.getEmail(), user.getPassword(), Collections.emptyList());
    }
}
