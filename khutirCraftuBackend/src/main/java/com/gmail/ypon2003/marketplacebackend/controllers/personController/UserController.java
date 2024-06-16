package com.gmail.ypon2003.marketplacebackend.controllers.personController;

import com.gmail.ypon2003.marketplacebackend.models.Ad;
import com.gmail.ypon2003.marketplacebackend.models.Person;
import com.gmail.ypon2003.marketplacebackend.services.PersonService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author uriiponomarenko 31.05.2024
 */
@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController<Person>{

    private static final Logger log = LoggerFactory.getLogger(Person.class);
    private final PersonService userService;

    public UserController(PersonService userService) {
        super(userService);
        this.userService = userService;
    }

    @GetMapping
    @Override
    public List<Person> getAll() {
        return super.getAll()
                .stream().filter(person -> "ROLE_USER".equals(person.getRole()))
                .collect(Collectors.toList());
    }

    @Override
    @PutMapping("{id}")
    public void update(Long id, Person entity) {
        super.update(id, entity);
    }

    @Override
    @DeleteMapping("{id}")
    public void delete(Long id) {
        super.delete(id);
    }

    @PostMapping("/add_user")
    public Person addUser(@Valid @RequestBody Person user) {
        user.setEmail(user.getEmail());
        user.setPassword(user.getPassword());
        user.setRole("ROLE_USER");

        return userService.createPerson(user);
    }

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
    public ResponseEntity<List<Ad>> getFavorites(@PathVariable("personId") Long personId) throws ChangeSetPersister.NotFoundException {
        List<Ad> favorites = userService.getFavorites(personId);
        return ResponseEntity.ok(favorites);
    }
}
