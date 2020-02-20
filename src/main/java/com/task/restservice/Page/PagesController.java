package com.task.restservice.Page;

import com.task.restservice.Category.CategoryNotFoundException;
import com.task.restservice.Category.CategoryRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;

@RestController
public class PagesController {
    @Autowired
    private Logger logger;

    @Autowired
    private PagesRepository pagesRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping(path = "/categories/{catId}/pages")
    public @ResponseBody Pages savePage(@PathVariable Integer catId, @Valid @RequestBody Pages pages) {
        return categoryRepository.findById(catId).map( category -> {
            pages.setCategory(category);
            return pagesRepository.save(pages);
        }).orElseThrow(() -> new CategoryNotFoundException("Category id " + catId + " not found"));
    }

    @GetMapping(path = "/categories/{catId}/pages")
    public @ResponseBody Set<Pages> getAll(@PathVariable Integer catId) {
        return pagesRepository.findByCategoryId(catId);
    }

    @GetMapping(path = "/pages/{id}")
    public @ResponseBody Optional<Pages> getByID(@PathVariable Integer id) {
        return pagesRepository.findById(id);
    }

    @DeleteMapping(path = "/pages/{id}/delete")
    public @ResponseBody ResponseEntity<?> deleteCategory(@PathVariable Integer id) {
        try {
            pagesRepository.deleteById(id);
        }
        catch (IllegalArgumentException e) {
            logger.error("Category delete failed: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
        catch (EmptyResultDataAccessException e) {
            logger.error("Requested category with id {} not found: {}", id.toString(), e.getMessage());
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/pages/{id}")
    public @ResponseBody ResponseEntity<?> updateCategory(@RequestBody Pages pagesToUpdate, @PathVariable Integer id) {
        Pages updatedPages = pagesRepository.findById(id)
                .map(page -> {
                    page.setName(pagesToUpdate.getName());
                    page.setContent(pagesToUpdate.getContent());
                    return pagesRepository.save(page);
                }).orElseGet(() -> {
                    pagesToUpdate.setId(id);
                    return pagesRepository.save(pagesToUpdate);
                });

        return ResponseEntity.noContent().build();
    }

}