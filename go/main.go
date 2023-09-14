package main

import (
	"github.com/gin-gonic/gin"
)

func main() {

	products := NewProductsStorage()
	handler := NewHandler(products)

	router := gin.Default()

	router.POST("/product", handler.CreateProduct)
	router.GET("/product/:id", handler.GetProduct)
	router.PUT("/product/:id", handler.UpdateProduct)
	router.DELETE("/product/:id", handler.DeleteProduct)

	router.Run(":82")

}
