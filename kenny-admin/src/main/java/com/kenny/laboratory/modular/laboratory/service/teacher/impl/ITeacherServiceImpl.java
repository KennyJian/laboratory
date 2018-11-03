package com.kenny.laboratory.modular.laboratory.service.teacher.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kenny.laboratory.core.shiro.ShiroKit;
import com.kenny.laboratory.modular.laboratory.dto.teacher.ExperimentApplyDTO;
import com.kenny.laboratory.modular.laboratory.service.IExperimentService;
import com.kenny.laboratory.modular.laboratory.service.teacher.ITeacherService;
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
}
