package com.rebrainme.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Double amount = 0.0D;
    private Integer count = 0;

    private List<Product> products = new ArrayList<>();

    public void add(Product product) {
        try {
            addProduct(product);
        } catch (Exception exception) {
            removeProduct(product);
            log.error("Ошибка добавления товара");
        }
    }

    private void addProduct(final Product product) {
        this.products.add(product);
        amount += product.getDetails().getCost();
        ++count;
    }

    private void removeProduct(final Product product) {
        this.products.remove(product);
        amount -= product.getDetails().getCost();
        --count;
    }
}
