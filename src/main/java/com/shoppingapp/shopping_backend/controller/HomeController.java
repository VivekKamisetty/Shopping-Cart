package com.shoppingapp.shopping_backend.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication", description = "Endpoints related to login testing and secured access")
@RestController
public class HomeController {
    
    @Operation(
        summary = "Greet the user",
        description = "Returns a greeting message with the authenticated user's name, if user not authenticated returns 'Hello, Guest'"
    )
    @ApiResponse(responseCode = "200", description = "Greeting returned")
    @GetMapping("/")
    public String home(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) return "Hello, Guest";
        return "Hello, " + principal.getAttribute("name");
    }
    @Operation(
        summary = "Authenticated area check",
        description = "Returns the authenticated user's email to confirm access to a secured area"
    )
    @ApiResponse(responseCode = "200", description = "User is authenticated")
    @GetMapping("/secure")
    public String secure(@AuthenticationPrincipal OAuth2User principal) {
        return "Secure Area for: " + principal.getAttribute("email");
    }
}
