package com.shoppingapp.shopping_backend.controller;

import com.shoppingapp.shopping_backend.model.CartItem;
import com.shoppingapp.shopping_backend.service.CartService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Map;

@Tag(name = "Cart", description = "Operations related to the shopping cart")
@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @Operation(
        summary = "Add a product to the cart",
        description = "Adds a specified quantity of a product to the logged-in user's cart"
    )
    @ApiResponse(responseCode = "200", description = "Item added to cart")
    @PostMapping("/add")
    public String addToCart(@AuthenticationPrincipal OAuth2User principal,
                            @RequestBody Map<String, Object> payload) {
        String email = principal.getAttribute("email");
        Long productId = Long.valueOf(payload.get("productId").toString());
        int quantity = Integer.parseInt(payload.get("quantity").toString());
        cartService.addToCart(email, productId, quantity);
        return "Item added to cart.";
    }

    @Operation(
        summary = "View cart items",
        description = "Retrieves the current cart for the authenticated user"
    )
    @ApiResponse(responseCode = "200", description = "Cart retrieved")
    @GetMapping
    public List<CartItem> getCart(@AuthenticationPrincipal OAuth2User principal) {
        String email = principal.getAttribute("email");
        return cartService.getUserCart(email);
    }

    @PostMapping("/remove")
    public String removeFromCart(@AuthenticationPrincipal OAuth2User principal,
                             @RequestBody Map<String, Object> payload) {
        String email = principal.getAttribute("email");
        Long productId = Long.valueOf(payload.get("productId").toString());
        cartService.removeFromCart(email, productId);
        return "Item removed from cart.";
}


    //@GetMapping("/add")
    public String testAddToCart(@AuthenticationPrincipal OAuth2User principal) {
        cartService.addToCart(principal.getAttribute("email"), 1L, 1);
        return "Test item added via GET.";
    }

}
