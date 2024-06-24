package com.learnthepath.recruitmentsystemabc.service;

import com.learnthepath.recruitmentsystemabc.dto.CandidateDto;
import com.learnthepath.recruitmentsystemabc.entity.CandidateEntity;
import org.springframework.stereotype.Service;

@Service
public interface CandidateService {
    void saveCandidate(CandidateDto candidateDto);
    CandidateDto mapToCandidateDto(CandidateEntity candidateEntity);
    CandidateEntity mapToCandidateEntity(CandidateDto candidateDto);
}
