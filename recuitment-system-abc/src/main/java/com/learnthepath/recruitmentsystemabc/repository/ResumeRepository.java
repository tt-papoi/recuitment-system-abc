package com.learnthepath.recruitmentsystemabc.repository;

import com.learnthepath.recruitmentsystemabc.entity.ResumeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeRepository extends JpaRepository<ResumeEntity, Integer> {
    List<ResumeEntity> findByRecruitmentIdAndCandidateId(Integer recruitmentId, Integer candidateId);

    List<ResumeEntity> findByRecruitmentId(Integer recruitmentId);

    List<ResumeEntity> findByRecruitmentIdAndStatus(Integer recruitmentId, String status);
}
