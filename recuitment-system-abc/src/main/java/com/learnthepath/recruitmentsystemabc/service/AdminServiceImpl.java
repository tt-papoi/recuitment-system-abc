package com.learnthepath.recruitmentsystemabc.service;

import com.learnthepath.recruitmentsystemabc.dto.EnterpriseDto;
import com.learnthepath.recruitmentsystemabc.entity.EnterpriseEntity;
import com.learnthepath.recruitmentsystemabc.repository.EnterpriseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @Autowired
    private EnterpriseService enterpriseService;

    @Override
    public List<EnterpriseDto> getNonMemberEnterprises() {
        List<EnterpriseEntity> enterpriseEntities = enterpriseService.getNonMemberEnterprises();
        List<EnterpriseDto> enterpriseDtos = new ArrayList<>();
        for(EnterpriseEntity entity : enterpriseEntities) {
            enterpriseDtos.add(enterpriseService.mapToEnterpriseDto(entity));
        }
        return enterpriseDtos;
    }
}
