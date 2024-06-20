package com.gmail.ypon2003.marketplacebackend.controllers.personController;

import com.gmail.ypon2003.marketplacebackend.models.Person;
import com.gmail.ypon2003.marketplacebackend.services.PersonService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/seller")
public class SellerController {

    private static final Logger log = LoggerFactory.getLogger(Person.class);
    private final PersonService sellerService;

    public SellerController(PersonService sellerService) {
        this.sellerService = sellerService;
    }

    @GetMapping
    public List<Person> getAll() {
        return sellerService.findAll()
                .stream()
                .filter(person -> "ROLE_SELLER"
                        .equals(person.getRole()))
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public void update(@PathVariable @Valid Long id, Person entity) {
        log.info("Оновлення продавця з ID: {}", id);
        log.info("Дані продавця: {}", entity);
        if(id == null) {
            throw new IllegalArgumentException("ID продавця не може бути null");
        }
        sellerService.update(id, entity);
    }

    @DeleteMapping("{id}")
    public void delete(Long id) {
        sellerService.delete(id);
    }


//    @PostMapping("/add_seller")
//    public Person addSeller(@Valid @RequestBody Person seller) {
//        seller.setName(seller.getName());
//        seller.setLastName(seller.getLastName());
//        seller.setEmail(seller.getEmail());
//        seller.setPhoneNumber(seller.getPhoneNumber());
//        seller.setPassword(seller.getPassword());
//        seller.setRole("ROLE_SELLER");
//
//        return sellerService.createPerson(seller);
//    }
}
