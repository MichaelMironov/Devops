package main

import (
	"errors"
	"sync"
)

type Product struct {
	ID          int    `json:"id"`
	Name        string `json:"name"`
	Description string `json:"description"`
}

type Storage interface {
	Insert(p *Product)
	Get(id int) (Product, error)
	Update(id int, p Product)
	Delete(id int)
}

type ProductsStorage struct {
	counter int
	product map[int]Product
	sync.Mutex
}

func NewProductsStorage() *ProductsStorage {
	return &ProductsStorage{
		product: make(map[int]Product),
		counter: 1,
	}
}

func (s *ProductsStorage) Insert(p *Product) {
	s.Lock()

	p.ID = s.counter
	s.product[p.ID] = *p

	s.counter++

	s.Unlock()
}

func (s *ProductsStorage) Get(id int) (Product, error) {
	s.Lock()
	defer s.Unlock()

	product, ok := s.product[id]
	if !ok {
		return product, errors.New("Product not found")
	}

	return product, nil
}

func (s *ProductsStorage) Update(id int, p Product) {
	s.Lock()
	s.product[id] = p
	s.Unlock()
}

func (s *ProductsStorage) Delete(id int) {
	s.Lock()
	delete(s.product, id)
	s.Unlock()
}
