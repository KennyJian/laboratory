package com.kenny.laboratory.modular.laboratory.controller.teacher;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kenny.laboratory.core.base.controller.BaseController;
import com.kenny.laboratory.core.common.annotion.Permission;
import com.kenny.laboratory.core.common.constant.Const;
import com.kenny.laboratory.core.common.exception.BizExceptionEnum;
import com.kenny.laboratory.core.exception.GunsException;
import com.kenny.laboratory.core.log.LogObjectHolder;
import com.kenny.laboratory.core.shiro.ShiroKit;
import com.kenny.laboratory.core.util.ToolUtil;
import com.kenny.laboratory.modular.laboratory.service.IApplyLaboratoryService;
import com.kenny.laboratory.modular.laboratory.service.teacher.ITeacherService;
import com.kenny.laboratory.modular.system.model.ApplyLaboratory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * 实验室申请管理控制器
 *
 * @author kenny
 * @Date 2018-11-02 15:02:52
 */
@Controller
@RequestMapping("/teacher/applydetail")
public class TeacherApplyDetailController extends BaseController {

    private String PREFIX = "/laboratory/teacher/applydetail/";

    @Autowired
    private IApplyLaboratoryService applyLaboratoryService;

    @Autowired
    private ITeacherService teacherService;


    /**
     * 跳转到实验室申请管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "applyLaboratoryDetail.html";
    }


    /**
     * 获取实验室申请管理列表
     */
    @Permission
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(
    @RequestParam(required = false) String experimentName,
    @RequestParam(required = false) String laboratoryName,
    @RequestParam(required = false) String applyBeginTime,
    @RequestParam(required = false) String applyEndTime,
    @RequestParam(required = false) String status){
        EntityWrapper<ApplyLaboratory> entityWrapper=new EntityWrapper<>();
        if(ToolUtil.isNotEmpty(experimentName)){
            entityWrapper.like("experiment_name",experimentName);
        }
        if(ToolUtil.isNotEmpty(laboratoryName)){
            entityWrapper.like("laboratory_name",laboratoryName);
        }
        if(ToolUtil.isNotEmpty(applyBeginTime)){
            entityWrapper.gt("apply_begin_time",applyBeginTime);
        }
        if(ToolUtil.isNotEmpty(applyEndTime)){
            entityWrapper.lt("apply_end_time",applyEndTime);
        }
        if(ToolUtil.isNotEmpty(status)){
            entityWrapper.like("status",status);
        }
            entityWrapper.eq("teacher_id", ShiroKit.getUser().getId());

        return teacherService.convertApplyLaboratoryToApplyLaboratoryDetailDTO(applyLaboratoryService.selectList(entityWrapper));
    }

}
