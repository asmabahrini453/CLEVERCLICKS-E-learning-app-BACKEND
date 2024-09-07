package com.pfa.project.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pfa.project.Dto.UserDto;
import com.pfa.project.Entities.Enum.Status;
import com.pfa.project.Entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
//so when the user doesn't want the 2fa,
// the secretImgUri will be empty so the user won't see that attribute

public class AuthenticationResponse {
    private String token ;
    private long id;
    private String name;
    private String email;
    private String password;
    private String role;
    private Status status;


    public AuthenticationResponse(User user, String token) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.status = user.getStatus();

        this.token = token;
    }

}
