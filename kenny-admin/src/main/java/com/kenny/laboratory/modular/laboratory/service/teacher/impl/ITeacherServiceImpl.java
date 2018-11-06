package com.kenny.laboratory.modular.laboratory.service.teacher.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kenny.laboratory.core.shiro.ShiroKit;
import com.kenny.laboratory.core.shiro.ShiroUser;
import com.kenny.laboratory.modular.laboratory.dto.teacher.*;
import com.kenny.laboratory.modular.laboratory.exception.GradeException;
import com.kenny.laboratory.modular.laboratory.exception.StudentRepeatApplyException;
import com.kenny.laboratory.modular.laboratory.exception.ValidateException;
import com.kenny.laboratory.modular.laboratory.labenum.AuditingEnum;
import com.kenny.laboratory.modular.laboratory.labenum.ElectrifyEnum;
import com.kenny.laboratory.modular.laboratory.service.*;
import com.kenny.laboratory.modular.laboratory.service.student.IStudentService;
import com.kenny.laboratory.modular.laboratory.service.teacher.ITeacherService;
import com.kenny.laboratory.modular.laboratory.utils.ValidateUserUtil;
import com.kenny.laboratory.modular.system.dao.UserMapper;
import com.kenny.laboratory.modular.system.model.*;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
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
    private ILaboratoryService laboratoryService;

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
    @Transactional
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
    @Transactional
    public void auditingFail(Long applyExperimentId) {
        EntityWrapper<ApplyExperiment> applyExperimentEntityWrapper=new EntityWrapper<>();
        applyExperimentEntityWrapper.eq("id",applyExperimentId);
        ApplyExperiment applyExperiment=applyExperimentService.selectOne(applyExperimentEntityWrapper);
        applyExperiment.setStatus(AuditingEnum.FAIL.getCode());
        applyExperimentService.updateById(applyExperiment);

        //删除学生表的信息
        EntityWrapper<Score> scoreEntityWrapper=new EntityWrapper<>();
        scoreEntityWrapper.eq("student_id",applyExperiment.getStudentId());
        scoreEntityWrapper.eq("experiment_id",applyExperiment.getExperimentId());
        scoreService.delete(scoreEntityWrapper);

    }

    @Override
    public List<TeacherScoreDTO> covertScoreToTeacherScoreDTO(List<Score> scoreList) {
        List<TeacherScoreDTO> teacherScoreDTOList=new ArrayList<>();
        for(Score score:scoreList){
            TeacherScoreDTO teacherScoreDTO=new TeacherScoreDTO();
            BeanUtils.copyProperties(score,teacherScoreDTO);
            teacherScoreDTOList.add(teacherScoreDTO);
        }
        return teacherScoreDTOList;
    }

    @Override
    @Transactional
    public void teacherGrade(Score score) {
        //获取选中的成绩记录
        EntityWrapper<Score> scoreEntityWrapper=new EntityWrapper<>();
        scoreEntityWrapper.eq("id",score.getId());
        Score slectScore=scoreService.selectOne(scoreEntityWrapper);
        //判断是否达到评分条件
        if(!isAchieveAttendNum(slectScore)){
            throw new GradeException();
        }
        //修改分数
        slectScore.setScore(score.getScore());
        scoreService.updateById(slectScore);

    }

    @Override
    public boolean isAchieveAttendNum(Score score) {
        EntityWrapper<Experiment> experimentEntityWrapper=new EntityWrapper<>();
        experimentEntityWrapper.eq("experiment_id",score.getExperimentId());
        Experiment experiment=experimentService.selectOne(experimentEntityWrapper);
        //未达到评分条件
        if(score.getAttendanceNum()<=(experiment.getCourseNum()*2/3)){
            return false;
        }
        return true;
    }

    @Override
    public List<TacherSignInDTO> applyLaboratoryconvertToTacherSignInDTO(List<ApplyLaboratory> applyLaboratoryList) {
        List<TacherSignInDTO> tacherSignInDTOList=new ArrayList<>();
        for(ApplyLaboratory applyLaboratory:applyLaboratoryList){
            TacherSignInDTO tacherSignInDTO=new TacherSignInDTO();
            BeanUtils.copyProperties(applyLaboratory,tacherSignInDTO);
            Laboratory laboratory=laboratoryService.selectById(applyLaboratory.getLaboratoryId());
            tacherSignInDTO.setIsElectrify(ElectrifyEnum.getMsg(laboratory.getIsElectrify()));
            tacherSignInDTOList.add(tacherSignInDTO);
        }
        return tacherSignInDTOList;
    }

    @Override
    @Transactional
    public void teacherSignIn(Long selectLaboratoryId,String pwd) {
        //验证密码是否正确
        //封装请求账号密码为shiro可验证的token
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(ShiroKit.getUser().getAccount(), pwd.toCharArray());
//        获取数据库中的账号密码，准备比对
        User user = userMapper.getByAccount(ShiroKit.getUser().getAccount());
        String credentials = user.getPassword();
        String salt = user.getSalt();
        ByteSource credentialsSalt = new Md5Hash(salt);
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                new ShiroUser(), credentials, credentialsSalt, "");

        //校验用户账号密码
        HashedCredentialsMatcher md5CredentialsMatcher = new HashedCredentialsMatcher();
        md5CredentialsMatcher.setHashAlgorithmName(ShiroKit.hashAlgorithmName);
        md5CredentialsMatcher.setHashIterations(ShiroKit.hashIterations);
        boolean passwordTrueFlag = md5CredentialsMatcher.doCredentialsMatch(
                usernamePasswordToken, simpleAuthenticationInfo);
        //密码正确
        if (passwordTrueFlag) {
            Laboratory laboratory=laboratoryService.selectById(selectLaboratoryId);
            laboratory.setIsElectrify(ElectrifyEnum.OPEN.getCode());
            laboratoryService.updateById(laboratory);
        } else {
            throw new ValidateException();
        }

    }
}
