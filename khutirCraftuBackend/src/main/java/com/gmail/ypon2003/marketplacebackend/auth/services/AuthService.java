package com.gmail.ypon2003.marketplacebackend.auth.services;

import com.gmail.ypon2003.marketplacebackend.models.Person;
import com.gmail.ypon2003.marketplacebackend.repositories.PersonRepository;
import com.gmail.ypon2003.marketplacebackend.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuthService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public String loginService(String email, String password) throws Exception {

        Person person = personRepository.findByEmail(email);
        if(person == null) {
            throw new Exception("Email not found");
        }
        if(!passwordEncoder.matches(password, person.getPassword())) {
            throw new Exception("Invalid password");
        }
        return jwtTokenProvider.createToken(email, person.getRole());
    }

}
