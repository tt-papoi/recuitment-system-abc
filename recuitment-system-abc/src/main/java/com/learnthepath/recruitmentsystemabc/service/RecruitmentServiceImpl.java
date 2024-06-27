package com.learnthepath.recruitmentsystemabc.service;

import com.learnthepath.recruitmentsystemabc.entity.RecruitmentEntity;
import com.learnthepath.recruitmentsystemabc.repository.RecruitmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecruitmentServiceImpl implements RecruitmentService {
    @Autowired
    private RecruitmentRepository recruitmentRepository;

    @Autowired
    private UserService userService;

    @Override
    public void saveRecruitment(RecruitmentEntity recruitmentEntity) {
        recruitmentRepository.save(recruitmentEntity);
    }

    @Override
    public RecruitmentEntity findById(Integer id) {
        return recruitmentRepository.getReferenceById(id);
    }
}
