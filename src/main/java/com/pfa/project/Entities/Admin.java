package com.pfa.project.Entities;

import com.pfa.project.Dto.RequestAdmin;
import com.pfa.project.Dto.RequestAdminUpdate;
import com.pfa.project.Dto.RequestCourse;
import com.pfa.project.Entities.Enum.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String phone;
    private String description;
    private String email;
    private String password;
    private Boolean status;
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;
    @Enumerated(EnumType.ORDINAL)
    private Role role;
    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;
    @Transient
    private String processedImg;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private List<Course> course;

    public RequestAdminUpdate getUpdatedDto() {
        RequestAdminUpdate admin = new RequestAdminUpdate();
        admin.setId(id);
        admin.setUsername(username);
        admin.setEmail(email);
        admin.setFirstName(firstName);
        admin.setLastName(lastName);
        admin.setPhone(phone);
        admin.setDescription(description);
        admin.setStatus(true);
        admin.setByteImg(img);

        return admin;
    }
    public RequestAdmin getDto() {
        RequestAdmin admin = new RequestAdmin();
        admin.setId(id);
        admin.setUsername(username);
        admin.setEmail(email);
        admin.setPassword(password);
        admin.setFirstName(firstName);
        admin.setLastName(lastName);
        admin.setPhone(phone);
        admin.setDescription(description);
        admin.setRole(role);
        admin.setStatus(true);
        admin.setByteImg(img);

        return admin;
    }
}
