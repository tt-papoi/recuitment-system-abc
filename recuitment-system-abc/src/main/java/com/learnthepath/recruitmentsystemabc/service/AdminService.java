package com.learnthepath.recruitmentsystemabc.service;

import com.learnthepath.recruitmentsystemabc.dto.EnterpriseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminService {
    List<EnterpriseDto> getNonMemberEnterprises();
}
