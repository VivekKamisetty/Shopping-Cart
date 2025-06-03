package com.shoppingapp.shopping_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.shoppingapp.shopping_backend.model.Product;
import com.shoppingapp.shopping_backend.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class ShoppingBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingBackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(ProductRepository productRepo) {
	    return args -> {
			if (productRepo.count() == 0) {

			productRepo.save(new Product("Chocolate Cake", "Rich dark chocolate", 12.99, null));
			productRepo.save(new Product("Strawberry Donut", "With glaze", 3.49, null));
			productRepo.save(new Product("Coffee", "Hot and strong", 2.99, null));
	    
			}
		};
	}

}
