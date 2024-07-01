package com.learnthepath.recruitmentsystemabc.service;

import com.learnthepath.recruitmentsystemabc.dto.EnterpriseDto;
import com.learnthepath.recruitmentsystemabc.dto.RecruitmentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EnterpriseService {
    void saveEnterprise(EnterpriseDto enterpriseDto);

    void updateStatusById(Integer id, String status);

    EnterpriseDto findById(Integer id);

    EnterpriseDto getCurrentEnterprise();

    List<EnterpriseDto> getNonMemberEnterprises();
}
