package com.gmail.ypon2003.marketplacebackend.services.adService;

import com.gmail.ypon2003.marketplacebackend.models.Ad;
import com.gmail.ypon2003.marketplacebackend.repositories.AdRepository;
import org.springframework.stereotype.Service;

@Service
public class AdImageService {

    private final AdRepository adRepository;
    //private final //TODO сервіс для зберігання фото


    public AdImageService(AdRepository adRepository) {
        this.adRepository = adRepository;
    }

    public void saveAdImage(Ad ad, byte[] imageData) {
        // Зберегти зображення в //TODO сервіс для зберігання фото
        // Оновити поле зображення в об'єкті Ad
        // Зберегти оновлений об'єкт Ad в adRepository
    }

    public byte[] getAdImage(long adId) {
        // Завантажити зображення з //TODO сервіс для зберігання фото за ID оголошення
        // Повернути зображення
        return getAdImage(adId);
    }
}
