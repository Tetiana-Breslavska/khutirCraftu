package com.gmail.ypon2003.marketplacebackend.auth.services;

import com.gmail.ypon2003.marketplacebackend.models.Person;
import com.gmail.ypon2003.marketplacebackend.repositories.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Person register(String email, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        if (personRepository.findByEmail(email) != null) {
            throw new IllegalArgumentException("User with this email already exists");
        }
        Person person = new Person();
        person.setEmail(email);
        person.setPassword(passwordEncoder.encode(password));
        person.setRole("ROLE_USER");
        return personRepository.save(person);
    }

    @Transactional
    public void changeRoleToSeller(String email) {
        Person person = personRepository.findByEmail(email);
        if (person == null) {
            throw new IllegalArgumentException("User with this email does not exist");
        }
        person.setRole("ROLE_SELLER");
        personRepository.save(person);
    }
}
