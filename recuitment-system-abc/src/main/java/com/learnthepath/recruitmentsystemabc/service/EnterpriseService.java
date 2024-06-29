package com.learnthepath.recruitmentsystemabc.service;

import com.learnthepath.recruitmentsystemabc.dto.EnterpriseDto;
import com.learnthepath.recruitmentsystemabc.dto.RecruitmentDto;
import com.learnthepath.recruitmentsystemabc.entity.EnterpriseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EnterpriseService {
    void saveEnterprise(EnterpriseDto enterpriseDto);

    void updateStatusById(Integer id, String status);

    EnterpriseEntity findById(Integer id);

    EnterpriseDto getCurrentEnterprise();

    List<RecruitmentDto> findPendingApprovalRecruitment();

    void createNewRecruitment(RecruitmentDto recruitmentDto);
}
