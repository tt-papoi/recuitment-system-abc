package com.learnthepath.recruitmentsystemabc.service;

import com.learnthepath.recruitmentsystemabc.dto.EnterpriseDto;
import com.learnthepath.recruitmentsystemabc.dto.RecruitmentDto;
import com.learnthepath.recruitmentsystemabc.entity.EnterpriseEntity;
import com.learnthepath.recruitmentsystemabc.entity.RecruitmentEntity;
import com.learnthepath.recruitmentsystemabc.repository.EnterpriseRepository;
import com.learnthepath.recruitmentsystemabc.repository.RecruitmentRepository;
import com.learnthepath.recruitmentsystemabc.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @Autowired
    private RecruitmentRepository recruitmentRepository;

    @Override
    public List<EnterpriseDto> getNonMemberEnterprises() {
        String status = "NON_MEMBER";
        List<EnterpriseEntity> nonMemberEnterprises = enterpriseRepository.findByStatus(status);
        List<EnterpriseDto> enterpriseDtos = new ArrayList<>();
        for (EnterpriseEntity entity : nonMemberEnterprises) {
            enterpriseDtos.add(Utils.mapToDto(entity));
        }
        return enterpriseDtos;
    }

    @Override
    public List<RecruitmentDto> getRecruitmentByStatus(String status) {
        List<RecruitmentEntity> entities = recruitmentRepository.findByStatus(status);
        List<RecruitmentDto> dtos = new ArrayList<>();
        for(RecruitmentEntity entity : entities) {
            dtos.add(Utils.mapToDto(entity));
        }
        return dtos;
    }

    @Override
    public RecruitmentEntity findById(Integer id) {
        return recruitmentRepository.getReferenceById(id);
    }
}
