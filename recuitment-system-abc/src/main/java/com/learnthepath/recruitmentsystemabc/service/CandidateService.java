package com.learnthepath.recruitmentsystemabc.service;

import com.learnthepath.recruitmentsystemabc.dto.CandidateDto;
import org.springframework.stereotype.Service;

@Service
public interface CandidateService {
    void saveCandidate(CandidateDto candidateDto);
}
