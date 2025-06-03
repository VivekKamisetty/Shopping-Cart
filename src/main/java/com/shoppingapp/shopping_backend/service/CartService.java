package com.shoppingapp.shopping_backend.service;

import com.shoppingapp.shopping_backend.model.CartItem;
import com.shoppingapp.shopping_backend.model.Product;
import com.shoppingapp.shopping_backend.repository.CartItemRepository;
import com.shoppingapp.shopping_backend.repository.ProductRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartItemRepository cartRepo;
    private final ProductRepository productRepo;

    public CartService(CartItemRepository cartRepo, ProductRepository productRepo) {
        this.cartRepo = cartRepo;
        this.productRepo = productRepo;
    }

    public void addToCart(String userEmail, Long productId, int quantity) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        CartItem item = new CartItem(userEmail, productId, product.getName(), product.getPrice(), quantity);
        cartRepo.save(item);
    }

    public List<CartItem> getUserCart(String userEmail) {
        return cartRepo.findByUserEmail(userEmail);
    }

    @Transactional
    public void checkout(String email) {
        List<CartItem> cartItems = cartRepo.findByUserEmail(email);
        for (CartItem item : cartItems) {
            cartRepo.delete(item);
        }
    }

    public void removeFromCart(String email, Long productId) {
        List<CartItem> items = cartRepo.findByUserEmail(email);
        for (CartItem item : items) {
            if (item.getProductId().equals(productId)) {
                if (item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1);
                    cartRepo.save(item);
                } else {
                    cartRepo.delete(item);
                }
                break;
            }
        }
    }
}