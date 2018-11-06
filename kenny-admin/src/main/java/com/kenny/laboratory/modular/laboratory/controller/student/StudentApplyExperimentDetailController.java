package com.kenny.laboratory.modular.laboratory.controller.student;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kenny.laboratory.core.base.controller.BaseController;
import com.kenny.laboratory.core.common.annotion.Permission;
import com.kenny.laboratory.core.common.exception.BizExceptionEnum;
import com.kenny.laboratory.core.exception.GunsException;
import com.kenny.laboratory.core.log.LogObjectHolder;
import com.kenny.laboratory.core.shiro.ShiroKit;
import com.kenny.laboratory.core.util.ToolUtil;
import com.kenny.laboratory.modular.laboratory.service.IApplyExperimentService;
import com.kenny.laboratory.modular.laboratory.service.student.IStudentService;
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
@RequestMapping("/student/applydetail")
public class StudentApplyExperimentDetailController extends BaseController {

    private String PREFIX = "/laboratory/student/applydetail/";


    @Autowired
    private IApplyExperimentService applyExperimentService;

    @Autowired
    private IStudentService studentService;


    /**
     * 跳转到实验选课管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "applyExperimentDetail.html";
    }


    /**
     * 获取实验选课管理列表
     */
    @Permission
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(
    @RequestParam(required = false) String experimentName,
    @RequestParam(required = false) String status){
        EntityWrapper<ApplyExperiment> entityWrapper=new EntityWrapper<>();
        if(ToolUtil.isNotEmpty(experimentName)){
            entityWrapper.like("experimentName",experimentName);
        }
        if(ToolUtil.isNotEmpty(status)){
            entityWrapper.eq("status",status);
        }
        entityWrapper.eq("student_id", ShiroKit.getUser().getId());


        return studentService.convertApplyeExperimentToStudentApplyDetailDTO(applyExperimentService.selectList(entityWrapper));
    }


}
