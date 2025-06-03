package com.shoppingapp.shopping_backend.repository;

import com.shoppingapp.shopping_backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}