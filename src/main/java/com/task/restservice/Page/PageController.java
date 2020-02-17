package com.task.restservice.Page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class PageController {
    @Autowired
    private Logger logger;

    @Autowired
    private PageRepository pageRepository;

    @PostMapping(path = "/pages")
    public @ResponseBody ResponseEntity<?> add(@RequestBody Page page) {
        try {
            pageRepository.save(page);
        }
        catch (IllegalArgumentException e) {
            logger.error("Page add failed: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(page);
    }

    @GetMapping(path = "/pages")
    public @ResponseBody Iterable<Page> getAll() {
        return pageRepository.findAll();
    }

    @GetMapping(path = "/pages/{id}")
    public @ResponseBody Optional<Page> getByID(@PathVariable Integer id) {
        return pageRepository.findById(id);
    }

    @DeleteMapping(path = "/pages/{id}/delete")
    public @ResponseBody ResponseEntity<?> deleteCategory(@PathVariable Integer id) {
        try {
            pageRepository.deleteById(id);
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
    public @ResponseBody ResponseEntity<?> updateCategory(@RequestBody Page pageToUpdate, @PathVariable Integer id) {
        Page updatedPage = pageRepository.findById(id)
                .map(page -> {
                    page.setName(pageToUpdate.getName());
                    page.setContent(pageToUpdate.getContent());
                    return pageRepository.save(page);
                }).orElseGet(() -> {
                    pageToUpdate.setId(id);
                    return pageRepository.save(pageToUpdate);
                });

        return ResponseEntity.noContent().build();
    }

}