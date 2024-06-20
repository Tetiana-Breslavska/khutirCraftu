package com.gmail.ypon2003.marketplacebackend.services.productService;

import com.gmail.ypon2003.marketplacebackend.models.Product;
import com.gmail.ypon2003.marketplacebackend.repositories.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductImageService {

    private final ProductRepository productRepository;
    //private final //TODO сервіс для зберігання фото


    public ProductImageService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void saveAdImage(Product product, byte[] imageData) {
        // Зберегти зображення в //TODO сервіс для зберігання фото
        // Оновити поле зображення в об'єкті Product
        // Зберегти оновлений об'єкт Product в adRepository
    }

    public byte[] getProductImage(long productId) {
        // Завантажити зображення з //TODO сервіс для зберігання фото за ID оголошення
        // Повернути зображення
        return getProductImage(productId);
    }
}
