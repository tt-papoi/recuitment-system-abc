package com.learnthepath.recruitmentsystemabc.service;

import com.learnthepath.recruitmentsystemabc.dto.RecruitmentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminService {
    List<RecruitmentDto> findRecruitmentByStatus(String status);
}
