package com.pfa.project.ServiceImpl;

import com.pfa.project.Dto.RequestAdmin;
import com.pfa.project.Dto.RequestAdminUpdate;
import com.pfa.project.Dto.RequestChangePassword;
import com.pfa.project.Entities.Admin;
import com.pfa.project.Repository.AdminRepository;
import com.pfa.project.Service.AdminService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<RequestAdmin> getAllAdmins() {
        List<Admin> admins = adminRepository.findAll();
        return admins.stream().map(Admin::getDto).collect(Collectors.toList());
    }

    @Override
    public RequestAdmin getAdminById(Long id) {
        Optional<Admin> adminOptional = adminRepository.findById(id);
        Admin admin = adminOptional.orElseThrow(() -> new EntityNotFoundException("Admin not found with id: " + id));
        return admin.getDto();
    }

    @Override
    public RequestAdmin createAdmin(RequestAdmin requestAdmin, MultipartFile imageFile) throws IOException {
        Admin admin = new Admin();
        admin.setUsername(requestAdmin.getUsername());
        admin.setEmail(requestAdmin.getEmail());
        admin.setPassword(passwordEncoder.encode(requestAdmin.getPassword()));
        admin.setRole(requestAdmin.getRole());
        admin.setDescription(requestAdmin.getDescription());
        admin.setFirstName(requestAdmin.getFirstName());
        admin.setLastName(requestAdmin.getLastName());
        admin.setPhone(requestAdmin.getPhone());
        admin.setStatus(requestAdmin.getStatus());
        admin.setImg(imageFile.getBytes());

        return adminRepository.save(admin).getDto();
    }

    @Override
    public RequestAdminUpdate updateAdmin(Long id, RequestAdminUpdate requestAdminUpdate) throws IOException {
        Optional<Admin> adminOptional = adminRepository.findById(id);

        if (adminOptional.isPresent()) {
            Admin updatedAdmin = adminOptional.get();

            if (requestAdminUpdate.getUsername() != null) {
                updatedAdmin.setUsername(requestAdminUpdate.getUsername());
            }
            if (requestAdminUpdate.getFirstName() != null) {
                updatedAdmin.setFirstName(requestAdminUpdate.getFirstName());
            }
            if (requestAdminUpdate.getLastName() != null) {
                updatedAdmin.setLastName(requestAdminUpdate.getLastName());
            }
            if (requestAdminUpdate.getPhone() != null) {
                updatedAdmin.setPhone(requestAdminUpdate.getPhone());
            }

            if (requestAdminUpdate.getDescription() != null) {
                updatedAdmin.setDescription(requestAdminUpdate.getDescription());
            }
            if (requestAdminUpdate.getEmail() != null) {
                updatedAdmin.setEmail(requestAdminUpdate.getEmail());
            }
            if (requestAdminUpdate.getStatus() != null) {
                updatedAdmin.setStatus(requestAdminUpdate.getStatus());
            }
            if (requestAdminUpdate.getImg() != null) {
                updatedAdmin.setImg(requestAdminUpdate.getImg().getBytes());
            }

            Admin savedAdmin = adminRepository.save(updatedAdmin);
            return savedAdmin.getUpdatedDto();
        } else {
            throw new EntityNotFoundException("Admin not found with id: " + id);
        }
    }

    @Override
    public void changePassword(Long id, RequestChangePassword requestChangePassword) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Admin not found with id: " + id));

        if (!passwordEncoder.matches(requestChangePassword.getOldPassword(), admin.getPassword())) {
            throw new RuntimeException("Old password does not match.");
        }

        admin.setPassword(passwordEncoder.encode(requestChangePassword.getNewPassword()));
        adminRepository.save(admin);
    }
}
