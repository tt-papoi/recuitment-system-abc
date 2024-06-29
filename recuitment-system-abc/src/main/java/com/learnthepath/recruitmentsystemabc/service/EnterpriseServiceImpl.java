package com.learnthepath.recruitmentsystemabc.service;

import com.learnthepath.recruitmentsystemabc.dto.EnterpriseDto;
import com.learnthepath.recruitmentsystemabc.dto.RecruitmentDto;
import com.learnthepath.recruitmentsystemabc.entity.EnterpriseEntity;
import com.learnthepath.recruitmentsystemabc.entity.RecruitmentEntity;
import com.learnthepath.recruitmentsystemabc.entity.RoleEntity;
import com.learnthepath.recruitmentsystemabc.entity.UserEntity;
import com.learnthepath.recruitmentsystemabc.exception.EntityNotFoundException;
import com.learnthepath.recruitmentsystemabc.repository.EnterpriseRepository;
import com.learnthepath.recruitmentsystemabc.repository.InvoiceRepository;
import com.learnthepath.recruitmentsystemabc.repository.RecruitmentRepository;
import com.learnthepath.recruitmentsystemabc.repository.RoleRepository;
import com.learnthepath.recruitmentsystemabc.security.CustomUserDetails;
import com.learnthepath.recruitmentsystemabc.utils.Utils;
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
    private RecruitmentRepository recruitmentRepository;

    @Autowired
    private InvoiceRepository

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

        enterpriseDto.setStatus("NON_MEMBER");
        // Map DTO to entity
        EnterpriseEntity enterpriseEntity = Utils.mapToEntity(enterpriseDto);

        // Set the user entity and synchronize the id
        enterpriseEntity.setUser(userEntity);

        // Save or update enterpriseEntity
        enterpriseRepository.save(enterpriseEntity);

        // update new role
        RoleEntity roleEntity = roleRepository.findByName("ENTERPRISE");
        Set<RoleEntity> newRole = new HashSet<>(Collections.singleton(roleEntity));
        userService.updateRole(userEntity.getId(), newRole);
    }

    @Override
    public void updateStatusById(Integer id, String status) {
        EnterpriseEntity enterprise = enterpriseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find enterprise with id: " + id));

        // update status
        enterprise.setStatus(status);
        enterpriseRepository.save(enterprise);
    }

    @Override
    public EnterpriseEntity findById(Integer id) {
        return enterpriseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find enterprise with id: " + id));
    }

    @Override
    public EnterpriseDto getCurrentEnterprise() {
        Integer id = userService.getCurrentUserId();
        return Utils.mapToDto(enterpriseRepository.getReferenceById(id));
    }

    @Override
    public List<RecruitmentDto> findPendingApprovalRecruitment() {
        Integer id = userService.getCurrentUserId();
        String status = "PENDING_APPROVAL";
        List<RecruitmentEntity> entities = recruitmentRepository.findByEnterpriseIdAndStatus(id, status);
        List<RecruitmentDto> dtos = new ArrayList<>();
        for(RecruitmentEntity entity : entities) {
            dtos.add(Utils.mapToDto(entity));
        }

        return dtos;
    }

    @Override
    public void createNewRecruitment(RecruitmentDto recruitmentDto) {
        recruitmentDto.setStatus("PENDING_APPROVAL");

        // Get the enterprise using the application
        EnterpriseEntity enterpriseEntity = findById(userService.getCurrentUserId());
        RecruitmentEntity recruitmentEntity = Utils.mapToEntity(recruitmentDto);
        recruitmentEntity.setEnterprise(enterpriseEntity);
        recruitmentRepository.save(recruitmentEntity);
    }
}
