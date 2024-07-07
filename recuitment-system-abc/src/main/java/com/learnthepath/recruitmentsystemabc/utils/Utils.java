package com.learnthepath.recruitmentsystemabc.utils;

import com.learnthepath.recruitmentsystemabc.dto.*;
import com.learnthepath.recruitmentsystemabc.entity.*;

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
        entity.setDisapprovalReason(dto.getDisapprovalReason());
        entity.setEnterprise(Utils.mapToEntity(dto.getEnterprise()));
        entity.setBenefits(dto.getBenefits());
        entity.setWorkExperience(dto.getWorkExperience());
        entity.setSalary(dto.getSalary());
        entity.setWorkLocation(dto.getWorkLocation());
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
        dto.setDisapprovalReason(entity.getDisapprovalReason());
        dto.setSalary(entity.getSalary());
        dto.setBenefits(entity.getBenefits());
        dto.setWorkExperience(entity.getWorkExperience());
        dto.setWorkLocation(entity.getWorkLocation());
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
        dto.setName(candidateEntity.getName());
        return dto;
    }
    public static CandidateEntity mapToEntity(CandidateDto candidateDto) {
        CandidateEntity entity = new CandidateEntity();
        entity.setBirthday(candidateDto.getBirthday());
        entity.setId(candidateDto.getId());
        entity.setEmail(candidateDto.getEmail());
        entity.setPhoneNumber(candidateDto.getPhoneNumber());
        entity.setName(candidateDto.getName());
        return entity;
    }

    public static InvoiceDto mapToDto(InvoiceEntity entity) {
        InvoiceDto dto = new InvoiceDto();
        dto.setId(entity.getId());
        dto.setPaymentDate(entity.getPaymentDate());
        dto.setMethod(entity.getMethod());
        dto.setTotalAmount(entity.getTotalAmount());
        dto.setStatus(entity.getStatus());
        dto.setRecruitment(mapToDto(entity.getRecruitment()));
        return dto;
    }

    public static InvoiceEntity mapToEntity(InvoiceDto dto) {
        InvoiceEntity entity = new InvoiceEntity();
        entity.setId(dto.getId());
        entity.setPaymentDate(dto.getPaymentDate());
        entity.setMethod(dto.getMethod());
        entity.setTotalAmount(dto.getTotalAmount());
        entity.setStatus(dto.getStatus());
        entity.setRecruitment(mapToEntity(dto.getRecruitment()));
        return entity;
    }

    public static ResumeDto mapToDto(ResumeEntity entity) {
        ResumeDto dto = new ResumeDto();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setCandidate(mapToDto(entity.getCandidate()));
        dto.setRecruitment(mapToDto(entity.getRecruitment()));
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public static ResumeEntity mapToDto(ResumeDto dto) {
        ResumeEntity entity = new ResumeEntity();
        entity.setId(dto.getId());
        entity.setContent(dto.getContent());
        entity.setCandidate(mapToEntity(dto.getCandidate()));
        entity.setRecruitment(mapToEntity(dto.getRecruitment()));
        entity.setStatus(dto.getStatus());
        return entity;
    }

}
