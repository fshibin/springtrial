package com.thefans.ecommerce.service;

import java.util.Collection;
import com.thefans.ecommerce.model.Product;

public interface ProductService {
   public abstract Product getProduct(String id);
   public abstract void createProduct(Product product);
   public abstract void updateProduct(String id, Product product);
   public abstract void deleteProduct(String id);
   public abstract Collection<Product> getProducts();
   public abstract boolean hasProduct(String id);
}
