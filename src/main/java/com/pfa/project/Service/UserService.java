package com.pfa.project.Service;

import com.pfa.project.Dto.RequestAdminUpdate;
import com.pfa.project.Dto.RequestCategory;
import com.pfa.project.Dto.RequestChangePassword;
import com.pfa.project.Dto.UserDto;
import com.pfa.project.Entities.Admin;
import com.pfa.project.Entities.User;
import com.pfa.project.auth.UserAuth.RequestUserUpdate;
import com.pfa.project.auth.UserAuth.ResponseUser;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();

    UserDto changeStatus(Long id , String status);


    UserDto getUserDetailsById(Long id);
    void updateProfile(Long id, RequestUserUpdate requestUserUpdate);
    void updateProfilePicture(Long id, MultipartFile profilePicture);
    void changePassword(Long id, RequestChangePassword requestChangePassword);

    boolean deleteUser(Long id) ;

    List<UserDto> getAllUserByName(String name);
    User getUserDetailsByEmail(String email);

    // UserDto updateProfile(Long id, RequestUserUpdate requestUserUpdate);
    // UserDto updateUserWithImage(Long id, UserDto userDto) throws IOException;
    // RequestUserUpdate updateUser(Long id, RequestUserUpdate requestUserUpdate) throws IOException;
     /* User registerNewUserServiceMethod(String name, String email, String password);
     List<String> checkUserEmail(String email);
     String checkUserPasswordByEmail(String email);*/


}
