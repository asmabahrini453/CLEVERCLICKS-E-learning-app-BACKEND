package com.pfa.project.Entities;

import com.pfa.project.Dto.GetChapterDto;
import com.pfa.project.Dto.ImageData;
import com.pfa.project.Dto.RequestChapter;
import com.pfa.project.Dto.RequestCourse;
import com.pfa.project.Entities.Enum.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ElementCollection
    private List<String> contents;

    @Enumerated(EnumType.ORDINAL)
    private Status status;


    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL)
    private List<Quiz> quizzes;

    @OneToMany(mappedBy = "chapter", cascade = CascadeType.REMOVE)
    private List<ChapterImage> images;

    public GetChapterDto getDto() {
        GetChapterDto chapter = new GetChapterDto();
        chapter.setId(id);
        chapter.setTitle(title);
        chapter.setContents(contents);
        chapter.setStatus(status);
        if (course != null) {
            chapter.setCourseId(course.getId());
        }

        if (images != null) {
            List<Long> imageIds = new ArrayList<>();
            for (ChapterImage image : images) {
                imageIds.add(image.getId());
            }
            chapter.setImageIds(imageIds);
        }
        if (images != null) {
            List<ImageData> imageDataList = new ArrayList<>();
            for (ChapterImage image : images) {
                ImageData imageData = new ImageData();
                imageData.setId(image.getId());
                imageData.setByteImg(Base64.getEncoder().encodeToString(image.getImage()));
                imageDataList.add(imageData);
            }
            chapter.setImages(imageDataList);
        }

        return chapter;
    }
    public RequestChapter getDto2() {
        RequestChapter chapter = new RequestChapter();
        chapter.setId(id);
        chapter.setTitle(title);
        chapter.setContents(contents);
        chapter.setStatus(status);
        if (course != null) {
            chapter.setCourseId(course.getId());
            chapter.setCourseTitle(course.getTitle());
        }

        if (images != null) {
            List<Long> imageIds = new ArrayList<>();
            for (ChapterImage image : images) {
                imageIds.add(image.getId());
            }
            chapter.setImageIds(imageIds);
        }


        return chapter;
    }
}
