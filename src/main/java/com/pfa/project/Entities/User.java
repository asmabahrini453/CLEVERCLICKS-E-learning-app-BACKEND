package com.pfa.project.Entities;

import com.pfa.project.Dto.RequestAdminUpdate;
import com.pfa.project.Dto.UserDto;
import com.pfa.project.Entities.Enum.Role;
import com.pfa.project.Entities.Enum.Status;
import com.pfa.project.Entities.token.Token;
import com.pfa.project.auth.UserAuth.RequestUserUpdate;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.ORDINAL)
    private Role role;
    @Enumerated(EnumType.ORDINAL)
    private Status status;
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;
    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Token> tokens;

    public UserDto getDto() {
        UserDto user = new UserDto();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setCreatedAt(createdAt);
        user.setRole(role);
        user.setStatus(Status.ACTIVE);
        user.setByteImg(img);

        return user;
    }
    public RequestUserUpdate getUpdatedDto() {
        RequestUserUpdate u = new RequestUserUpdate();
        u.setId(id);
        u.setName(name);
        u.setEmail(email);

        return u;
    }



    // private boolean mfaEnabled; //to check if he enables 2FA
    // private String secret; //for 2FA

    // getAuthorities(): returns a list of roles
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
