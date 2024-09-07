package com.pfa.project.Entities;

import com.pfa.project.Dto.RequestCategory;
import com.pfa.project.Dto.RequestCourse;
import com.pfa.project.Entities.Enum.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Base64;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name ;
    private String description;
    @Enumerated(EnumType.ORDINAL)
    private Status status;
    @CreationTimestamp
    private Instant createdAt ;
    @UpdateTimestamp
    private Instant updatedAt ;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;
    @Transient
    private String processedImg;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Course> courses;

    public RequestCategory getDto() {
        RequestCategory category = new RequestCategory();
        category.setId(id);
        category.setName(name);
        category.setDescription(description);
        category.setStatus(status);
        category.setByteImg(img);

        return category;
    }
    public RequestCategory getDtoUser() {
        RequestCategory category = new RequestCategory();
        category.setId(id);
        category.setName(name);
        category.setDescription(description);
        category.setStatus(status);

        try {
            byte[] byteImgData = img;
            category.setByteImg(byteImgData);
        } catch (Exception e) {
            category.setByteImg(new byte[0]);
        }

        return category;
    }



}
