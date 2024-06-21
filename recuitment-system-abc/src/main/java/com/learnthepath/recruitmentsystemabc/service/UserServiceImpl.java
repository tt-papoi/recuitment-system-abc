package com.learnthepath.recruitmentsystemabc.service;

import com.learnthepath.recruitmentsystemabc.dto.UserDto;
import com.learnthepath.recruitmentsystemabc.entity.RoleEntity;
import com.learnthepath.recruitmentsystemabc.entity.UserEntity;
import com.learnthepath.recruitmentsystemabc.repository.RoleRepository;
import com.learnthepath.recruitmentsystemabc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDto.getUsername());
        // encrypt the password using spring security
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));

        RoleEntity roleEntity = roleRepository.findByName("NONE");
        userEntity.setRoles(Collections.singleton(roleEntity));
        userRepository.save(userEntity);
    }

    @Override
    public UserEntity findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean checkUserExist(String username) {
        return findUserByUsername(username) != null;
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<UserEntity> userEntityList = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for(UserEntity userEntity : userEntityList) {
            userDtoList.add(mapToUserDto(userEntity));
        }
        return userDtoList;
    }

    @Override
    public UserDto mapToUserDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setUsername(userEntity.getUsername());
        return userDto;
    }

    @Override
    public String determineRedirectUrl(Authentication authentication) {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ADMIN")) {
            return "/admin/home";
        } else if (roles.contains("LEADERSHIP")) {
            return "/leadership/home";
        } else if (roles.contains("ENTERPRISE")) {
            return "/enterprise/home";
        } else if (roles.contains("CANDIDATE")) {
            return "/candidate/home";
        } else if (roles.contains("NONE")) {
            return "/register-member/home";
        } else {
            return "/";
        }
    }
}
