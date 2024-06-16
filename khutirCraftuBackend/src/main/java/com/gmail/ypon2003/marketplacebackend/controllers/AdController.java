package com.gmail.ypon2003.marketplacebackend.controllers;

import com.gmail.ypon2003.marketplacebackend.models.Ad;
import com.gmail.ypon2003.marketplacebackend.services.adService.AdImageService;
import com.gmail.ypon2003.marketplacebackend.services.adService.AdPaginationService;
import com.gmail.ypon2003.marketplacebackend.services.adService.AdSearchService;
import com.gmail.ypon2003.marketplacebackend.services.adService.AdService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author uriiponomarenko 28.05.2024
 */
@RestController
@RequestMapping("/api/ads")
public class AdController {

    private static final Logger log = LoggerFactory.getLogger(Ad.class);
    private final AdService adService;
    private final AdPaginationService paginationService;
    private final AdImageService adImageService;
    private final AdSearchService adSearchService;

    public AdController(AdService adService, AdPaginationService paginationService, AdImageService adImageService, AdSearchService adSearchService) {
        this.adService = adService;
        this.paginationService = paginationService;
        this.adImageService = adImageService;
        this.adSearchService = adSearchService;
    }

    @GetMapping("/ad")
    public List<Ad> getAllAds() {
        log.info("Ad.getAllAds()");
        return adService.findAll();
    }

    @PostMapping("/ad")
    public Ad addAd(@Valid @RequestBody Ad ad) {
        ad.setName(ad.getName());
        ad.setPrice(ad.getPrice());
        ad.setInfoSeller(ad.getInfoSeller());
        ad.setDescription(ad.getDescription());
        ad.setFlag(ad.isFlag());

        return adService.save(ad);
    }

    @PutMapping("ad/{id}")
    public void updateAd(@PathVariable("id") Long id, @RequestBody Ad ad) {
        adService.updateAd(id, ad);
    }

    @DeleteMapping("ad/{id}")
    public void deleteAd (@PathVariable("id") Long id) {
        adService.deleteAd(id);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Ad>> searchAdsByName(@RequestParam("name") String name) {
        List<Ad> ads = adSearchService.searchAdsByName(name);
        return ResponseEntity.ok(ads);
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<Ad>> getAdsSorted(@RequestParam("sortBy") String sortBy) {
        List<Ad> ads;
        if(sortBy.equals("price")) {
            ads = adSearchService.getAdsSortedByPrice();
        } else {
            ads = adSearchService.getAdsSortedByDate();
        }
        return ResponseEntity.ok(ads);
    }

    @GetMapping
    public ResponseEntity<Page<Ad>> getAds(@RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Ad> adsPage = paginationService.getAdsPage(pageable);
        return ResponseEntity.ok(adsPage);
    }
}
