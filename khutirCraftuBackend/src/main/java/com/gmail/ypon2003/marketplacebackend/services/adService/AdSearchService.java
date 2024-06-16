package com.gmail.ypon2003.marketplacebackend.services.adService;

import com.gmail.ypon2003.marketplacebackend.models.Ad;
import com.gmail.ypon2003.marketplacebackend.repositories.AdRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdSearchService {

    private final AdRepository adRepository;

    public AdSearchService(AdRepository adRepository) {
        this.adRepository = adRepository;
    }

    public List<Ad> searchAdsByName(String name) {
        return adRepository.findByNameContaining(name);
    }

    public List<Ad> getAdsSortedByPrice() {
        return adRepository.findAll(Sort.by(Sort.Direction.ASC, "price"));
    }

    public List<Ad> getAdsSortedByDate() {
        return adRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

}
