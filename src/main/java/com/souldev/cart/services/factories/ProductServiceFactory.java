package com.souldev.cart.services.factories;

import com.souldev.cart.repositories.ProductRepository;
import com.souldev.cart.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductServiceFactory implements ServiceFactory<ProductService> {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceFactory(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public ProductService create() {
        return new ProductService(productRepository);
    }
}
