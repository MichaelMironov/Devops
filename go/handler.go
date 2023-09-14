package main

import (
	"fmt"
	"net/http"
	"strconv"

	"github.com/gin-gonic/gin"
)

type ErrorResponse struct {
	Message string `json:"message"`
}

type Handler struct {
	storage Storage
}

func NewHandler(storage Storage) *Handler {
	return &Handler{storage: storage}
}

func (h *Handler) CreateProduct(con *gin.Context) {

	var product Product

	if err := con.BindJSON(&product); err != nil {
		fmt.Printf("Failed to bind product: %s\n", err.Error())
		con.JSON(http.StatusBadRequest, ErrorResponse{
			Message: err.Error(),
		})
		return
	}
	h.storage.Insert(&product)

	con.JSON(http.StatusOK, map[string]interface{}{
		"id": product.ID,
	})
}

func (h *Handler) UpdateProduct(con *gin.Context) {
	id, err := strconv.Atoi(con.Param("id"))
	if err != nil {
		fmt.Printf("Failed to convert id param to int: %s\n", err.Error())
		con.JSON(http.StatusBadRequest, ErrorResponse{
			Message: err.Error(),
		})
		return
	}

	var product Product

	if err := con.BindJSON(&product); err != nil {
		fmt.Printf("Failed to bind product: %s\n", err.Error())
		con.JSON(http.StatusBadRequest, ErrorResponse{
			Message: err.Error(),
		})
		return
	}

	h.storage.Update(id, product)

	con.JSON(http.StatusOK, map[string]interface{}{
		"id": product.ID,
	})
}

func (h *Handler) GetProduct(con *gin.Context) {
	id, err := strconv.Atoi(con.Param("id"))
	if err != nil {
		fmt.Printf("Failed to convert param to int: %s\n", err.Error())
		con.JSON(http.StatusBadRequest, ErrorResponse{
			Message: err.Error(),
		})

		return
	}

	product, err := h.storage.Get(id)

	if err != nil {
		fmt.Printf("Failed to get product: %s\n", err.Error())
		con.JSON(http.StatusBadRequest, ErrorResponse{
			Message: err.Error(),
		})
		return
	}

	con.JSON(http.StatusOK, product)
}

func (h *Handler) DeleteProduct(con *gin.Context) {
	id, err := strconv.Atoi(con.Param("id"))
	if err != nil {
		fmt.Printf("Failed to convert id param to int: %s\n", err.Error())
		con.JSON(http.StatusBadRequest, ErrorResponse{
			Message: err.Error(),
		})
		return
	}

	h.storage.Delete(id)

	con.String(http.StatusOK, "Product deleted")
}
