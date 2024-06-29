package com.learnthepath.recruitmentsystemabc.service;

import com.learnthepath.recruitmentsystemabc.dto.RecruitmentDto;
import com.learnthepath.recruitmentsystemabc.entity.RecruitmentEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecruitmentService {
    void saveRecruitment(RecruitmentEntity recruitmentEntity);
    RecruitmentDto findById(Integer id);
    void updateStatusById(Integer id, String status);
    List<RecruitmentDto> getRecruitmentByStatus(String status);
    void createNewRecruitment(RecruitmentDto recruitmentDto);
}
