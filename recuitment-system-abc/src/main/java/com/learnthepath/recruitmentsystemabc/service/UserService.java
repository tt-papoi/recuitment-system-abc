package com.learnthepath.recruitmentsystemabc.service;

import com.learnthepath.recruitmentsystemabc.dto.UserDto;
import com.learnthepath.recruitmentsystemabc.entity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    void saveUser(UserDto userDto);

    UserEntity findUserByUsername(String username);

    boolean checkUserExist(String username);

    List<UserDto> findAllUsers();

    UserDto mapToUserDto(UserEntity userEntity);

    String determineRedirectUrl(Authentication authentication);
}
