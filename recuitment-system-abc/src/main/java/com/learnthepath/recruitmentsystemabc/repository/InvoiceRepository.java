package com.learnthepath.recruitmentsystemabc.repository;

import com.learnthepath.recruitmentsystemabc.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Integer> {
    @Query("SELECT i FROM InvoiceEntity i WHERE i.recruitment.id = :recruitmentId AND r.status = :status")
    List<InvoiceEntity> findByRecruitmentIdAndStatus(Integer recruitmentId, String status);
}
