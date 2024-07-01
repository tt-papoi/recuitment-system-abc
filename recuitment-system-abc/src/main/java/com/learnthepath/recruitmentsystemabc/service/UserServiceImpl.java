package com.learnthepath.recruitmentsystemabc.service;

import com.learnthepath.recruitmentsystemabc.dto.UserDto;
import com.learnthepath.recruitmentsystemabc.entity.RoleEntity;
import com.learnthepath.recruitmentsystemabc.entity.UserEntity;
import com.learnthepath.recruitmentsystemabc.repository.RoleRepository;
import com.learnthepath.recruitmentsystemabc.repository.UserRepository;
import com.learnthepath.recruitmentsystemabc.security.CustomUserDetails;
import com.learnthepath.recruitmentsystemabc.security.CustomUserDetailsService;
import com.learnthepath.recruitmentsystemabc.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

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
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean checkUserExist(String username) {
        return findByUsername(username) != null;
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<UserEntity> userEntityList = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for (UserEntity userEntity : userEntityList) {
            userDtoList.add(Utils.mapToDto(userEntity));
        }
        return userDtoList;
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
            return "/candidate/jobs";
        } else if (roles.contains("NONE")) {
            return "/register-member/home";
        } else {
            return "/";
        }
    }

    @Override
    public void updateRole(Integer userId, Set<RoleEntity> newRoles) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Update roles
        user.setRoles(newRoles);

        // Save the updated user entity
        userRepository.save(user);

        // Reload user details and update security context
        customUserDetailsService.reloadUserDetails(user.getUsername());
    }

    @Override
    public Integer getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                CustomUserDetails userDetails = (CustomUserDetails) principal;
                return userDetails.getId();  // Assuming the username is the user ID
            }
        }
        return null;
    }
}
