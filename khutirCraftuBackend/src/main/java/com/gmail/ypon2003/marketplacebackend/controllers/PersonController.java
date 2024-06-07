package com.gmail.ypon2003.marketplacebackend.controllers;

import com.gmail.ypon2003.marketplacebackend.models.Ad;
import com.gmail.ypon2003.marketplacebackend.models.Person;
import com.gmail.ypon2003.marketplacebackend.services.PersonService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author uriiponomarenko 31.05.2024
 */
@RestController
@RequestMapping("/api/person")
public class PersonController {

    private static final Logger log = LoggerFactory.getLogger(Person.class);
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping()
    public List<Person> getAllPerson() {
        log.info("Person.getAllPerson()");
        return personService.findAll();
    }

    @PostMapping("/add_person")
    public Person addPerson(@Valid @RequestBody Person person) {
        person.setEmail(person.getEmail());
        person.setPassword(person.getPassword());
        person.setRole("ROLE_USER");

        return personService.createPerson(person);
    }

    @GetMapping("/sellers")
    public List<Person> getAllPersonSellers() {
        log.info("Person.getAllPersonSeller()");
        return personService.findAllSellers();
    }
    @PostMapping("/add_seller")
    public Person addPersonSeller(@Valid @RequestBody Person person) {
        person.setName(person.getName());
        person.setLastName(person.getLastName());
        person.setEmail(person.getEmail());
        person.setPhoneNumber(person.getPhoneNumber());
        person.setPassword(person.getPassword());
        person.setRole("ROLE_SELLER");

        return personService.createPerson(person);
    }

    @PutMapping("/{id}")
    public void updatePerson(@PathVariable("id") Long id, @RequestBody Person person) {
        personService.update(id, person);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable("id") Long id) {
        personService.delete(id);
    }

    @PostMapping("/{personId}/favorites/{adId}")
    public ResponseEntity<Void> addToFavorites(@PathVariable("personId") Long personId,
                                               @PathVariable("adId") Long adId) throws ChangeSetPersister.NotFoundException {
        personService.addToFavorites(personId, adId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{personId}/favorites/{adId}")
    public ResponseEntity<Void> removeFromFavorites(@PathVariable("personId") Long personId,
                                                    @PathVariable("adId") Long adId) throws ChangeSetPersister.NotFoundException {
        personService.removeFromFavorites(personId, adId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{personId}/favorites")
    public ResponseEntity<List<Ad>> getFavorites(@PathVariable("personId") Long personId) throws ChangeSetPersister.NotFoundException {
        List<Ad> favorites = personService.getFavorites(personId);
        return ResponseEntity.ok(favorites);
    }
}
