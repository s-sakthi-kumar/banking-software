package com.example.rdsdemo.repository;

import com.example.rdsdemo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /** Find all products whose name contains the given string (case-insensitive). */
    List<Product> findByNameContainingIgnoreCase(String name);
}
