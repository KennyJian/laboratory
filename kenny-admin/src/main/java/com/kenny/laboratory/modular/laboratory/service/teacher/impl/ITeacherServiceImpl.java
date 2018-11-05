package com.kenny.laboratory.modular.laboratory.service.teacher.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kenny.laboratory.core.shiro.ShiroKit;
import com.kenny.laboratory.modular.laboratory.dto.teacher.ApplyLaboratoryDTO;
import com.kenny.laboratory.modular.laboratory.dto.teacher.ApplyLaboratoryDetailDTO;
import com.kenny.laboratory.modular.laboratory.dto.teacher.ExperimentApplyDTO;
import com.kenny.laboratory.modular.laboratory.labenum.AuditingEnum;
import com.kenny.laboratory.modular.laboratory.service.IApplyLaboratoryService;
import com.kenny.laboratory.modular.laboratory.service.IExperimentService;
import com.kenny.laboratory.modular.laboratory.service.teacher.ITeacherService;
import com.kenny.laboratory.modular.system.model.ApplyLaboratory;
import com.kenny.laboratory.modular.system.model.Experiment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ITeacherServiceImpl implements ITeacherService {

    @Autowired
    private IExperimentService experimentService;

    @Autowired
    private IApplyLaboratoryService applyLaboratoryService;


    //通过实验id获取实验对象
    private Experiment findExperimentByExperimentId(ApplyLaboratoryDTO applyLaboratoryDTO){
        EntityWrapper<Experiment> experimentEntityWrapper=new EntityWrapper<>();
        experimentEntityWrapper.eq("experiment_id",applyLaboratoryDTO.getExperimentId());
        return experimentService.selectOne(experimentEntityWrapper);
    }

    @Override
    public List<ExperimentApplyDTO> findAllExperimentByTeacher() {
        EntityWrapper<Experiment> entityWrapper=new EntityWrapper<>();
        entityWrapper.in("teacher_id", ShiroKit.getUser().getId().toString());
        List<Experiment> experimentList=experimentService.selectList(entityWrapper);
        List<ExperimentApplyDTO> experimentApplyDTOList=new ArrayList<>();
        for(Experiment experiment:experimentList){
            ExperimentApplyDTO experimentApplyDTO=new ExperimentApplyDTO();
            BeanUtils.copyProperties(experiment,experimentApplyDTO);
            experimentApplyDTOList.add(experimentApplyDTO);
        }
        return experimentApplyDTOList;
    }

    @Override
    public boolean applyLaboratory(ApplyLaboratoryDTO applyLaboratoryDTO) {
        //通过实验id获取实验对象
        Experiment experiment=findExperimentByExperimentId(applyLaboratoryDTO);

        //新建实验室申请的对象
        ApplyLaboratory applyLaborator=new ApplyLaboratory();
        BeanUtils.copyProperties(applyLaboratoryDTO,applyLaborator);
        applyLaborator.setExperimentName(experiment.getExperimentName());
        applyLaborator.setStatus(AuditingEnum.WAIT.getCode());
        applyLaborator.setTeacherId(ShiroKit.getUser().getId());

        //插入表
        if(applyLaboratoryService.insert(applyLaborator)){
            return true;
        }
        return false;
    }

    @Override
    public boolean isEquipmentEnough(ApplyLaboratoryDTO applyLaboratoryDTO) {

        //通过实验id获取实验对象
        Experiment experiment=findExperimentByExperimentId(applyLaboratoryDTO);
        if(experiment.getEquipmentNeedNum()<=applyLaboratoryDTO.getLaboratoryEquipmentNum()){
            return true;
        }

        return false;
    }

    @Override
    public List<ApplyLaboratoryDetailDTO> convertApplyLaboratoryToApplyLaboratoryDetailDTO(List<ApplyLaboratory> applyLaboratoryList) {
        List<ApplyLaboratoryDetailDTO> applyLaboratoryDetailDTOList=new ArrayList<>();
        for(ApplyLaboratory applyLaboratory:applyLaboratoryList){
            ApplyLaboratoryDetailDTO applyLaboratoryDetailDTO=new ApplyLaboratoryDetailDTO();
            BeanUtils.copyProperties(applyLaboratory,applyLaboratoryDetailDTO);
            applyLaboratoryDetailDTO.setStatus(AuditingEnum.getMsg(applyLaboratory.getStatus()));
            applyLaboratoryDetailDTOList.add(applyLaboratoryDetailDTO);
        }
        return applyLaboratoryDetailDTOList;
    }
}
