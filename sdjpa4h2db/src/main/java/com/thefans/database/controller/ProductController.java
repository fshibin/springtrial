package com.thefans.database.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thefans.database.model.Product;
import com.thefans.database.repository.ProductRepository;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductRepository ProductRepository;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam(required = false) String name) {
        try {
            List<Product> Products = new ArrayList<Product>();

            if (name == null)
                ProductRepository.findAll().forEach(Products::add);
            else
                ProductRepository.findByNameContaining(name).forEach(Products::add);

            if (Products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(Products, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") long id) {
        Optional<Product> ProductData = ProductRepository.findById(id);

        if (ProductData.isPresent()) {
            return new ResponseEntity<>(ProductData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product Product) {
        try {
            Product _Product = ProductRepository
                .save(new Product(Product.getName(), Product.getDescription()));
            return new ResponseEntity<>(_Product, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") long id, @RequestBody Product Product) {
        Optional<Product> ProductData = ProductRepository.findById(id);

        if (ProductData.isPresent()) {
            Product _Product = ProductData.get();
            _Product.setName(Product.getName());
            _Product.setDescription(Product.getDescription());
            return new ResponseEntity<>(ProductRepository.save(_Product), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") long id) {
        try {
            ProductRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/products")
    public ResponseEntity<HttpStatus> deleteAllProducts() {
        try {
            ProductRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}