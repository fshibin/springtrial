package com.thefans.sdjdbc4mysql.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.thefans.sdjdbc4mysql.model.Product;

@Repository 
public interface ProductRepository extends CrudRepository<Product, Long> {
	List<Product> findByName(String name);

	@Modifying
    @Query("UPDATE product SET name = :name WHERE id = :id")
    boolean updateByName(@Param("id") Long id, @Param("name") String name);
}