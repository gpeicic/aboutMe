package com.example.AboutMe.Security;

import com.example.AboutMe.Korisnik.Korisnik;
import com.example.AboutMe.Korisnik.KorisnikService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {


    private final KorisnikService korisnikService;


    public OAuth2LoginSuccessHandler(@Lazy KorisnikService korisnikService) {
        this.korisnikService = korisnikService;

    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String password =  UUID.randomUUID().toString();;

        Korisnik korisnik = korisnikService.findByEmail(email);
        if(korisnik == null){
            Korisnik korisnik1 = new Korisnik(email,password);
            korisnikService.create(korisnik1);
        }

        response.sendRedirect("http://localhost:5173");
    }
}
