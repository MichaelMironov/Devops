package com.rebrainme.service;

import com.rebrainme.model.Order;
import com.rebrainme.model.Product;
import org.springframework.stereotype.Service;


@Service
public class OrderService {

    private final Order order = new Order();

    public Order getOrderedProducts() {
        return order;
    }

    public void addProductToOrder(final Product product) {
        order.add(product);
    }
}
