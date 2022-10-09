package com.woo.jpa.shopping.domain.service;

import com.woo.jpa.shopping.domain.entity.Product;
import com.woo.jpa.shopping.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void insertProduct(Product product) {
        productRepository.save(product);
    }

    public void updateProduct(Long productId, Product product) {
        Optional<Product> byId = productRepository.findById(productId);
        byId.get().setName(product.getName());
        byId.get().setPrice(product.getPrice());
        byId.get().setQuantity(product.getQuantity());
    }

    public Product getProduct(Long productId) {
        return productRepository.findById(productId).get();
    }

    public List<Product> getProductList() {
        return (List<Product>) productRepository.findAll();
    }
}
