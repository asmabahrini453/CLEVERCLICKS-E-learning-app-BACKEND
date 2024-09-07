package com.pfa.project.Repository;

import com.pfa.project.Entities.Category;
import com.pfa.project.Entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category , Long> {
    List<Category> findByNameContaining(String name);

}
