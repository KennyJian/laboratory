package com.kenny.laboratory.modular.laboratory.service.admin.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kenny.laboratory.modular.laboratory.dto.admin.AuditingLaboratoryShowDTO;
import com.kenny.laboratory.modular.laboratory.exception.EquipmentNotEnoughException;
import com.kenny.laboratory.modular.laboratory.exception.LaboratoryOccupiedException;
import com.kenny.laboratory.modular.laboratory.labenum.AuditingEnum;
import com.kenny.laboratory.modular.laboratory.service.IApplyLaboratoryService;
import com.kenny.laboratory.modular.laboratory.service.IExperimentService;
import com.kenny.laboratory.modular.laboratory.service.ILaboratoryService;
import com.kenny.laboratory.modular.laboratory.service.admin.IAdminService;
import com.kenny.laboratory.modular.system.dao.UserMapper;
import com.kenny.laboratory.modular.system.model.ApplyLaboratory;
import com.kenny.laboratory.modular.system.model.Experiment;
import com.kenny.laboratory.modular.system.model.Laboratory;
import com.kenny.laboratory.modular.system.model.User;
import com.kenny.laboratory.modular.system.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class IAdminServiceImpl implements IAdminService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IApplyLaboratoryService applyLaboratoryService;

    @Autowired
    private IExperimentService experimentService;

    @Autowired
    private ILaboratoryService laboratoryService;

    @Override
    public List<AuditingLaboratoryShowDTO> convertAuditingLaboratoryShow(List<ApplyLaboratory> applyLaboratoryList) {


        List<AuditingLaboratoryShowDTO> auditingLaboratoryShowDTOList=new ArrayList<>();
        for (ApplyLaboratory applyLaboratory:applyLaboratoryList){
            AuditingLaboratoryShowDTO auditingLaboratoryShowDTO=new AuditingLaboratoryShowDTO();
            BeanUtils.copyProperties(applyLaboratory,auditingLaboratoryShowDTO);
            //通过老师ID获取老师名字
            auditingLaboratoryShowDTO.setTeacherName(userMapper.getNameById(applyLaboratory.getTeacherId()));
            //改变状态给用户的显示方式
            auditingLaboratoryShowDTO.setStatus(AuditingEnum.getMsg(applyLaboratory.getStatus()));
            auditingLaboratoryShowDTOList.add(auditingLaboratoryShowDTO);
        }

        return auditingLaboratoryShowDTOList;
    }

    @Override
    public void auditingSuccess(Long id) {
        //获取实验申请记录对象
        EntityWrapper<ApplyLaboratory> applyLaboratoryEntityWrapper=new EntityWrapper<>();
        applyLaboratoryEntityWrapper.eq("id",id);
        ApplyLaboratory applyLaboratoryNow=applyLaboratoryService.selectOne(applyLaboratoryEntityWrapper);
        //获取实验对象
        EntityWrapper<Experiment> experimentEntityWrapper=new EntityWrapper<>();
        experimentEntityWrapper.eq("experiment_id",applyLaboratoryNow.getExperimentId());
        Experiment experiment=experimentService.selectOne(experimentEntityWrapper);
        //获取实验室对象
        EntityWrapper<Laboratory> laboratoryEntityWrapper=new EntityWrapper<>();
        laboratoryEntityWrapper.eq("laboratory_id",applyLaboratoryNow.getLaboratoryId());
        Laboratory laboratory=laboratoryService.selectOne(laboratoryEntityWrapper);
        //再次判断实验室设备是否足够(实验室设备可能再申请后改变了)
        if (experiment.getEquipmentNeedNum()>laboratory.getLaboratoryEquipmentNum()){
            throw new EquipmentNotEnoughException();
        }

        //判断实验室是否被占用
        EntityWrapper<ApplyLaboratory> applyLaboratoryEntityWrapper2=new EntityWrapper<>();
        applyLaboratoryEntityWrapper2.eq("laboratory_id",applyLaboratoryNow.getLaboratoryId());
        applyLaboratoryEntityWrapper2.eq("status",1);
        List<ApplyLaboratory> applyLaboratoryList=applyLaboratoryService.selectList(applyLaboratoryEntityWrapper2);
        for(ApplyLaboratory applyLaboratory:applyLaboratoryList){
            if(applyLaboratoryNow.getApplyBeginTime().equals(applyLaboratory.getApplyBeginTime())||applyLaboratoryNow.getApplyBeginTime().equals(applyLaboratory.getApplyEndTime())){
                throw new LaboratoryOccupiedException();
            }
            if(applyLaboratoryNow.getApplyEndTime().equals(applyLaboratory.getApplyBeginTime())||applyLaboratoryNow.getApplyEndTime().equals(applyLaboratory.getApplyEndTime())){
                throw new LaboratoryOccupiedException();
            }
            if(applyLaboratoryNow.getApplyBeginTime().before(applyLaboratory.getApplyEndTime())&&applyLaboratoryNow.getApplyBeginTime().after(applyLaboratory.getApplyBeginTime())){
                throw new LaboratoryOccupiedException();
            }
            if(applyLaboratoryNow.getApplyEndTime().before(applyLaboratory.getApplyEndTime())&&applyLaboratoryNow.getApplyEndTime().after(applyLaboratory.getApplyBeginTime())){
                throw new LaboratoryOccupiedException();
            }

        }
        //审核成功
        applyLaboratoryNow.setStatus(AuditingEnum.SUCCESS.getCode());
        applyLaboratoryService.updateById(applyLaboratoryNow);
    }

    @Override
    public void auditingFail(Long id) {
        EntityWrapper<ApplyLaboratory> applyLaboratoryEntityWrapper=new EntityWrapper<>();
        applyLaboratoryEntityWrapper.eq("id",id);
        ApplyLaboratory applyLaboratory=applyLaboratoryService.selectOne(applyLaboratoryEntityWrapper);
        applyLaboratory.setStatus(AuditingEnum.FAIL.getCode());
        applyLaboratoryService.updateById(applyLaboratory);
    }
}
