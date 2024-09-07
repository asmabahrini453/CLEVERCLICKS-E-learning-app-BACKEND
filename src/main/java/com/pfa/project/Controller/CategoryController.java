package com.pfa.project.Controller;

import com.pfa.project.Dto.*;
import com.pfa.project.Entities.Category;
import com.pfa.project.Repository.CategoryRepository;
import com.pfa.project.Service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/pfa/category")
@AllArgsConstructor
public class CategoryController {
    @Autowired
    private final CategoryService categoryService ;

    @GetMapping("")
    public ResponseEntity<List<RequestCategory>> getAllCategories() {
        List<RequestCategory> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("{id}")
    public ResponseEntity<RequestCategory> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }
    @PostMapping("")
    public ResponseEntity<RequestCategory> addCategory(@Valid RequestCategory requestCategory,
                                                   @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        RequestCategory createdCategory = categoryService.createCategory(requestCategory, imageFile);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdCategory);
    }

    @PutMapping("{id}")
    public ResponseEntity<RequestCategory> updateCategory(
            @PathVariable Long id,
            @ModelAttribute RequestCategory requestCategory
    ) {
        try {
            RequestCategory updatedCategory = categoryService.updateCategory(id, requestCategory);
            return ResponseEntity.ok(updatedCategory);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        boolean deletedCategory = categoryService.deleteCategory(id);
        if (deletedCategory) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<RequestCategory>> getAllCategoryByName(@PathVariable String name) {
        List<RequestCategory> categories = categoryService.getAllCategoryByName(name);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}/{status}")
    public ResponseEntity<?> changeStatus(@PathVariable Long id,@PathVariable String status){
        RequestCategory dto = categoryService.changeStatus(id,status);
        if(dto == null){
            return new ResponseEntity<>("error" , HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
    @Autowired
    private CategoryRepository categoryRepository ;

    @PostMapping("/pagination")
    public ResponseEntity<Page<Category>> getAllCategoriesUsingPagination(@RequestBody PageRequestDto dto){
        Page<Category> categoryPage = categoryService.findAllCategoriesPagination(dto);
        return ResponseEntity.ok(categoryPage);
    }
}
