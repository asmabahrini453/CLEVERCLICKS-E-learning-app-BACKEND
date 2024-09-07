package com.pfa.project.auth.UserAuth;

import com.pfa.project.Entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUser {
    Long id ;
    String name ;
    String email ;
    String password ;

    public static ResponseUser makeUser(User user){
        return ResponseUser.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())

                .build();
    }

}
