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
import com.kenny.laboratory.modular.system.model.ApplyExperiment;
import com.kenny.laboratory.modular.laboratory.service.IApplyExperimentService;

/**
 * 实验选课管理控制器
 *
 * @author kenny
 * @Date 2018-11-02 15:03:14
 */
@Controller
@RequestMapping("/applyExperiment")
public class ApplyExperimentController extends BaseController {

    private String PREFIX = "/laboratory/applyExperiment/";

    @Autowired
    private IApplyExperimentService applyExperimentService;


    /**
     * 跳转到实验选课管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "applyExperiment.html";
    }

    /**
     * 跳转到添加实验选课管理
     */
    @RequestMapping("/applyExperiment_add")
    public String applyExperimentAdd() {
        return PREFIX + "applyExperiment_add.html";
    }

    /**
     * 跳转到修改实验选课管理
     */
    @RequestMapping("/applyExperiment_update/{applyExperimentId}")
    public String applyExperimentUpdate(@PathVariable String applyExperimentId, Model model) {
        Long idByInt= Long.parseLong(applyExperimentId);
        ApplyExperiment applyExperiment = applyExperimentService.selectById(idByInt);
        model.addAttribute("item",applyExperiment);
        LogObjectHolder.me().set(applyExperiment);
        return PREFIX + "applyExperiment_edit.html";
    }

    /**
     * 获取实验选课管理列表
     */
    @Permission
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(    @RequestParam(required = false) String id,
    @RequestParam(required = false) String experimentId,
    @RequestParam(required = false) String studentName,
    @RequestParam(required = false) String experimentName,
    @RequestParam(required = false) String status,
    @RequestParam(required = false) String studentId,
    @RequestParam(required = false) String teacherId){
        EntityWrapper<ApplyExperiment> entityWrapper=new EntityWrapper<>();
        if(ToolUtil.isNotEmpty(id)){
            entityWrapper.like("id",id);
        }
        if(ToolUtil.isNotEmpty(experimentId)){
            entityWrapper.like("experimentId",experimentId);
        }
        if(ToolUtil.isNotEmpty(studentName)){
            entityWrapper.like("studentName",studentName);
        }
        if(ToolUtil.isNotEmpty(experimentName)){
            entityWrapper.like("experimentName",experimentName);
        }
        if(ToolUtil.isNotEmpty(status)){
            entityWrapper.like("status",status);
        }
        if(ToolUtil.isNotEmpty(studentId)){
            entityWrapper.like("studentId",studentId);
        }
        if(ToolUtil.isNotEmpty(teacherId)){
            entityWrapper.like("teacherId",teacherId);
        }
//        if (!ShiroKit.isAdmin()){
//            entityWrapper.in("deptid",ShiroKit.getDeptDataScope());
//        }
        return applyExperimentService.selectList(entityWrapper);
    }

    /**
     * 新增实验选课管理
     */
    @Permission
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(@Valid ApplyExperiment applyExperiment, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        applyExperimentService.insert(applyExperiment);
        return SUCCESS_TIP;
    }

    /**
     * 删除实验选课管理
     */
    @Permission
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String applyExperimentId) {
        Long idByInt= Long.parseLong(applyExperimentId);
        applyExperimentService.deleteById(idByInt);
        return SUCCESS_TIP;
    }

    /**
     * 修改实验选课管理
     */
    @Permission
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(@Valid ApplyExperiment applyExperiment, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        applyExperimentService.updateById(applyExperiment);
        return SUCCESS_TIP;
    }

    /**
     * 实验选课管理详情
     */
    @RequestMapping(value = "/detail/{applyExperimentId}")
    @ResponseBody
    public Object detail(@PathVariable("applyExperimentId") String applyExperimentId) {
        Long idByInt= Long.parseLong(applyExperimentId);
        return applyExperimentService.selectById(idByInt);
    }
}
