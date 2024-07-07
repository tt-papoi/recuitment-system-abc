package com.learnthepath.recruitmentsystemabc.service;

import com.learnthepath.recruitmentsystemabc.dto.RecruitmentDto;
import com.learnthepath.recruitmentsystemabc.entity.EnterpriseEntity;
import com.learnthepath.recruitmentsystemabc.entity.RecruitmentEntity;
import com.learnthepath.recruitmentsystemabc.entity.ResumeEntity;
import com.learnthepath.recruitmentsystemabc.exception.EntityNotFoundException;
import com.learnthepath.recruitmentsystemabc.repository.EnterpriseRepository;
import com.learnthepath.recruitmentsystemabc.repository.RecruitmentRepository;
import com.learnthepath.recruitmentsystemabc.repository.ResumeRepository;
import com.learnthepath.recruitmentsystemabc.security.CustomUserDetails;
import com.learnthepath.recruitmentsystemabc.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecruitmentServiceImpl implements RecruitmentService {
    @Autowired
    private RecruitmentRepository recruitmentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @Autowired
    private ResumeRepository resumeRepository;

    @Override
    public void saveRecruitment(RecruitmentEntity recruitmentEntity) {
        recruitmentRepository.save(recruitmentEntity);
    }

    @Override
    public RecruitmentDto findById(Integer id) {
        return Utils.mapToDto(recruitmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find recruitment with id: " + id)));
    }

    @Override
    public Page<RecruitmentDto> findAll(Pageable pageable) {
        Page<RecruitmentEntity> pageEntities = recruitmentRepository.findAll(pageable);

        List<RecruitmentDto> dtos = pageEntities.getContent().stream()
                .map(Utils::mapToDto)
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, pageEntities.getTotalElements());
    }

    @Override
    public Page<RecruitmentDto> searchJobs(String keyword, Pageable pageable) {
        Page<RecruitmentEntity> entities = recruitmentRepository.searchJobs(keyword, pageable);

        List<RecruitmentDto> dtos = entities.stream()
                .map(Utils::mapToDto)
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, entities.getTotalElements());
    }

    @Override
    public boolean hasAppliedForJob(Integer recruitmentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        List<ResumeEntity> resumes = resumeRepository.findByRecruitmentIdAndCandidateId(recruitmentId, userDetails.getId());

        return !resumes.isEmpty();
    }

    @Override
    public void updateStatusById(Integer id, String status) {
        RecruitmentEntity recruitment = recruitmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find recruitment with id: " + id));

        // update status
        recruitment.setStatus(status);
        recruitmentRepository.save(recruitment);
    }

    @Override
    public void updateRecruitment(RecruitmentDto recruitmentDto) {
        recruitmentDto.setStatus("PENDING_APPROVAL");

        // Get the enterprise using the application
        Integer enterpriseId = userService.getCurrentUserId();
        EnterpriseEntity enterpriseEntity = enterpriseRepository.findById(enterpriseId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find recruitment with id: " + enterpriseId));
        recruitmentDto.setEnterprise(Utils.mapToDto(enterpriseEntity));
        RecruitmentEntity recruitmentEntity = Utils.mapToEntity(recruitmentDto);
        recruitmentRepository.save(recruitmentEntity);
    }

    @Override
    public void updateDisapprovalReasonById(Integer id, String disapprovalReason) {
        RecruitmentEntity recruitment = recruitmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find recruitment with id: " + id));

        recruitment.setDisapprovalReason(disapprovalReason);
        recruitmentRepository.save(recruitment);
    }


    @Override
    public void createNewRecruitment(RecruitmentDto recruitmentDto) {
        recruitmentDto.setStatus("PENDING_APPROVAL");

        // Get the enterprise using the application
        Integer enterpriseId = userService.getCurrentUserId();
        EnterpriseEntity enterpriseEntity = enterpriseRepository.findById(enterpriseId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find recruitment with id: " + enterpriseId));
        recruitmentDto.setEnterprise(Utils.mapToDto(enterpriseEntity));
        RecruitmentEntity recruitmentEntity = Utils.mapToEntity(recruitmentDto);
        recruitmentRepository.save(recruitmentEntity);
    }

    @Override
    public List<RecruitmentDto> findRecruitmentByEnterpriseIdAndStatus(Integer enterpriseId, String status) {
        List<RecruitmentEntity> entities = recruitmentRepository.findByEnterpriseIdAndStatus(enterpriseId, status);
        List<RecruitmentDto> dtos = new ArrayList<>();
        for(RecruitmentEntity entity : entities) {
            dtos.add(Utils.mapToDto(entity));
        }

        return dtos;
    }

    @Override
    public List<RecruitmentDto> findRecruitmentByStatus(String status) {
        List<RecruitmentEntity> entities = recruitmentRepository.findByStatus(status);
        List<RecruitmentDto> dtos = new ArrayList<>();
        for(RecruitmentEntity entity : entities) {
            dtos.add(Utils.mapToDto(entity));
        }

        return dtos;
    }

}
