package com.learnthepath.recruitmentsystemabc.service;

import com.learnthepath.recruitmentsystemabc.dto.CandidateDto;
import com.learnthepath.recruitmentsystemabc.entity.CandidateEntity;
import com.learnthepath.recruitmentsystemabc.entity.RoleEntity;
import com.learnthepath.recruitmentsystemabc.entity.UserEntity;
import com.learnthepath.recruitmentsystemabc.repository.CandidateRepository;
import com.learnthepath.recruitmentsystemabc.repository.RoleRepository;
import com.learnthepath.recruitmentsystemabc.security.CustomUserDetails;
import com.learnthepath.recruitmentsystemabc.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class CandidateServiceImpl implements CandidateService {
    @Autowired
    private UserService userService;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void saveCandidate(CandidateDto candidateDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // Find the user by username
        UserEntity userEntity = userService.findByUsername(userDetails.getUsername());
        if (userEntity == null) {
            throw new RuntimeException("User not found: " + userDetails.getUsername());
        }

        // Map DTO to entity
        CandidateEntity candidateEntity = Utils.mapToEntity(candidateDto);

        // Set the user entity and synchronize the id
        candidateEntity.setUser(userEntity);
        //enterpriseEntity.setId(userEntity.getId()); // Ensure the id is synchronized

        // Save or update enterpriseEntity
        candidateRepository.save(candidateEntity);

        // update new role
        RoleEntity roleEntity = roleRepository.findByName("CANDIDATE");
        Set<RoleEntity> newRole = new HashSet<>(Collections.singleton(roleEntity));
        userService.updateRole(userEntity.getId(), newRole);
    }
}
