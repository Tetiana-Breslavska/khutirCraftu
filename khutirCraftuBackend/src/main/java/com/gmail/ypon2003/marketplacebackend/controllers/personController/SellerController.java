package com.gmail.ypon2003.marketplacebackend.controllers.personController;

import com.gmail.ypon2003.marketplacebackend.models.Person;
import com.gmail.ypon2003.marketplacebackend.services.PersonService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/seller")
public class SellerController extends BaseController<Person>{

    private final PersonService sellerService;

    public SellerController(PersonService sellerService) {
        super(sellerService);
        this.sellerService = sellerService;
    }

    @GetMapping
    @Override
    public List<Person> getAll() {
        return sellerService.findAll()
                .stream()
                .filter(person -> "ROLE_SELLER"
                        .equals(person.getRole()))
                .collect(Collectors.toList());
    }

    @Override
    @PutMapping("/{id}")
    public void update(Long id, Person entity) {
        super.update(id, entity);
    }

    @Override
    @DeleteMapping("{id}")
    public void delete(Long id) {
        super.delete(id);
    }


    @PostMapping("/add_seller")
    public Person addSeller(@Valid @RequestBody Person seller) {
        seller.setName(seller.getName());
        seller.setLastName(seller.getLastName());
        seller.setEmail(seller.getEmail());
        seller.setPhoneNumber(seller.getPhoneNumber());
        seller.setPassword(seller.getPassword());
        seller.setRole("ROLE_SELLER");

        return sellerService.createPerson(seller);
    }
}
