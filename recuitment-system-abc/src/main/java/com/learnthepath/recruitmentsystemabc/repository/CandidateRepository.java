package com.learnthepath.recruitmentsystemabc.repository;

import com.learnthepath.recruitmentsystemabc.entity.CandidateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends JpaRepository<CandidateEntity, Integer> {
}
