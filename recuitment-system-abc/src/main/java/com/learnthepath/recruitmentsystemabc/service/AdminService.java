package com.learnthepath.recruitmentsystemabc.service;

import com.learnthepath.recruitmentsystemabc.dto.EnterpriseDto;
import com.learnthepath.recruitmentsystemabc.dto.RecruitmentDto;
import com.learnthepath.recruitmentsystemabc.entity.RecruitmentEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminService {
    List<EnterpriseDto> getNonMemberEnterprises();
    List<RecruitmentDto> getRecruitmentByStatus(String status);
    RecruitmentEntity findById(Integer id);
}
