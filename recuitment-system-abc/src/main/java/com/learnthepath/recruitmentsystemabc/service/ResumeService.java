package com.learnthepath.recruitmentsystemabc.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface ResumeService {
    void saveResume(Integer recruitmentId, MultipartFile cvPdf) throws IOException;
}
