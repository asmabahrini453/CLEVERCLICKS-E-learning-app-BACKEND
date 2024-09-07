package com.pfa.project.Service;

import com.pfa.project.Dto.*;
import com.pfa.project.Entities.Admin;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface AdminService {
    List<RequestAdmin> getAllAdmins()  ;
    RequestAdmin getAdminById(Long id);

    RequestAdmin createAdmin(RequestAdmin requestAdmin, MultipartFile imageFile) throws IOException ;
    RequestAdminUpdate updateAdmin(Long id, RequestAdminUpdate requestAdminUpdate) throws IOException;
    void changePassword(Long id , RequestChangePassword requestChangePassword);

}
