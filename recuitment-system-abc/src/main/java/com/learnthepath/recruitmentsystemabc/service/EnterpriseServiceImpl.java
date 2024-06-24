package com.learnthepath.recruitmentsystemabc.service;

import com.learnthepath.recruitmentsystemabc.dto.EnterpriseDto;
import com.learnthepath.recruitmentsystemabc.entity.EnterpriseEntity;
import com.learnthepath.recruitmentsystemabc.entity.RoleEntity;
import com.learnthepath.recruitmentsystemabc.entity.UserEntity;
import com.learnthepath.recruitmentsystemabc.repository.EnterpriseRepository;
import com.learnthepath.recruitmentsystemabc.repository.RoleRepository;
import com.learnthepath.recruitmentsystemabc.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class EnterpriseServiceImpl implements EnterpriseService {

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public void saveEnterprise(EnterpriseDto enterpriseDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // Find the user by username
        UserEntity userEntity = userService.findByUsername(userDetails.getUsername());
        if (userEntity == null) {
            throw new RuntimeException("User not found: " + userDetails.getUsername());
        }

        // Map DTO to entity
        EnterpriseEntity enterpriseEntity = mapToEnterpriseEntity(enterpriseDto);

        // Set the user entity and synchronize the id
        enterpriseEntity.setUser(userEntity);
        //enterpriseEntity.setId(userEntity.getId()); // Ensure the id is synchronized

        // Save or update enterpriseEntity
        enterpriseRepository.save(enterpriseEntity);

        // update new role
        RoleEntity roleEntity = roleRepository.findByName("ENTERPRISE");
        Set<RoleEntity> newRole = new HashSet<>(Collections.singleton(roleEntity));
        userService.updateRole(userEntity.getId(), newRole);

//        // reload auth
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());
//        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);
//        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    @Override
    public EnterpriseDto mapToEnterpriseDto(EnterpriseEntity enterpriseEntity) {
        EnterpriseDto dto = new EnterpriseDto();
        dto.setId(enterpriseEntity.getId());
        dto.setName(enterpriseEntity.getName());
        dto.setAddress(enterpriseEntity.getAddress());
        dto.setEmail(enterpriseEntity.getEmail());
        dto.setRepresentative(enterpriseEntity.getRepresentative());
        dto.setPhoneNumber(enterpriseEntity.getPhoneNumber());
        dto.setTaxCode(enterpriseEntity.getTaxCode());
        return dto;
    }

    @Override
    public EnterpriseEntity mapToEnterpriseEntity(EnterpriseDto enterpriseDto) {
        EnterpriseEntity entity = new EnterpriseEntity();
        entity.setId(enterpriseDto.getId()); // Ensure to set the id if necessary
        entity.setName(enterpriseDto.getName());
        entity.setAddress(enterpriseDto.getAddress());
        entity.setEmail(enterpriseDto.getEmail());
        entity.setRepresentative(enterpriseDto.getRepresentative());
        entity.setPhoneNumber(enterpriseDto.getPhoneNumber());
        entity.setTaxCode(enterpriseDto.getTaxCode());
        return entity;
    }
}
