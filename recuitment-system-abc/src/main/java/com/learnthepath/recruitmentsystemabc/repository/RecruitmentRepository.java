package com.learnthepath.recruitmentsystemabc.repository;

import com.learnthepath.recruitmentsystemabc.entity.RecruitmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecruitmentRepository extends JpaRepository<RecruitmentEntity, Integer> {

    @Query("SELECT r FROM RecruitmentEntity r WHERE r.enterprise.id = :enterpriseId")
    List<RecruitmentEntity> findByEnterpriseId(Integer enterpriseId);

    @Query("SELECT r FROM RecruitmentEntity r WHERE r.status = :status")
    List<RecruitmentEntity> findByStatus(String status);

    @Query("SELECT r FROM RecruitmentEntity r WHERE r.enterprise.id = :enterpriseId AND r.status = :status")
    List<RecruitmentEntity> findByEnterpriseIdAndStatus(Integer enterpriseId, String status);
}
