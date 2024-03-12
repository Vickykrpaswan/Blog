package com.blog.controllers;

import com.blog.payloads.CategoryDto;
import com.blog.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    // create
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto category=this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    // update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable("categoryId") Integer categoryId){
        CategoryDto updatedCategory =this.categoryService.updateCategory(categoryDto, categoryId);
        return new ResponseEntity<>(updatedCategory,HttpStatus.OK);
    }

    // get
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer categoryId){
        return new ResponseEntity<>(this.categoryService.getCategory(categoryId),HttpStatus.OK);
    }

    // get All
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getCategories(){
        return new ResponseEntity<>(this.categoryService.getCategories(),HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") Integer categoryId){
        this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(Map.of("message","category delete successfully"),HttpStatus.OK);
    }
}
