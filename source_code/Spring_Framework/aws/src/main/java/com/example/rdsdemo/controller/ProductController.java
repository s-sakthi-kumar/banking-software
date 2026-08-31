package com.example.rdsdemo.controller;

import com.example.rdsdemo.entity.Product;
import com.example.rdsdemo.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Simple CRUD REST controller that exercises the RDS connection.
 *
 * Endpoints:
 *   GET    /api/products          - list all products
 *   GET    /api/products/{id}     - get one product
 *   GET    /api/products?name=x   - search by name
 *   POST   /api/products          - create a product
 *   PUT    /api/products/{id}     - update a product
 *   DELETE /api/products/{id}     - delete a product
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository repo;

    public ProductController(ProductRepository repo) {
        this.repo = repo;
    }

    // ── READ ──────────────────────────────────────────────────────────────────

    @GetMapping
    public List<Product> list(@RequestParam(required = false) String name) {
        if (name != null && !name.isBlank()) {
            return repo.findByNameContainingIgnoreCase(name);
        }
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return repo.findById(id)
                   .map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // ── CREATE ────────────────────────────────────────────────────────────────

    @PostMapping
    public Product create(@RequestBody Product product) {
        return repo.save(product);
    }

    // ── UPDATE ────────────────────────────────────────────────────────────────

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id,
                                          @RequestBody Product incoming) {
        return repo.findById(id)
                   .map(existing -> {
                       existing.setName(incoming.getName());
                       existing.setDescription(incoming.getDescription());
                       existing.setPrice(incoming.getPrice());
                       return ResponseEntity.ok(repo.save(existing));
                   })
                   .orElse(ResponseEntity.notFound().build());
    }

    // ── DELETE ────────────────────────────────────────────────────────────────

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
