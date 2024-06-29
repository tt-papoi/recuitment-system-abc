package com.learnthepath.recruitmentsystemabc.service;

import com.learnthepath.recruitmentsystemabc.dto.RecruitmentDto;
import com.learnthepath.recruitmentsystemabc.entity.EnterpriseEntity;
import com.learnthepath.recruitmentsystemabc.entity.RecruitmentEntity;
import com.learnthepath.recruitmentsystemabc.exception.EntityNotFoundException;
import com.learnthepath.recruitmentsystemabc.repository.EnterpriseRepository;
import com.learnthepath.recruitmentsystemabc.repository.RecruitmentRepository;
import com.learnthepath.recruitmentsystemabc.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecruitmentServiceImpl implements RecruitmentService {
    @Autowired
    private RecruitmentRepository recruitmentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @Override
    public void saveRecruitment(RecruitmentEntity recruitmentEntity) {
        recruitmentRepository.save(recruitmentEntity);
    }

    @Override
    public RecruitmentDto findById(Integer id) {
        return Utils.mapToDto(recruitmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find recruitment with id: " + id)));
    }

    @Override
    public void updateStatusById(Integer id, String status) {
        RecruitmentEntity recruitment = recruitmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find recruitment with id: " + id));

        // update status
        recruitment.setStatus(status);
        recruitmentRepository.save(recruitment);
    }

    @Override
    public List<RecruitmentDto> getRecruitmentByStatus(String status) {
        List<RecruitmentEntity> entities = recruitmentRepository.findByStatus(status);
        List<RecruitmentDto> dtos = new ArrayList<>();
        for(RecruitmentEntity entity : entities) {
            dtos.add(Utils.mapToDto(entity));
        }
        return dtos;
    }

    @Override
    public void createNewRecruitment(RecruitmentDto recruitmentDto) {
        recruitmentDto.setStatus("PENDING_APPROVAL");

        // Get the enterprise using the application
        Integer enterpriseId = userService.getCurrentUserId();
        EnterpriseEntity enterpriseEntity = enterpriseRepository.findById(enterpriseId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find recruitment with id: " + enterpriseId));
        RecruitmentEntity recruitmentEntity = Utils.mapToEntity(recruitmentDto);
        recruitmentEntity.setEnterprise(enterpriseEntity);
        recruitmentRepository.save(recruitmentEntity);
    }
}
