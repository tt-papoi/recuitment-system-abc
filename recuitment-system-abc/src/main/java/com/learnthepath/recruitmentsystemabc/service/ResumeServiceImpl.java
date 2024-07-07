package com.learnthepath.recruitmentsystemabc.service;

import com.learnthepath.recruitmentsystemabc.entity.CandidateEntity;
import com.learnthepath.recruitmentsystemabc.entity.RecruitmentEntity;
import com.learnthepath.recruitmentsystemabc.entity.ResumeEntity;
import com.learnthepath.recruitmentsystemabc.exception.EntityNotFoundException;
import com.learnthepath.recruitmentsystemabc.repository.CandidateRepository;
import com.learnthepath.recruitmentsystemabc.repository.RecruitmentRepository;
import com.learnthepath.recruitmentsystemabc.repository.ResumeRepository;
import com.learnthepath.recruitmentsystemabc.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ResumeServiceImpl implements ResumeService {
    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private RecruitmentRepository recruitmentRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Override
    public void saveResume(Integer recruitmentId, MultipartFile cvPdf) throws IOException {
        ResumeEntity resume = new ResumeEntity();
        resume.setContent(cvPdf.getBytes());

        RecruitmentEntity recruitmentEntity = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find recruitment with id: " + recruitmentId));
        resume.setRecruitment(recruitmentEntity);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // Find candidate by id
        CandidateEntity candidateEntity = candidateRepository.findById(userDetails.getId())
                .orElseThrow(() -> new EntityNotFoundException("Cannot find recruitment with id: " + userDetails.getId()));
        resume.setCandidate(candidateEntity);
        resume.setStatus("PENDING_APPROVAL");
        resumeRepository.save(resume);
    }
}
