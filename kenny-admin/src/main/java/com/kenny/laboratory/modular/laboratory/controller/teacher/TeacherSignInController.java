package com.kenny.laboratory.modular.laboratory.controller.teacher;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kenny.laboratory.core.base.controller.BaseController;
import com.kenny.laboratory.core.common.annotion.Permission;
import com.kenny.laboratory.core.log.LogObjectHolder;
import com.kenny.laboratory.core.shiro.ShiroKit;
import com.kenny.laboratory.core.util.ToolUtil;
import com.kenny.laboratory.modular.laboratory.labenum.AuditingEnum;
import com.kenny.laboratory.modular.laboratory.service.IApplyLaboratoryService;
import com.kenny.laboratory.modular.laboratory.service.teacher.ITeacherService;
import com.kenny.laboratory.modular.system.model.ApplyLaboratory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 实验室申请管理控制器
 *
 * @author kenny
 * @Date 2018-11-02 15:02:52
 */
@Controller
@RequestMapping("/teacher/sign")
public class TeacherSignInController extends BaseController {

    private String PREFIX = "/laboratory/teacher/sign/";

    @Autowired
    private IApplyLaboratoryService applyLaboratoryService;

    @Autowired
    private ITeacherService teacherService;


    /**
     * 跳转到实验室申请管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "sign.html";
    }

    /**
     * 跳转到添加实验室申请管理
     */
    @RequestMapping("/sign_in/{applyLaboratoryId}")
    public String signIn(@PathVariable String applyLaboratoryId, Model model) {
        Long idByInt= Long.parseLong(applyLaboratoryId);
        ApplyLaboratory applyLaboratory = applyLaboratoryService.selectById(idByInt);
        model.addAttribute("selectLaboratoryId",applyLaboratory.getLaboratoryId());
        LogObjectHolder.me().set(applyLaboratory);
        return PREFIX + "sign_in.html";
    }


    /**
     * 获取实验室申请管理列表
     */
    @Permission
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(
    @RequestParam(required = false) String experimentName,
    @RequestParam(required = false) String laboratoryName){
        EntityWrapper<ApplyLaboratory> entityWrapper=new EntityWrapper<>();
        if(ToolUtil.isNotEmpty(experimentName)){
            entityWrapper.like("experimentName",experimentName);
        }
        if(ToolUtil.isNotEmpty(laboratoryName)){
            entityWrapper.like("laboratoryName",laboratoryName);
        }
        entityWrapper.eq("status", AuditingEnum.SUCCESS.getCode());
        entityWrapper.eq("teacher_id", ShiroKit.getUser().getId());

        return teacherService.applyLaboratoryconvertToTacherSignInDTO(applyLaboratoryService.selectList(entityWrapper));
    }

    /**
     * 签入
     */
    @Permission
    @RequestMapping(value = "/signin")
    @ResponseBody
    public Object signin(@RequestParam String selectLaboratoryId,@RequestParam String pwd) {
        Long idByInt=Long.parseLong(selectLaboratoryId);
        teacherService.teacherSignIn(idByInt,pwd);
        return SUCCESS_TIP;
    }


}
