package com.rebrainme.controller;


import com.rebrainme.model.Details;
import com.rebrainme.model.Product;
import com.rebrainme.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StoreController {

    private final StorageService storageService;

    @Autowired
    public StoreController(final StorageService storageService) {
        this.storageService = storageService;
    }

    /**
     * @api {GET} /products Available products
     * @apiName Get available product
     * @apiGroup Products
     * @apiExample {curl} cURL
     * curl -X GET http://localhost:8081/products
     *      -H 'Content-Type: application/json'
     *      -H 'Accept: application/json'
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * [
     *  {
     *      "id":1,
     *      "name":"Juice",
     *      "details":
     *      {
     *          "description":"Orange juice",
     *          "cost":3.5
     *      }
     *  }
     * ]
     *
     * @apiErrorExample Product not exist:
     * HTTP/1.1 500 Not Found
     *  {
     *      "message": "Unexpected server-side error"
     *  }
     */
    @GetMapping("/products")
    List<Product> getItems() {
        return storageService.getAllItems();
    }

    /**
     * @api {get} /product/:id Product by ID
     * @apiParam {Number} id Product unique ID
     * @apiGroup Product
     * @apiExample {curl} cURL
     * curl -X GET http://localhost:8081/product?id=1
     *      -H 'Content-Type: application/json'
     *      -H 'Accept: application/json'
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     *  {
     *      "description":"Orange juice",
     *      "cost":3.5
     *  }
     * @apiErrorExample Product not exist:
     * HTTP/1.1 404 Not Found
     *  {
     *      "error":"Item not exist"
     *  }
     */
    @GetMapping("/product")
    Details getProductById(@RequestParam Long id) {
        return storageService.findById(id);
    }

    /**
     * @api {patch} /product Update product
     * @apiBody {Object} Product object
     * @apiGroup Product
     * @apiPermission admin
     * @apiExample {curl} cURL
     * curl -X PATCH http://localhost:8081/product?id=1
     *      -H 'Content-Type: application/json'
     *      -H 'Accept: application/json'
     *      -d '{{"id":1,"name":"Meat","details":{"description":"Cow","cost":45.0}}'
     * @apiExample {java} Java
     *  @RestController
     *  public class StoreController {
     *
     *     private final StorageService storageService;
     *
     *     @Autowired
     *     public StoreController(final StorageService storageService) {
     *         this.storageService = storageService;
     *     }
     *     Product changeProductInfo(@RequestBody Product product) {
     *         return storageService.update(product);
     *     }
     *  }
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     *  {
     *     "id": 1,
     *     "name": "Meat",
     *     "details": {
     *         "description": "Cow",
     *         "cost": 45.0
     *      }
     *  }
     * @apiErrorExample Product not exist:
     * HTTP/1.1 404 Not Found
     *  {
     *      "error":"Item not exist"
     *  }
     */
    @PatchMapping("/product")
    Product changeProductInfo(@RequestBody Product product) {
        return storageService.update(product);
    }
}
