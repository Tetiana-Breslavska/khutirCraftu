package com.gmail.ypon2003.marketplacebackend.services;

import com.gmail.ypon2003.marketplacebackend.models.Product;
import com.gmail.ypon2003.marketplacebackend.models.Person;
import com.gmail.ypon2003.marketplacebackend.repositories.ProductRepository;
import com.gmail.ypon2003.marketplacebackend.repositories.PersonRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author uriiponomarenko 28.05.2024
 */
@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepository personRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    public PersonService(PersonRepository personRepository, ProductRepository productRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.productRepository = productRepository;
        this.passwordEncoder = passwordEncoder;
    }

//    @Transactional
//    public Person createPerson(Person person) {
//
//        return personRepository.save(person);
//    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findByEmail(String email) {
        return null;//TODO dev future
    }

    @Transactional
    public void addToFavorites(Long personId, Long productId) throws ChangeSetPersister.NotFoundException {
        Person person = personRepository.findById(personId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        Product product = productRepository.findById(productId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        person.addToFavorites(product);
        personRepository.save(person);
    }

    @Transactional
    public void removeFromFavorites(Long personId, Long productId) throws ChangeSetPersister.NotFoundException {
        Person person = personRepository.findById(personId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        Product product = productRepository.findById(productId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        person.removeFromFavorites(product);
        personRepository.save(person);
    }

    public List<Product> getFavorites(Long personId) throws ChangeSetPersister.NotFoundException {
        Person person = personRepository.findById(personId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        return person.getFavorites();
    }

    @Transactional
    public void update(long id, Person person) {
        person.setPerson_id(id);
        personRepository.save(person);
    }

    @Transactional
    public void delete(long id) {
        personRepository.deleteById(id);
    }

}
