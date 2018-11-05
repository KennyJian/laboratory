package com.kenny.laboratory.modular.laboratory.controller.teacher;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kenny.laboratory.core.base.controller.BaseController;
import com.kenny.laboratory.core.common.annotion.Permission;
import com.kenny.laboratory.core.common.exception.BizExceptionEnum;
import com.kenny.laboratory.core.exception.GunsException;
import com.kenny.laboratory.core.log.LogObjectHolder;
import com.kenny.laboratory.core.shiro.ShiroKit;
import com.kenny.laboratory.core.util.ToolUtil;
import com.kenny.laboratory.modular.laboratory.service.IApplyExperimentService;
import com.kenny.laboratory.modular.laboratory.service.teacher.ITeacherService;
import com.kenny.laboratory.modular.system.model.ApplyExperiment;
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
 * 实验选课管理控制器
 *
 * @author kenny
 * @Date 2018-11-02 15:03:14
 */
@Controller
@RequestMapping("/teacher/auditing")
public class TeacherAuditingController extends BaseController {

    private String PREFIX = "/laboratory/teacher/auditing/";

    @Autowired
    private IApplyExperimentService applyExperimentService;

    @Autowired
    private ITeacherService teacherService;

    /**
     * 跳转到实验选课管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "auditingExperiment.html";
    }


    /**
     * 获取实验选课管理列表
     */
    @Permission
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(
    @RequestParam(required = false) String studentName,
    @RequestParam(required = false) String experimentName,
    @RequestParam(required = false) String status){
        EntityWrapper<ApplyExperiment> entityWrapper=new EntityWrapper<>();
        if(ToolUtil.isNotEmpty(studentName)){
            entityWrapper.like("student_name",studentName);
        }
        if(ToolUtil.isNotEmpty(experimentName)){
            entityWrapper.like("experiment_name",experimentName);
        }
        if(ToolUtil.isNotEmpty(status)){
            entityWrapper.eq("status",status);
        }
        entityWrapper.eq("teacher_id", ShiroKit.getUser().getId());

        return teacherService.convertApplyExperimentToAuditingExperimentDTO(applyExperimentService.selectList(entityWrapper));
    }


    /**
     * 通过学生选课
     */
    @Permission
    @RequestMapping(value = "/success")
    @ResponseBody
    public Object success(@RequestParam String applyExperimentId) {
        Long idByLong=Long.parseLong(applyExperimentId);
        teacherService.auditingSuccess(idByLong);
        return SUCCESS_TIP;
    }

    /**
     * 不通过学生选课
     */
    @Permission
    @RequestMapping(value = "/fail")
    @ResponseBody
    public Object fail(@RequestParam String applyExperimentId) {
        Long idByLong=Long.parseLong(applyExperimentId);
        teacherService.auditingFail(idByLong);
        return SUCCESS_TIP;
    }


}
