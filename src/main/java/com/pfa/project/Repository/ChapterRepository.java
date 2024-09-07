package com.pfa.project.Repository;

import com.pfa.project.Entities.Category;
import com.pfa.project.Entities.Chapter;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter , Long> {

    List<Chapter> findAllByCourseId(Long courseId);
    List<Chapter> findByTitleContaining(String title);

}
