package com.kenny.laboratory.modular.laboratory.controller;

import com.kenny.laboratory.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.kenny.laboratory.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.kenny.laboratory.core.common.annotion.Permission;
import com.kenny.laboratory.core.common.exception.BizExceptionEnum;
import com.kenny.laboratory.core.exception.GunsException;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;
import com.kenny.laboratory.core.shiro.ShiroKit;
import com.kenny.laboratory.core.util.ToolUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kenny.laboratory.modular.system.model.ApplyLaboratory;
import com.kenny.laboratory.modular.laboratory.service.IApplyLaboratoryService;

/**
 * 实验室申请管理控制器
 *
 * @author kenny
 * @Date 2018-11-02 15:02:52
 */
@Controller
@RequestMapping("/applyLaboratory")
public class ApplyLaboratoryController extends BaseController {

    private String PREFIX = "/laboratory/applyLaboratory/";

    @Autowired
    private IApplyLaboratoryService applyLaboratoryService;


    /**
     * 跳转到实验室申请管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "applyLaboratory.html";
    }

    /**
     * 跳转到添加实验室申请管理
     */
    @RequestMapping("/applyLaboratory_add")
    public String applyLaboratoryAdd() {
        return PREFIX + "applyLaboratory_add.html";
    }

    /**
     * 跳转到修改实验室申请管理
     */
    @RequestMapping("/applyLaboratory_update/{applyLaboratoryId}")
    public String applyLaboratoryUpdate(@PathVariable String applyLaboratoryId, Model model) {
        Long idByInt= Long.parseLong(applyLaboratoryId);
        ApplyLaboratory applyLaboratory = applyLaboratoryService.selectById(idByInt);
        model.addAttribute("item",applyLaboratory);
        LogObjectHolder.me().set(applyLaboratory);
        return PREFIX + "applyLaboratory_edit.html";
    }

    /**
     * 获取实验室申请管理列表
     */
    @Permission
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(    @RequestParam(required = false) String id,
    @RequestParam(required = false) String experimentId,
    @RequestParam(required = false) String laboratoryId,
    @RequestParam(required = false) String experimentName,
    @RequestParam(required = false) String laboratoryName,
    @RequestParam(required = false) String applyBeginTime,
    @RequestParam(required = false) String applyEndTime,
    @RequestParam(required = false) String status,
    @RequestParam(required = false) String comment,
    @RequestParam(required = false) String teacherId){
        EntityWrapper<ApplyLaboratory> entityWrapper=new EntityWrapper<>();
        if(ToolUtil.isNotEmpty(id)){
            entityWrapper.like("id",id);
        }
        if(ToolUtil.isNotEmpty(experimentId)){
            entityWrapper.like("experimentId",experimentId);
        }
        if(ToolUtil.isNotEmpty(laboratoryId)){
            entityWrapper.like("laboratoryId",laboratoryId);
        }
        if(ToolUtil.isNotEmpty(experimentName)){
            entityWrapper.like("experimentName",experimentName);
        }
        if(ToolUtil.isNotEmpty(laboratoryName)){
            entityWrapper.like("laboratoryName",laboratoryName);
        }
        if(ToolUtil.isNotEmpty(applyBeginTime)){
            entityWrapper.like("applyBeginTime",applyBeginTime);
        }
        if(ToolUtil.isNotEmpty(applyEndTime)){
            entityWrapper.like("applyEndTime",applyEndTime);
        }
        if(ToolUtil.isNotEmpty(status)){
            entityWrapper.like("status",status);
        }
        if(ToolUtil.isNotEmpty(comment)){
            entityWrapper.like("comment",comment);
        }
        if(ToolUtil.isNotEmpty(teacherId)){
            entityWrapper.like("teacherId",teacherId);
        }
        return applyLaboratoryService.selectList(entityWrapper);
    }

    /**
     * 新增实验室申请管理
     */
    @Permission
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(@Valid ApplyLaboratory applyLaboratory, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        applyLaboratoryService.insert(applyLaboratory);
        return SUCCESS_TIP;
    }

    /**
     * 删除实验室申请管理
     */
    @Permission
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String applyLaboratoryId) {
        Long idByInt= Long.parseLong(applyLaboratoryId);
        applyLaboratoryService.deleteById(idByInt);
        return SUCCESS_TIP;
    }

    /**
     * 修改实验室申请管理
     */
    @Permission
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(@Valid ApplyLaboratory applyLaboratory, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        applyLaboratoryService.updateById(applyLaboratory);
        return SUCCESS_TIP;
    }

    /**
     * 实验室申请管理详情
     */
    @RequestMapping(value = "/detail/{applyLaboratoryId}")
    @ResponseBody
    public Object detail(@PathVariable("applyLaboratoryId") String applyLaboratoryId) {
        Long idByInt= Long.parseLong(applyLaboratoryId);
        return applyLaboratoryService.selectById(idByInt);
    }
}
