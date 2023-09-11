package com.rebrainme.controller;

import com.rebrainme.model.Order;
import com.rebrainme.model.Product;
import com.rebrainme.service.OrderService;
import com.rebrainme.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@SessionAttributes("productsOrder")
public class OrderController {

    private final OrderService orderService;
    private final StorageService storageService;

    @Autowired
    public OrderController(final OrderService orderService, final StorageService storageService) {
        this.orderService = orderService;
        this.storageService = storageService;
    }

    @GetMapping("/order")
    public Order getOrder() {
        return orderService.getOrderedProducts();
    }


    /**
     * @api {GET} /order Add product to order
     * @apiName Add product to order
     * @apiGroup Order
     * @apiExample {curl} cURL
     * curl -X POST http://localhost:8081/products
     *      -H 'Content-Type: application/json'
     *      -H 'Accept: application/json'
     *      -d '{"id":1,"name":"Juice","details":{"description":"Orange juice","cost":3.5}}'
     * @apiExample {java} Java
     * @RestController
     * @SessionAttributes("productsOrder")
     * public class OrderController {
     *
     *     private final OrderService orderService;
     *     private final StorageService storageService;
     *
     *     @Autowired
     *     public OrderController(final OrderService orderService, final StorageService storageService) {
     *         this.orderService = orderService;
     *         this.storageService = storageService;
     *     }
     *
     *     @PostMapping("/order")
     *     public ResponseEntity<Order> addProductToOrder(@RequestBody Product product) {
     *
     *         final boolean productExist = storageService
     *                 .getAllItems().stream()
     *                 .anyMatch(existingProduct -> existingProduct.getName().equals(product.getName()));
     *
     *         if (productExist) {
     *             orderService.addProductToOrder(product);
     *             return new ResponseEntity<>(orderService.getOrderedProducts(), HttpStatus.CREATED);
     *         }
     *
     *         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not exist in storage");
     *     }
     *  }
     *
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 201 CREATED
     * {
     *  "amount": 7.5,
     *  "count": 2,
     *  "products": [
     *     {
     *      "id": 1,
     *      "name": "Juice",
     *      "details": {
     *        "description": "Orange juice",
     *        "cost": 3.5
     *        }
     *      },
     *     {
     *      "id": 3,
     *      "name": "Milk",
     *      "details": {
     *        "description": "Almond milk",
     *        "cost": 4.0
     *        }
     *      }
     *   ]
     * }
     *
     * @apiErrorExample Product not exist:
     * HTTP/1.1 404 Not Found
     *  {
     *      "message": "Item not exist in storage"
     *  }
     */
    @PostMapping("/order")
    public ResponseEntity<Order> addProductToOrder(@RequestBody Product product) {

        final boolean productExist = storageService
                .getAllItems().stream()
                .anyMatch(existingProduct -> existingProduct.getName().equals(product.getName()));

        if (productExist) {
            orderService.addProductToOrder(product);
            return new ResponseEntity<>(orderService.getOrderedProducts(), HttpStatus.CREATED);
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not exist in storage");
    }
}
