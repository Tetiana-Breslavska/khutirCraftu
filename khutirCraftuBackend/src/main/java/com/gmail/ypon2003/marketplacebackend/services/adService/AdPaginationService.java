package com.gmail.ypon2003.marketplacebackend.services.adService;

import com.gmail.ypon2003.marketplacebackend.models.Ad;
import com.gmail.ypon2003.marketplacebackend.repositories.AdRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AdPaginationService {

    private final AdRepository adRepository;


    public AdPaginationService(AdRepository adRepository) {
        this.adRepository = adRepository;
    }

    public Page<Ad> getAdsPage(Pageable pageable) {
        return adRepository.findAll(pageable);
    }
}
