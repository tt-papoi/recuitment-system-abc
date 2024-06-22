package com.learnthepath.recruitmentsystemabc.service;

import com.learnthepath.recruitmentsystemabc.dto.EnterpriseDto;
import com.learnthepath.recruitmentsystemabc.dto.UserDto;
import com.learnthepath.recruitmentsystemabc.entity.EnterpriseEntity;
import org.springframework.stereotype.Service;

@Service
public interface EnterpriseService {
    void saveEnterprise(EnterpriseDto enterpriseDto);
    EnterpriseDto mapToEnterpriseDto(EnterpriseEntity enterpriseEntity);
    EnterpriseEntity mapToEnterpriseEntity(EnterpriseDto enterpriseDto);
}
