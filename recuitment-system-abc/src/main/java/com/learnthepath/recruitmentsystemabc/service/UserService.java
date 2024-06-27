package com.learnthepath.recruitmentsystemabc.service;

import com.learnthepath.recruitmentsystemabc.dto.UserDto;
import com.learnthepath.recruitmentsystemabc.entity.RoleEntity;
import com.learnthepath.recruitmentsystemabc.entity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface UserService {
    void saveUser(UserDto userDto);

    UserEntity findByUsername(String username);

    boolean checkUserExist(String username);

    List<UserDto> findAllUsers();

    String determineRedirectUrl(Authentication authentication);

    void updateRole(Integer userId, Set<RoleEntity> newRoles);

    Integer getCurrentUserId();
}
