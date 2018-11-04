package com.kenny.laboratory.modular.laboratory.controller.admin;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kenny.laboratory.core.base.controller.BaseController;
import com.kenny.laboratory.core.common.annotion.Permission;
import com.kenny.laboratory.core.common.exception.BizExceptionEnum;
import com.kenny.laboratory.core.exception.GunsException;
import com.kenny.laboratory.core.log.LogObjectHolder;
import com.kenny.laboratory.core.util.ToolUtil;
import com.kenny.laboratory.modular.laboratory.dto.admin.AuditingLaboratoryShowDTO;
import com.kenny.laboratory.modular.laboratory.dto.admin.EditCommentDTO;
import com.kenny.laboratory.modular.laboratory.service.IApplyLaboratoryService;
import com.kenny.laboratory.modular.laboratory.service.admin.IAdminService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 实验室申请管理控制器
 *
 * @author kenny
 * @Date 2018-11-02 15:02:52
 */
@Controller
@RequestMapping("/admin/auditingLaboratory")
public class AdminAuditingLaboratoryController extends BaseController {

    private String PREFIX = "/laboratory/admin/auditing/";

    @Autowired
    private IApplyLaboratoryService applyLaboratoryService;

    @Autowired
    private IAdminService adminService;


    /**
     * 跳转到实验室申请管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "applyLaboratory.html";
    }


    /**
     * 跳转到填写备注页面
     */
    @RequestMapping("/applyLaboratory_comment/{applyLaboratoryId}")
    public String applyLaboratoryUpdate(@PathVariable String applyLaboratoryId, Model model) {
        Long idByInt= Long.parseLong(applyLaboratoryId);
        ApplyLaboratory applyLaboratory = applyLaboratoryService.selectById(idByInt);
        model.addAttribute("item",applyLaboratory);
        LogObjectHolder.me().set(applyLaboratory);
        return PREFIX + "applyLaboratory_comment.html";
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
            entityWrapper.like("apply_begin_time",applyBeginTime);
        }
        if(ToolUtil.isNotEmpty(applyEndTime)){
            entityWrapper.like("apply_end_time",applyEndTime);
        }
        if(ToolUtil.isNotEmpty(status)){
            entityWrapper.eq("status",status);
        }

        List<ApplyLaboratory> applyLaboratoryList=applyLaboratoryService.selectList(entityWrapper);
        List<AuditingLaboratoryShowDTO> auditingLaboratoryShowDTOList=adminService.convertAuditingLaboratoryShow(applyLaboratoryList);

        return auditingLaboratoryShowDTOList;
    }


    /**
     * 通过实验室审核
     */
    @Permission
    @RequestMapping(value = "/auditing/success")
    @ResponseBody
    public Object auditingSuccess(@RequestParam String applyLaboratoryId) {
        Long idByInt= Long.parseLong(applyLaboratoryId);
        adminService.auditingSuccess(idByInt);
        return SUCCESS_TIP;
    }

    /**
     * 不通过实验室审核
     */
    @Permission
    @RequestMapping(value = "/auditing/fail")
    @ResponseBody
    public Object auditingFail(@RequestParam String applyLaboratoryId) {
        Long idByInt= Long.parseLong(applyLaboratoryId);
        adminService.auditingFail(idByInt);
        return SUCCESS_TIP;
    }

    /**
     * 提交备注
     */
    @Permission
    @RequestMapping(value = "/setComment")
    @ResponseBody
    public Object commitComment(@Valid EditCommentDTO editCommentDTO, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }

        ApplyLaboratory applyLaboratory=applyLaboratoryService.selectById(editCommentDTO.getId());
        applyLaboratory.setComment(editCommentDTO.getComment());

        applyLaboratoryService.updateById(applyLaboratory);
        return SUCCESS_TIP;
    }

}
