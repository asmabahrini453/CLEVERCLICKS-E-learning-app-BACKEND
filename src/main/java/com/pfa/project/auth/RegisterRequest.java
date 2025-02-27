package com.pfa.project.auth;

import com.pfa.project.Entities.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RegisterRequest {
    private String name ;
    private String email ;
    private String password ;
    private Role role ;
    private boolean status ;
}
