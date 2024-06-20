package com.gmail.ypon2003.marketplacebackend.controllers;

import com.gmail.ypon2003.marketplacebackend.models.Product;
import com.gmail.ypon2003.marketplacebackend.services.productService.ProductImageService;
import com.gmail.ypon2003.marketplacebackend.services.productService.ProductPaginationService;
import com.gmail.ypon2003.marketplacebackend.services.productService.ProductSearchService;
import com.gmail.ypon2003.marketplacebackend.services.productService.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(Product.class);
    private final ProductService productService;
    private final ProductPaginationService paginationService;
    private final ProductImageService productImageService;
    private final ProductSearchService productSearchService;

    @GetMapping("/product")
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @PostMapping("/product")
    public Product addProduct(@Valid @RequestBody Product product) {
        product.setName(product.getName());
        product.setPrice(product.getPrice());
        product.setInfoSeller(product.getInfoSeller());
        product.setDescription(product.getDescription());
        product.setFlag(product.isFlag());

        return productService.save(product);
    }

    @PutMapping("product/{id}")
    public void updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        productService.updateProduct(id, product);
    }

    @DeleteMapping("product/{id}")
    public void deleteProduct (@PathVariable("id") Long id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchAdsByName(@RequestParam("name") String name) {
        List<Product> products = productSearchService.searchProductsByName(name);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<Product>> getProductsSorted(@RequestParam("sortBy") String sortBy) {
        List<Product> products;
        if(sortBy.equals("price")) {
            products = productSearchService.getProductsSortedByPrice();
        } else {
            products = productSearchService.getProductsSortedByDate();
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping
    public ResponseEntity<Page<Product>> getProducts(@RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = paginationService.getProductsPage(pageable);
        return ResponseEntity.ok(productPage);
    }
}
