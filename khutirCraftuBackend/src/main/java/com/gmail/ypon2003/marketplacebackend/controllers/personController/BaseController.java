package com.gmail.ypon2003.marketplacebackend.controllers.personController;

import com.gmail.ypon2003.marketplacebackend.models.Person;
import com.gmail.ypon2003.marketplacebackend.services.PersonService;

import java.util.List;

public abstract class BaseController<P> {

    private final PersonService service;

    public BaseController(PersonService service) {
        this.service = service;
    }

    public List<Person> getAll() {
        return service.findAll();
    }

    public void update(Long id, Person entity) {
        service.update(id, entity);
    }

    public void delete(Long id) {
        service.delete(id);
    }
}
