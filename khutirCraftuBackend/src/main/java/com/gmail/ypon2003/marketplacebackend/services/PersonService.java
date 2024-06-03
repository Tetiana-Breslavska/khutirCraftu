package com.gmail.ypon2003.marketplacebackend.services;

import com.gmail.ypon2003.marketplacebackend.models.Ad;
import com.gmail.ypon2003.marketplacebackend.models.Person;
import com.gmail.ypon2003.marketplacebackend.repositories.AdRepository;
import com.gmail.ypon2003.marketplacebackend.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author uriiponomarenko 28.05.2024
 */
@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepository personRepository;
    private final AdRepository adRepository;

    @Autowired
    public PersonService(PersonRepository personRepository, AdRepository adRepository) {
        this.personRepository = personRepository;
        this.adRepository = adRepository;
    }

    @Transactional
    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    public List<Person> findAll() {
        return personRepository.findAll().stream()
                .filter(person -> "ROLE_USER".equals(person.getRole()))
                .collect(Collectors.toList());
    }

    public List<Person> findAllSellers() {
        return findAll().stream()
                .filter(person -> "ROLE_SELLER".equals(person.getRole()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void addToFavorites(Long personId, Long adId) throws ChangeSetPersister.NotFoundException {
        Person person = personRepository.findById(personId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        Ad ad = adRepository.findById(adId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        person.addToFavorites(ad);
        personRepository.save(person);
    }

    public List<Ad> getFavorites(Long personId) throws ChangeSetPersister.NotFoundException {
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
