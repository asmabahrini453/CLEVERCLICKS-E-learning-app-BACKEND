package com.pfa.project.ServiceImpl;

import com.pfa.project.Dto.*;
import com.pfa.project.Entities.*;
import com.pfa.project.Entities.Enum.OrderStatus;
import com.pfa.project.Entities.Enum.Role;
import com.pfa.project.Entities.Enum.Status;
import com.pfa.project.Repository.QuizRepository;
import com.pfa.project.Repository.UserRepository;
import com.pfa.project.Service.UserService;
import com.pfa.project.auth.UserAuth.RequestUserUpdate;
import com.pfa.project.auth.UserAuth.ResponseUser;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> getAllUsers() {
        List<User> user = userRepository.findAll();
        return user.stream().map(User::getDto).collect(Collectors.toList());
    }
    public UserDto getUserDetailsById(Long id){
        Optional<User> userOptional = userRepository.findById(id);
        User user = userOptional.orElseThrow(() -> new EntityNotFoundException("user not found with id: " + id));
        return user.getDto();
    }
    @Override
    public void updateProfile(Long id, RequestUserUpdate requestUserUpdate) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setName(requestUserUpdate.getName());
        user.setEmail(requestUserUpdate.getEmail());

        userRepository.save(user);
    }

    @Override
    public void updateProfilePicture(Long id, MultipartFile profilePicture) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        try {
            byte[] bytes = profilePicture.getBytes();
            user.setImg(bytes);
            userRepository.save(user);
        } catch (IOException e) {
            throw new RuntimeException("Failed to update profile picture", e);
        }
    }













   /* @Override
    public RequestUserUpdate updateUser(Long id, RequestUserUpdate requestUserUpdate) throws IOException {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User updatedUser = userOptional.get();

            if (requestUserUpdate.getName() != null) {
                updatedUser.setName(requestUserUpdate.getName());
            }
            if (requestUserUpdate.getEmail() != null) {
                updatedUser.setEmail(requestUserUpdate.getEmail());
            }


            User saveduser = userRepository.save(updatedUser);
            return saveduser.getUpdatedDto();
        } else {
            throw new EntityNotFoundException("user not found with id: " + id);
        }
    }*/
    public UserDto changeStatus(Long id, String status) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent() && (status.equalsIgnoreCase("Active") || status.equalsIgnoreCase("Inactive"))) {
            User user = optional.get();
            user.setStatus(Status.valueOf(status.toUpperCase()));
            return userRepository.save(user).getDto();
        }
        return null;
    }

    public User registerNewUserServiceMethod(String name, String email, String password){
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setStatus(Status.ACTIVE);
        return userRepository.save(newUser);
    }

  /*  public List<String> checkUserEmail(String email){
        return userRepository.checkUserEmail(email);
    }
    // End Of Check User Email Services Method.

    public String checkUserPasswordByEmail(String email){
        return userRepository.checkUserPasswordByEmail(email);
    }
    // End Of Check User Password Services Method.

  */
  public User getUserDetailsByEmail(String email){
      return userRepository.GetUserDetailsByEmail(email);
  }

  /*  @Override
    public UserDto updateProfile(Long id, RequestUserUpdate requestUserUpdate) {
        User updatedUser = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
        if (requestUserUpdate.getName() != null) {
            updatedUser.setName(requestUserUpdate.getName());
        }
        if (requestUserUpdate.getEmail() != null) {
            updatedUser.setEmail(requestUserUpdate.getEmail());
        }

        // Save the updated user back to the repository
        User savedUser = userRepository.save(updatedUser);

        // Convert the savedUser entity to a UserDto object
        return convertToUserDto(savedUser);
    }
*/
  /*  private UserDto convertToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }*/


    @Override
    public void changePassword(Long id, RequestChangePassword requestChangePassword) {
       User user = userRepository.findById(id).orElseThrow();
        if(!passwordEncoder.matches(requestChangePassword.getOldPassword() , user.getPassword())){
            throw new RuntimeException();
        }
        user.setPassword((passwordEncoder.encode(requestChangePassword.getNewPassword())));
        userRepository.save(user);
    }

    @Override
    public boolean deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<UserDto> getAllUserByName(String name) {
        List<User> users = userRepository.findByNameContaining(name);
        return users.stream().map(User::getDto).collect(Collectors.toList());
    }

}
