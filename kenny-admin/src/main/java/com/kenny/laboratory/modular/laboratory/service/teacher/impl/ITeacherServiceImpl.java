package com.kenny.laboratory.modular.laboratory.service.teacher.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kenny.laboratory.core.shiro.ShiroKit;
import com.kenny.laboratory.modular.laboratory.dto.teacher.ApplyLaboratoryDTO;
import com.kenny.laboratory.modular.laboratory.dto.teacher.ApplyLaboratoryDetailDTO;
import com.kenny.laboratory.modular.laboratory.dto.teacher.AuditingExperimentDTO;
import com.kenny.laboratory.modular.laboratory.dto.teacher.ExperimentApplyDTO;
import com.kenny.laboratory.modular.laboratory.exception.StudentRepeatApplyException;
import com.kenny.laboratory.modular.laboratory.labenum.AuditingEnum;
import com.kenny.laboratory.modular.laboratory.service.IApplyExperimentService;
import com.kenny.laboratory.modular.laboratory.service.IApplyLaboratoryService;
import com.kenny.laboratory.modular.laboratory.service.IExperimentService;
import com.kenny.laboratory.modular.laboratory.service.IScoreService;
import com.kenny.laboratory.modular.laboratory.service.student.IStudentService;
import com.kenny.laboratory.modular.laboratory.service.teacher.ITeacherService;
import com.kenny.laboratory.modular.system.dao.UserMapper;
import com.kenny.laboratory.modular.system.model.ApplyExperiment;
import com.kenny.laboratory.modular.system.model.ApplyLaboratory;
import com.kenny.laboratory.modular.system.model.Experiment;
import com.kenny.laboratory.modular.system.model.Score;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ITeacherServiceImpl implements ITeacherService {

    @Autowired
    private IExperimentService experimentService;

    @Autowired
    private IApplyLaboratoryService applyLaboratoryService;

    @Autowired
    private IApplyExperimentService applyExperimentService;

    @Autowired
    private IScoreService scoreService;

    @Autowired
    private IStudentService studentService;

    @Autowired
    private UserMapper userMapper;


    //通过实验id获取实验对象
    private Experiment findExperimentByExperimentId(ApplyLaboratoryDTO applyLaboratoryDTO){
        EntityWrapper<Experiment> experimentEntityWrapper=new EntityWrapper<>();
        experimentEntityWrapper.eq("experiment_id",applyLaboratoryDTO.getExperimentId());
        return experimentService.selectOne(experimentEntityWrapper);
    }

    private boolean isAuditingSuccess(Long experimentId,Integer studentId){
        EntityWrapper<ApplyExperiment> applyExperimentEntityWrapper=new EntityWrapper<>();
        applyExperimentEntityWrapper.eq("experiment_id",experimentId);
        applyExperimentEntityWrapper.eq("status",AuditingEnum.SUCCESS.getCode());
        applyExperimentEntityWrapper.eq("student_id",studentId);
        ApplyExperiment isapplyExperimentExist=applyExperimentService.selectOne(applyExperimentEntityWrapper);
        if(isapplyExperimentExist!=null){
            return true;
        }
        return false;
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

    @Override
    public List<AuditingExperimentDTO> convertApplyExperimentToAuditingExperimentDTO(List<ApplyExperiment> applyExperimentList) {

        List<AuditingExperimentDTO> auditingExperimentDTOList=new ArrayList<>();
        for(ApplyExperiment applyExperiment:applyExperimentList){
            AuditingExperimentDTO auditingExperimentDTO=new AuditingExperimentDTO();
            BeanUtils.copyProperties(applyExperiment,auditingExperimentDTO);
            auditingExperimentDTO.setStatus(AuditingEnum.getMsg(applyExperiment.getStatus()));
            auditingExperimentDTOList.add(auditingExperimentDTO);
        }
        return auditingExperimentDTOList;
    }

    @Override
    @Transactional
    public void auditingSuccess(Long applyExperimentId) {
        //查询这条申请记录
        EntityWrapper<ApplyExperiment> entityWrapper=new EntityWrapper<>();
        entityWrapper.eq("id",applyExperimentId);
        ApplyExperiment applyExperiment=applyExperimentService.selectOne(entityWrapper);

        //查询是否已经审核通过过了
        if(isAuditingSuccess(applyExperiment.getExperimentId(),applyExperiment.getStudentId())){
            throw new StudentRepeatApplyException();
        }
        //将状态设为通过
        applyExperiment.setStatus(AuditingEnum.SUCCESS.getCode());
        applyExperimentService.updateById(applyExperiment);

        //查询成绩表是否已为学生建立
        EntityWrapper<Score> scoreEntityWrapper=new EntityWrapper<>();
        scoreEntityWrapper.eq("student_id",applyExperiment.getStudentId());
        scoreEntityWrapper.eq("experiment_id",applyExperiment.getExperimentId());
        Score score=scoreService.selectOne(scoreEntityWrapper);
        //如果成绩表没有该学生记录 则插入一条
        if(score==null){
            Score newScore=new Score();
            newScore.setStudentId(applyExperiment.getStudentId());
            newScore.setExperimentId(applyExperiment.getExperimentId());
            newScore.setStudentName(applyExperiment.getStudentName());
            newScore.setExperimentName(applyExperiment.getExperimentName());
            newScore.setAttendanceNum(0);
            newScore.setScore(new BigDecimal(0));
            newScore.setTeacherName(userMapper.getNameById(applyExperiment.getTeacherId()));
            newScore.setTeacherId(applyExperiment.getTeacherId());
            scoreService.insert(newScore);
        }
    }

    @Override
    public void auditingFail(Long applyExperimentId) {
        EntityWrapper<ApplyExperiment> entityWrapper=new EntityWrapper<>();
        entityWrapper.eq("id",applyExperimentId);
        ApplyExperiment applyExperiment=applyExperimentService.selectOne(entityWrapper);
        applyExperiment.setStatus(AuditingEnum.FAIL.getCode());
        applyExperimentService.updateById(applyExperiment);

    }
}
