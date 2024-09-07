package com.pfa.project.ServiceImpl;

import com.pfa.project.Dto.GetChapterDto;
import com.pfa.project.Dto.PageRequestDto;
import com.pfa.project.Dto.RequestCategory;
import com.pfa.project.Entities.Category;
import com.pfa.project.Entities.Chapter;
import com.pfa.project.Entities.Enum.Status;
import com.pfa.project.Repository.CategoryRepository;
import com.pfa.project.Service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl  implements CategoryService {
    @Autowired
    private final CategoryRepository categoryRepository;


    @Override
    public RequestCategory createCategory(RequestCategory requestCategory, MultipartFile imageFile) throws IOException {

        Category category = new Category();
        category.setName(requestCategory.getName());
        category.setDescription(requestCategory.getDescription());
        category.setStatus(requestCategory.getStatus());
        category.setImg(imageFile.getBytes());



        return categoryRepository.save(category).getDto();
    }
    @Override
    public List<RequestCategory> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(Category::getDto).collect(Collectors.toList());
    }
    public RequestCategory changeStatus(Long id, String status) {
        Optional<Category> optional = categoryRepository.findById(id);
        if (optional.isPresent() && (status.equalsIgnoreCase("Active") || status.equalsIgnoreCase("Inactive"))) {
            Category c = optional.get();
            c.setStatus(Status.valueOf(status.toUpperCase()));
            return categoryRepository.save(c).getDto();
        }
        return null;
    }
    @Override
    public RequestCategory getCategoryById(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        Category category = categoryOptional.orElseThrow(() -> new EntityNotFoundException("category not found with id: " + id));
        return category.getDtoUser();
    }


    @Override
    public RequestCategory updateCategory(Long id, RequestCategory requestCategory) throws IOException {
        Optional<Category> categoryOptional = categoryRepository.findById(id);

        if (categoryOptional.isPresent()) {
            Category updatedCategory = categoryOptional.get();

            if (requestCategory.getName() != null){
                updatedCategory.setName(requestCategory.getName());

            }
            if (requestCategory.getDescription() != null){
                updatedCategory.setDescription(requestCategory.getDescription());

            }

            if (requestCategory.getStatus() != null){
                updatedCategory.setStatus(requestCategory.getStatus());

            }


            if (requestCategory.getImg() != null) {
                updatedCategory.setImg(requestCategory.getImg().getBytes());
            }

            Category savedCourse = categoryRepository.save(updatedCategory);
            return savedCourse.getDto();
        } else {
            throw new EntityNotFoundException("Course or Category not found");
        }
    }

    @Override
    public List<RequestCategory> getAllCategoryByName(String name) {
        List<Category> categories = categoryRepository.findByNameContaining(name);
        return categories.stream().map(Category::getDto).collect(Collectors.toList());    }

    @Override
    public boolean deleteCategory(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Page<Category> findAllCategoriesPagination(PageRequestDto dto) {
        Pageable pageable = dto.getPageable(dto);
        return categoryRepository.findAll(pageable);
    }
}
