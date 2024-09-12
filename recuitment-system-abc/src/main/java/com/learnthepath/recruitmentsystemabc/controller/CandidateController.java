package com.learnthepath.recruitmentsystemabc.controller;

import com.learnthepath.recruitmentsystemabc.constants.Constants;
import com.learnthepath.recruitmentsystemabc.dto.RecruitmentDto;
import com.learnthepath.recruitmentsystemabc.service.RecruitmentService;
import com.learnthepath.recruitmentsystemabc.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class CandidateController {
    @Autowired
    private RecruitmentService recruitmentService;

    @Autowired
    private ResumeService resumeService;

    @GetMapping({"/candidate/home", "/candidate/jobs", "/candidate/jobs/find"})
    public String showJobs(@RequestParam(value = "keyword", required = false) String keyword,
                             @RequestParam(value = "page", defaultValue = "0") int page,
                             Model model) {
        Pageable pageable = PageRequest.of(page, Constants.JOBS_PER_PAGE);
        Page<RecruitmentDto> jobs;
        if (keyword != null && !keyword.isEmpty()) {
            jobs = recruitmentService.searchJobs(keyword, pageable);
        } else {
            jobs = recruitmentService.findAll(pageable);
        }
        model.addAttribute("jobs", jobs);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", jobs.getTotalPages());
        return "candidate-page/candidate-jobs";
    }

    @GetMapping("/candidate/jobs/details")
    public String showJobDetails(@RequestParam(value = "id") Integer jobId, Model model) {
        RecruitmentDto job = recruitmentService.findById(jobId);
        boolean hasApplied = recruitmentService.hasAppliedForJob(jobId);
        model.addAttribute("job", job);
        model.addAttribute("hasApplied", hasApplied);
        return "candidate-page/candidate-jobs-details";
    }

    @PostMapping("/candidate/jobs/details/apply")
    public String handleFileUpload(@RequestParam("uploadCV") MultipartFile cv,
                                   @RequestParam("jobId") Integer jobId,
                                   Model model) {
        try {
            resumeService.saveResume(jobId, cv);
            model.addAttribute("message", "File uploaded successfully!");
        } catch (IOException e) {
            model.addAttribute("message", "Failed to upload file.");
        }
        return "redirect:/candidate/jobs";
    }

    @GetMapping("/candidate/jobs/applied")
    public String showAppliedJob(@RequestParam(value = "keyword", required = false) String keyword,
                             @RequestParam(value = "page", defaultValue = "0") int page,
                             Model model) {
        Pageable pageable = PageRequest.of(page, Constants.JOBS_PER_PAGE);
        Page<RecruitmentDto> jobs;
        if (keyword != null && !keyword.isEmpty()) {
            jobs = recruitmentService.searchAppliedJobs(keyword, pageable);
        } else {
            jobs = recruitmentService.findAllAppliedJobs(pageable);
        }
        model.addAttribute("jobs", jobs);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", jobs.getTotalPages());
        return "candidate-page/candidate-applied-jobs";
    }
}
