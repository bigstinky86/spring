package com.task.restservice.Category;

import com.task.restservice.Page.PagesRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class CategoryController {
    @Autowired
    private Logger logger;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PagesRepository pagesRepository;

    @PostMapping(path = "/categories")
    public @ResponseBody ResponseEntity<?> add(@RequestBody Category category) {
        try {
            categoryRepository.save(category);
        }
        catch (IllegalArgumentException e) {
            logger.error("Category add failed: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(category);
    }

    @GetMapping(path = "/categories")
    public @ResponseBody Iterable<Category> getAll() {
        return categoryRepository.findAll();
    }

    @GetMapping(path = "/categories/{id}")
    public @ResponseBody Category getByID(@PathVariable Integer id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category with id " + id.toString() + " not found"));
    }

    @DeleteMapping(path = "/categories/{id}/delete")
    public @ResponseBody ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            categoryRepository.deleteById(id);
        }
        catch (IllegalArgumentException e) {
            logger.error("Category delete failed: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
        catch (EmptyResultDataAccessException e) {
            logger.error("Category with id {} doesn't exist: {}", id.toString(), e.getMessage());
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/categories/{id}")
    public @ResponseBody ResponseEntity<?> update(@RequestBody Category categoryToUpdate, @PathVariable Integer id) {
        Category updatedCategory = categoryRepository.findById(id)
                .map(category -> {
                    category.setName(categoryToUpdate.getName());
                    return categoryRepository.save(category);
                }).orElseGet(() -> {
                    categoryToUpdate.setId(id);
                    return categoryRepository.save(categoryToUpdate);
                });

        return ResponseEntity.noContent().build();
    }

}
