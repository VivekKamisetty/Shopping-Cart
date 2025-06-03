package com.shoppingapp.shopping_backend.controller;

import com.shoppingapp.shopping_backend.model.Product;
import com.shoppingapp.shopping_backend.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Products", description = "Product listing and info")
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(
        summary = "Get all products",
        description = "Returns a list of available products for purchase"
    )
    @ApiResponse(responseCode = "200", description = "Product list retrieved")
    @GetMapping
    public List<Product> listProducts() {
        return productService.getAllProducts();
    }
}