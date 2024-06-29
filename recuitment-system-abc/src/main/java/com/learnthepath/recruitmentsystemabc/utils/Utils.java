package com.learnthepath.recruitmentsystemabc.utils;

import com.learnthepath.recruitmentsystemabc.dto.CandidateDto;
import com.learnthepath.recruitmentsystemabc.dto.EnterpriseDto;
import com.learnthepath.recruitmentsystemabc.dto.RecruitmentDto;
import com.learnthepath.recruitmentsystemabc.dto.UserDto;
import com.learnthepath.recruitmentsystemabc.entity.CandidateEntity;
import com.learnthepath.recruitmentsystemabc.entity.EnterpriseEntity;
import com.learnthepath.recruitmentsystemabc.entity.RecruitmentEntity;
import com.learnthepath.recruitmentsystemabc.entity.UserEntity;

public class Utils {
    public static UserDto mapToDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setUsername(userEntity.getUsername());
        return userDto;
    }
    public static UserEntity mapToEntity(UserDto userDto) {
        UserEntity entity = new UserEntity();
        entity.setUsername(userDto.getUsername());
        return entity;
    }

    public static RecruitmentEntity mapToEntity(RecruitmentDto dto) {
        RecruitmentEntity entity = new RecruitmentEntity();
        entity.setId(dto.getId());
        entity.setRequirements(dto.getRequirements());
        entity.setAppliedPosition(dto.getAppliedPosition());
        entity.setJobDescription(dto.getJobDescription());
        entity.setEndDate(dto.getEndDate());
        entity.setStartDate(dto.getStartDate());
        entity.setStatus(dto.getStatus());
        entity.setNumberCandidates(dto.getNumberCandidates());
        return entity;
    }

    public static RecruitmentDto mapToDto(RecruitmentEntity entity) {
        RecruitmentDto dto = new RecruitmentDto();
        dto.setId(entity.getId());
        dto.setRequirements(entity.getRequirements());
        dto.setAppliedPosition(entity.getAppliedPosition());
        dto.setJobDescription(entity.getJobDescription());
        dto.setEndDate(entity.getEndDate());
        dto.setStartDate(entity.getStartDate());
        dto.setStatus(entity.getStatus());
        dto.setNumberCandidates(entity.getNumberCandidates());
        dto.setEnterprise(mapToDto(entity.getEnterprise()));
        return dto;
    }

    public static EnterpriseDto mapToDto(EnterpriseEntity enterpriseEntity) {
        EnterpriseDto dto = new EnterpriseDto();
        dto.setId(enterpriseEntity.getId());
        dto.setName(enterpriseEntity.getName());
        dto.setAddress(enterpriseEntity.getAddress());
        dto.setEmail(enterpriseEntity.getEmail());
        dto.setRepresentative(enterpriseEntity.getRepresentative());
        dto.setPhoneNumber(enterpriseEntity.getPhoneNumber());
        dto.setTaxCode(enterpriseEntity.getTaxCode());
        dto.setStatus(enterpriseEntity.getStatus());
        return dto;
    }

    public static EnterpriseEntity mapToEntity(EnterpriseDto enterpriseDto) {
        EnterpriseEntity entity = new EnterpriseEntity();
        entity.setId(enterpriseDto.getId()); // Ensure to set the id if necessary
        entity.setName(enterpriseDto.getName());
        entity.setAddress(enterpriseDto.getAddress());
        entity.setEmail(enterpriseDto.getEmail());
        entity.setRepresentative(enterpriseDto.getRepresentative());
        entity.setPhoneNumber(enterpriseDto.getPhoneNumber());
        entity.setTaxCode(enterpriseDto.getTaxCode());
        entity.setStatus(enterpriseDto.getStatus());
        return entity;
    }

    public static CandidateDto mapToDto(CandidateEntity candidateEntity) {
        CandidateDto dto = new CandidateDto();
        dto.setBirthday(candidateEntity.getBirthday());
        dto.setId(candidateEntity.getId());
        dto.setEmail(candidateEntity.getEmail());
        dto.setPhoneNumber(candidateEntity.getPhoneNumber());
        return dto;
    }
    public static CandidateEntity mapToEntity(CandidateDto candidateDto) {
        CandidateEntity entity = new CandidateEntity();
        entity.setBirthday(candidateDto.getBirthday());
        entity.setId(candidateDto.getId());
        entity.setEmail(candidateDto.getEmail());
        entity.setPhoneNumber(candidateDto.getPhoneNumber());
        return entity;
    }
}
