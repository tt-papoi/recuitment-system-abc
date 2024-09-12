package com.learnthepath.recruitmentsystemabc.controller;

import com.learnthepath.recruitmentsystemabc.dto.ResumeDto;
import com.learnthepath.recruitmentsystemabc.service.ResumeService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ResumeController {
    @Autowired
    private ResumeService resumeService;

    @GetMapping("/enterprise/recruitment/posted/details/cv/{id}")
    public void getResumePdf(@PathVariable Integer id, HttpServletResponse response) {
        ResumeDto resumeDto = resumeService.findById(id);

        if (resumeDto == null || resumeDto.getContent() == null) {
            // Handle case where resume or content is not found
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=\"CV.pdf\"");
        response.setContentLength(resumeDto.getContent().length);

        try (InputStream inputStream = new ByteArrayInputStream(resumeDto.getContent())) {
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            throw new RuntimeException("IOError writing file to output stream", ex);
        }
    }

    @PostMapping("/enterprise/recruitment/cv/approval")
    public String approveResume(@RequestParam("resumeId") Integer resumeId,
                                @RequestParam("recruitmentId") Integer recruitmentId,
                                Model model) {
        resumeService.approveResume(resumeId);
        return "redirect:/enterprise/recruitment/posted/details?recruitmentId=" + recruitmentId;
    }

    @PostMapping("/enterprise/recruitment/cv/disapproval")
    public String disapproveResume(@RequestParam("resumeId") Integer resumeId,
                                @RequestParam("recruitmentId") Integer recruitmentId,
                                Model model) {
        resumeService.disapproveResume(resumeId);
        return "redirect:/enterprise/recruitment/posted/details?recruitmentId=" + recruitmentId;
    }
}
