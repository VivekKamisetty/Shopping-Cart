package com.shoppingapp.shopping_backend.controller;

import com.shoppingapp.shopping_backend.model.Order;
import com.shoppingapp.shopping_backend.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Orders", description = "Order management operations")
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(
        summary = "Place an order",
        description = "Places an order using items from the authenticated user's cart"
    )
    @ApiResponse(responseCode = "200", description = "Order successfully placed")
    @GetMapping("/place")
    public String placeOrder(@AuthenticationPrincipal OAuth2User principal) {
        orderService.placeOrder(principal.getAttribute("email"));
        return "Order successfully placed!";
    }

    @Operation(
        summary = "Get all user orders",
        description = "Returns a list of past orders placed by the logged-in user"
    )
    @ApiResponse(responseCode = "200", description = "Orders retrieved")
    @GetMapping
    public List<Order> getOrders(@AuthenticationPrincipal OAuth2User principal) {
        return orderService.getOrdersForUser(principal.getAttribute("email"));
    }

    @DeleteMapping("/delete")
    public String deleteOrders(@AuthenticationPrincipal OAuth2User principal) {
        orderService.deleteOrdersForUser(principal.getAttribute("email"));
        return "Order history deleted.";
}

}
