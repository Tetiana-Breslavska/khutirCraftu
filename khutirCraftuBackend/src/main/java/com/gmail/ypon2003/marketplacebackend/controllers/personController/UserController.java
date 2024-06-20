package com.gmail.ypon2003.marketplacebackend.controllers.personController;

import com.gmail.ypon2003.marketplacebackend.models.Product;
import com.gmail.ypon2003.marketplacebackend.models.Person;
import com.gmail.ypon2003.marketplacebackend.repositories.PersonRepository;
import com.gmail.ypon2003.marketplacebackend.services.PersonService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author uriiponomarenko 31.05.2024
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(Person.class);
    private final PersonService userService;
    private final PasswordEncoder passwordEncoder;
    private final PersonRepository personRepository;

    public UserController(PersonService userService, PasswordEncoder passwordEncoder, PersonRepository personRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.personRepository = personRepository;
    }

    @GetMapping
    public List<Person> getAll() {
        return userService.findAll()
                .stream().filter(person -> "ROLE_USER".equals(person.getRole()))
                .collect(Collectors.toList());
    }

    @PutMapping("{id}")
    public void update(@PathVariable Long id,@RequestBody @Valid Person entity) {
        userService.update(id, entity);
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestParam String role) {
        try {
            Person person = personRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
            person.setRole(role);
            personRepository.save(person);
            return new ResponseEntity<>("Role updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("{id}")
    public void delete(Long id) {
        userService.delete(id);
    }

//    @PostMapping("/add_user")
//    public Person addUser(@Valid @RequestBody Person user) {
//        user.setEmail(user.getEmail());
//        user.setPassword(user.getPassword());
//        user.setRole("ROLE_USER");
//
//        return userService.createPerson(user);
//    }

    @PostMapping("/{personId}/favorites/{adId}")
    public ResponseEntity<Void> addToFavorites(@PathVariable("personId") Long personId,
                                               @PathVariable("adId") Long adId) throws ChangeSetPersister.NotFoundException {
        userService.addToFavorites(personId, adId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{personId}/favorites/{adId}")
    public ResponseEntity<Void> removeFromFavorites(@PathVariable("personId") Long personId,
                                                    @PathVariable("adId") Long adId) throws ChangeSetPersister.NotFoundException {
       userService.removeFromFavorites(personId, adId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{personId}/favorites")
    public ResponseEntity<List<Product>> getFavorites(@PathVariable("personId") Long personId) throws ChangeSetPersister.NotFoundException {
        List<Product> favorites = userService.getFavorites(personId);
        return ResponseEntity.ok(favorites);
    }
}
