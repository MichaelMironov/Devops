package com.rebrainme.service;


import com.rebrainme.model.Details;
import com.rebrainme.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class StorageService {

    List<Product> products = List.of(
            Product.builder()
                    .id(1L)
                    .name("Juice")
                    .details(Details.builder().description("Orange juice").cost(3.50).build())
                    .build(),
            Product.builder()
                    .id(2L)
                    .name("Bread")
                    .details(Details.builder().description("Gray bread").cost(1.99).build())
                    .build(),
            Product.builder()
                    .id(3L)
                    .name("Milk")
                    .details(Details.builder().description("Almond milk").cost(4.00).build())
                    .build()
    );

    public List<Product> getAllItems() {
        return products;
    }

    public Product update(final Product product) {

        final Product productToChange = products.stream()
                .filter(foundProduct -> foundProduct.getId().equals(product.getId()))
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not exist"));

        if (product.getName() != null)
            productToChange.setName(product.getName());

        final Details details = product.getDetails();
        if (details != null) {
            if (details.getCost() != null)
                productToChange.getDetails().setCost(details.getCost());
            if (details.getDescription() != null)
                productToChange.getDetails().setDescription(details.getDescription());
        }

        return productToChange;
    }

    public Details findById(final Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .map(Product::getDetails)
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not exist"));
    }
}
