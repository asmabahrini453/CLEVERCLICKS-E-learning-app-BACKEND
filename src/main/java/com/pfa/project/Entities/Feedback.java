package com.pfa.project.Entities;
import com.pfa.project.Dto.*;
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
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment ;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    public FeedbackDto getDto() {
        FeedbackDto u = new FeedbackDto();
        u.setId(id);
        u.setComment(comment);
        if (user != null) {
            u.setUserId(user.getId());
        }
        if (admin != null) {
            u.setAdminId(admin.getId());
        }

        return u;
    }


}
