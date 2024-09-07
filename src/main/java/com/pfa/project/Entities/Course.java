package com.pfa.project.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pfa.project.Dto.RequestCourse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String rate;
    private Long price;
    private Boolean status;
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;

    @Transient
    private String processedImg;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "admin_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Admin admin;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Category category;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Chapter> chapter;

    /* @OneToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "image_id")
   private Image img;*/


    public RequestCourse getDto() {
        RequestCourse course = new RequestCourse();
        course.setId(id);
        course.setTitle(title);
        course.setDescription(description);
        course.setRate(rate);
        course.setPrice(price);
        course.setStatus(true);
        course.setByteImg(img);

        if (category != null) {
            course.setCategoryId(category.getId());
            course.setCategoryName(category.getName());
        }

        if (admin != null) {
            course.setAdminId(admin.getId());
        }

        return course;
    }
}
