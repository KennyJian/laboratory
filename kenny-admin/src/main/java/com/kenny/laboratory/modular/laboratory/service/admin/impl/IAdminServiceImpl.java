package com.kenny.laboratory.modular.laboratory.service.admin.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kenny.laboratory.modular.laboratory.dto.admin.AuditingLaboratoryShowDTO;
import com.kenny.laboratory.modular.laboratory.labenum.AuditingEnum;
import com.kenny.laboratory.modular.laboratory.service.IApplyLaboratoryService;
import com.kenny.laboratory.modular.laboratory.service.admin.IAdminService;
import com.kenny.laboratory.modular.system.dao.UserMapper;
import com.kenny.laboratory.modular.system.model.ApplyLaboratory;
import com.kenny.laboratory.modular.system.model.User;
import com.kenny.laboratory.modular.system.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IAdminServiceImpl implements IAdminService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IApplyLaboratoryService applyLaboratoryService;

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
