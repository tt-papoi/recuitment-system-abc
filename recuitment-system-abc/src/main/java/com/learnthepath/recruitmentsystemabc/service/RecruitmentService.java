package com.learnthepath.recruitmentsystemabc.service;

import com.learnthepath.recruitmentsystemabc.dto.RecruitmentDto;
import com.learnthepath.recruitmentsystemabc.entity.RecruitmentEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecruitmentService {
    void saveRecruitment(RecruitmentEntity recruitmentEntity);

    void updateStatusById(Integer id, String status);

    void updateRecruitment(RecruitmentDto recruitmentDto);

    void updateDisapprovalReasonById(Integer id, String disapprovalReason);

    void createNewRecruitment(RecruitmentDto recruitmentDto);

    List<RecruitmentDto> findRecruitmentByEnterpriseIdAndStatus(Integer enterpriseId, String status);

    List<RecruitmentDto> findRecruitmentByStatus(String status);

    RecruitmentDto findById(Integer id);

    Page<RecruitmentDto> findAll(Pageable pageable);

    Page<RecruitmentDto> searchJobs(String keyword, Pageable pageable);

    boolean hasAppliedForJob(Integer recruitmentId);

    Page<RecruitmentDto> findAllAppliedJobs(Pageable pageable);

    Page<RecruitmentDto> searchAppliedJobs(String keyword, Pageable pageable);
}
