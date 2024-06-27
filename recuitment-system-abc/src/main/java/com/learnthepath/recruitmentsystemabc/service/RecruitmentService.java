package com.learnthepath.recruitmentsystemabc.service;

import com.learnthepath.recruitmentsystemabc.dto.RecruitmentDto;
import com.learnthepath.recruitmentsystemabc.entity.RecruitmentEntity;
import org.springframework.stereotype.Service;

@Service
public interface RecruitmentService {
    void saveRecruitment(RecruitmentEntity recruitmentEntity);
    RecruitmentEntity findById(Integer id);
}
