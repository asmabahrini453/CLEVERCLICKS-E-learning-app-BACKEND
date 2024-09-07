package com.pfa.project.Service;

import com.pfa.project.Dto.*;
import com.pfa.project.Entities.Category;
import com.pfa.project.Entities.Chapter;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CategoryService {
    RequestCategory createCategory(RequestCategory requestCategory, MultipartFile imageFile) throws IOException ;
    List<RequestCategory> getAllCategories()   ;
    RequestCategory getCategoryById(Long id);
    RequestCategory updateCategory(Long id, RequestCategory requestCategory) throws IOException;
    List<RequestCategory> getAllCategoryByName(String name);
    boolean deleteCategory(Long id);
    RequestCategory changeStatus(Long id, String status);
    Page<Category> findAllCategoriesPagination(PageRequestDto dto);
}
