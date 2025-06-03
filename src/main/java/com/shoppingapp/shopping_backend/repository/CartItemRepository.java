package com.shoppingapp.shopping_backend.repository;

import com.shoppingapp.shopping_backend.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUserEmail(String userEmail);
    void deleteByUserEmail(String userEmail);
}