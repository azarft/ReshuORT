package com.alatoo.reshu_ort.services.user;

import com.alatoo.reshu_ort.dto.UserDTO;
import com.alatoo.reshu_ort.dto.authorization.AuthRegistrationDTO;
import com.alatoo.reshu_ort.entities.User;

public interface UserService {
    UserDTO saveUser(UserDTO userDTO);
    UserDTO register(AuthRegistrationDTO authRegistrationDTO);
    User getCurrentUser();
    void deleteUser();
}
