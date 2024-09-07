package com.pfa.project.auth.UserAuth;

import com.pfa.project.Entities.Enum.Status;
import com.pfa.project.Entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponse {
    private long id;
    private String name;
    private String email;
    private String password;
    private String role;
    private Status status;
    private String createdAt;
    private String updatedAt;

    public LoginResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.status = user.getStatus();
        this.createdAt = user.getCreatedAt().toString();
        this.updatedAt = user.getUpdatedAt().toString();
    }
}
