package com.learnthepath.recruitmentsystemabc.service;

import com.learnthepath.recruitmentsystemabc.dto.ResumeDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface ResumeService {
    void saveResume(Integer recruitmentId, MultipartFile cvPdf) throws IOException;

    List<ResumeDto> findByRecruitmentId(Integer recruitmentId);

    List<ResumeDto> findByRecruitmentIdAndStatus(Integer recruitmentId, String status);

    ResumeDto findById(Integer id);

    void approveResume(Integer resumeId);

    void disapproveResume(Integer resumeId);

}
