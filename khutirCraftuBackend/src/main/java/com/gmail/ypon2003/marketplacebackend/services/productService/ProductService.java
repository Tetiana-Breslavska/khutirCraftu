package com.gmail.ypon2003.marketplacebackend.services.productService;

import com.gmail.ypon2003.marketplacebackend.models.Product;
import com.gmail.ypon2003.marketplacebackend.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author uriiponomarenko 28.05.2024
 */
@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    public ProductService(ProductRepository productRepository) {

        this.productRepository = productRepository;
    }

    @Transactional
    public Product save(Product product) {
        try {
            if(product.getCreatedAt() == null) {
                product.setCreatedAt(new Date());
            }
            log.info("Saving product: {}", product);
            productRepository.save(product);
        } catch (Exception e) {
            log.error("Error saving product: {}", e.getMessage());
            throw new RuntimeException("Failed to save product", e);
        }
        return product;
    }

    public Optional<Product> showProduct(long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public void updateProduct(long id, Product productUpdate) {
        Optional<Product> updateToBeProduct = showProduct(id);
        if(updateToBeProduct.isPresent()) {
            Product product = updateToBeProduct.get();
            product.setName(productUpdate.getName());
            product.setCreatedAt(productUpdate.getCreatedAt());
            product.setFlag(productUpdate.isFlag());
            product.setDescription(productUpdate.getDescription());
            product.setPrice(productUpdate.getPrice());
            product.setInfoSeller(product.getInfoSeller());
        }
    }

    @Transactional
    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
