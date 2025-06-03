package com.shoppingapp.shopping_backend.service;

import com.shoppingapp.shopping_backend.model.*;
import com.shoppingapp.shopping_backend.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepo;
    private final CartService cartService;

    public OrderService(OrderRepository orderRepo, CartService cartService) {
        this.orderRepo = orderRepo;
        this.cartService = cartService;
    }

    public void placeOrder(String email) {
        List<CartItem> cartItems = cartService.getUserCart(email);

        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Cart is empty. Cannot place order.");
        }

        Order order = new Order();
        order.setUserEmail(email);
        order.setOrderTime(LocalDateTime.now());

        List<OrderItem> orderItems = cartItems.stream().map(item -> {
            OrderItem oi = new OrderItem();
            oi.setProductId(item.getProductId());
            oi.setProductName(item.getProductName());
            oi.setPrice(item.getPrice());
            oi.setQuantity(item.getQuantity());
            oi.setOrder(order); // Set back reference
            return oi;
        }).collect(Collectors.toList());

        order.setItems(orderItems);
        double total = orderItems.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();
        order.setTotalPrice(total);

        orderRepo.save(order);
        cartService.checkout(email);
    }

    public List<Order> getOrdersForUser(String email) {
        return orderRepo.findByUserEmail(email);
    }

    public void deleteOrdersForUser(String email) {
        List<Order> orders = orderRepo.findByUserEmail(email);
        orderRepo.deleteAll(orders);
    }
    
}
