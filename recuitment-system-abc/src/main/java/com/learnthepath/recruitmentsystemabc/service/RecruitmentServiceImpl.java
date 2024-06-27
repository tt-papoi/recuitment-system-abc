package com.learnthepath.recruitmentsystemabc.service;

import com.learnthepath.recruitmentsystemabc.dto.RecruitmentDto;
import com.learnthepath.recruitmentsystemabc.entity.RecruitmentEntity;
import com.learnthepath.recruitmentsystemabc.repository.RecruitmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecruitmentServiceImpl implements RecruitmentService {
    @Autowired
    private RecruitmentRepository recruitmentRepository;

    @Override
    public void saveRecruitment(RecruitmentDto recruitmentDto) {
        RecruitmentEntity entity = mapToRecruitmentEntity(recruitmentDto);
        recruitmentRepository.save(entity);
    }

    @Override
    public void createNewRecruitment(RecruitmentDto recruitmentDto) {
        recruitmentDto.setStatus("PENDING_APPROVAL");
        saveRecruitment(recruitmentDto);
    }

    @Override
    public RecruitmentEntity mapToRecruitmentEntity(RecruitmentDto dto) {
        RecruitmentEntity entity = new RecruitmentEntity();
        entity.setId(dto.getId());
        entity.setRequirments(dto.getRequirments());
        entity.setAppliedPosition(dto.getAppliedPosition());
        entity.setJobDecription(dto.getJobDecription());
        entity.setEndDate(dto.getEndDate());
        entity.setStartDate(dto.getStartDate());
        entity.setStatus(dto.getStatus());
        entity.setNumberCandidates(dto.getNumberCandidates());
        return entity;
    }

    @Override
    public RecruitmentDto mapToRecruitmentDto(RecruitmentEntity entity) {
        RecruitmentDto dto = new RecruitmentDto();
        dto.setId(entity.getId());
        dto.setRequirments(entity.getRequirments());
        dto.setAppliedPosition(entity.getAppliedPosition());
        dto.setJobDecription(entity.getJobDecription());
        dto.setEndDate(entity.getEndDate());
        dto.setStartDate(entity.getStartDate());
        dto.setStatus(entity.getStatus());
        dto.setNumberCandidates(entity.getNumberCandidates());
        return dto;
    }

}
