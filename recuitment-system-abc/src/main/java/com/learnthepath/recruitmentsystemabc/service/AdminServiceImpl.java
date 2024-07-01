package com.learnthepath.recruitmentsystemabc.service;

import com.learnthepath.recruitmentsystemabc.dto.RecruitmentDto;
import com.learnthepath.recruitmentsystemabc.entity.RecruitmentEntity;
import com.learnthepath.recruitmentsystemabc.repository.RecruitmentRepository;
import com.learnthepath.recruitmentsystemabc.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private RecruitmentRepository recruitmentRepository;

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
