package com.kenny.laboratory.modular.laboratory.service.student.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kenny.laboratory.core.shiro.ShiroKit;
import com.kenny.laboratory.modular.laboratory.dto.student.StudentApplyDetailDTO;
import com.kenny.laboratory.modular.laboratory.dto.student.StudentScoreDTO;
import com.kenny.laboratory.modular.laboratory.dto.student.StundentApplyShowDTO;
import com.kenny.laboratory.modular.laboratory.exception.AttendMaxException;
import com.kenny.laboratory.modular.laboratory.exception.StudentRepeatApplyException;
import com.kenny.laboratory.modular.laboratory.labenum.AuditingEnum;
import com.kenny.laboratory.modular.laboratory.service.IApplyExperimentService;
import com.kenny.laboratory.modular.laboratory.service.IExperimentService;
import com.kenny.laboratory.modular.laboratory.service.IScoreService;
import com.kenny.laboratory.modular.laboratory.service.student.IStudentService;
import com.kenny.laboratory.modular.system.dao.UserMapper;
import com.kenny.laboratory.modular.system.model.ApplyExperiment;
import com.kenny.laboratory.modular.system.model.Experiment;
import com.kenny.laboratory.modular.system.model.Score;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

@Service
public class IStudentServiceImpl implements IStudentService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IApplyExperimentService applyExperimentService;

    @Autowired
    private IScoreService scoreService;

    @Autowired
    private IExperimentService experimentService;

    private boolean isAuditingSuccess(String experimentId){
        Collection<Integer> collection=new ConcurrentLinkedDeque();
        collection.add(AuditingEnum.SUCCESS.getCode());
        collection.add(AuditingEnum.WAIT.getCode());
        EntityWrapper<ApplyExperiment> applyExperimentEntityWrapper=new EntityWrapper<>();
        applyExperimentEntityWrapper.eq("experiment_id",experimentId);
        applyExperimentEntityWrapper.in("status",collection);
        applyExperimentEntityWrapper.eq("student_id",ShiroKit.getUser().getId());
        ApplyExperiment isapplyExperimentExist=applyExperimentService.selectOne(applyExperimentEntityWrapper);
        if(isapplyExperimentExist!=null){
            return true;
        }
        return false;
    }

    @Override
    public List<StundentApplyShowDTO> convertExperientToStundentApplyShowDTO(List<Experiment> experimentList) {
        List<StundentApplyShowDTO> stundentApplyShowDTOList=new ArrayList<>();
        for(Experiment experiment:experimentList){
            StundentApplyShowDTO stundentApplyShowDTO=new StundentApplyShowDTO();
            BeanUtils.copyProperties(experiment,stundentApplyShowDTO);
            stundentApplyShowDTO.setTeacherName(userMapper.getNameById(experiment.getTeacherId()));
            stundentApplyShowDTOList.add(stundentApplyShowDTO);
        }
        return stundentApplyShowDTOList;
    }

    @Override
    @Transactional
    public void insertToApplyExperiment(String experimentId, String experimentName, String teacherId) {

        if(isAuditingSuccess(experimentId)){
            throw new StudentRepeatApplyException();
        }
        //插入到申请表
        ApplyExperiment applyExperiment=new ApplyExperiment();
        Long experimentIdByInt= Long.parseLong(experimentId);
        Integer teacherIdByInt=Integer.parseInt(teacherId);
        applyExperiment.setExperimentId(experimentIdByInt);
        applyExperiment.setStudentName(ShiroKit.getUser().getName());
        applyExperiment.setExperimentName(experimentName);
        applyExperiment.setStatus(AuditingEnum.WAIT.getCode());
        applyExperiment.setStudentId(ShiroKit.getUser().getId());
        applyExperiment.setTeacherId(teacherIdByInt);
        applyExperimentService.insert(applyExperiment);
    }

    @Override
    public List<StudentApplyDetailDTO> convertApplyeExperimentToStudentApplyDetailDTO(List<ApplyExperiment> applyExperimentList) {
        List<StudentApplyDetailDTO> studentApplyDetailDTOList=new ArrayList<>();
        for(ApplyExperiment applyExperiment:applyExperimentList){
            StudentApplyDetailDTO studentApplyDetailDTO=new StudentApplyDetailDTO();
            BeanUtils.copyProperties(applyExperiment,studentApplyDetailDTO);
            studentApplyDetailDTO.setStatus(AuditingEnum.getMsg(applyExperiment.getStatus()));
            studentApplyDetailDTO.setTeacherName(userMapper.getNameById(applyExperiment.getTeacherId()));
            studentApplyDetailDTOList.add(studentApplyDetailDTO);
        }
        return studentApplyDetailDTOList;
    }

    @Override
    public List<StudentScoreDTO> convertScoreToStudentScoreDTO(List<Score> scoreList) {
        List<StudentScoreDTO> studentScoreDTOList=new ArrayList<>();
        for(Score score:scoreList){
            StudentScoreDTO studentScoreDTO=new StudentScoreDTO();
            BeanUtils.copyProperties(score,studentScoreDTO);
            studentScoreDTOList.add(studentScoreDTO);
        }
        return studentScoreDTOList;
    }

    @Override
    @Transactional
    public void studentAttend(Long scoreId) {

        EntityWrapper<Score> scoreEntityWrapper=new EntityWrapper<>();
        scoreEntityWrapper.eq("id",scoreId);
        Score score=scoreService.selectOne(scoreEntityWrapper);
        Integer nowAttendanceNum=score.getAttendanceNum();

        //获取该实验总课程数
        EntityWrapper<Experiment> experimentEntityWrapper=new EntityWrapper<>();
        experimentEntityWrapper.eq("experiment_id",score.getExperimentId());
        Experiment experiment=experimentService.selectOne(experimentEntityWrapper);
        Integer needAttendanceNum=experiment.getCourseNum();
        if(nowAttendanceNum>=needAttendanceNum){
            throw new AttendMaxException();
        }else {
            score.setAttendanceNum(nowAttendanceNum+1);
            scoreService.updateById(score);
        }
    }

}
