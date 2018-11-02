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
import com.kenny.laboratory.modular.system.model.Experiment;
import com.kenny.laboratory.modular.laboratory.service.IExperimentService;

/**
 * 实验管理控制器
 *
 * @author fengshuonan
 * @Date 2018-11-02 15:01:21
 */
@Controller
@RequestMapping("/experiment")
public class ExperimentController extends BaseController {

    private String PREFIX = "/laboratory/experiment/";

    @Autowired
    private IExperimentService experimentService;


    /**
     * 跳转到实验管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "experiment.html";
    }

    /**
     * 跳转到添加实验管理
     */
    @RequestMapping("/experiment_add")
    public String experimentAdd() {
        return PREFIX + "experiment_add.html";
    }

    /**
     * 跳转到修改实验管理
     */
    @RequestMapping("/experiment_update/{experimentId}")
    public String experimentUpdate(@PathVariable String experimentId, Model model) {
        Long idByInt= Long.parseLong(experimentId);
        Experiment experiment = experimentService.selectById(idByInt);
        model.addAttribute("item",experiment);
        LogObjectHolder.me().set(experiment);
        return PREFIX + "experiment_edit.html";
    }

    /**
     * 获取实验管理列表
     */
    @Permission
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(    @RequestParam(required = false) String experimentId,
    @RequestParam(required = false) String experimentName,
    @RequestParam(required = false) String courseId,
    @RequestParam(required = false) String courseNum,
    @RequestParam(required = false) String equipmentNeedNum,
    @RequestParam(required = false) String teacherId){
        EntityWrapper<Experiment> entityWrapper=new EntityWrapper<>();
        if(ToolUtil.isNotEmpty(experimentId)){
            entityWrapper.like("experimentId",experimentId);
        }
        if(ToolUtil.isNotEmpty(experimentName)){
            entityWrapper.like("experimentName",experimentName);
        }
        if(ToolUtil.isNotEmpty(courseId)){
            entityWrapper.like("courseId",courseId);
        }
        if(ToolUtil.isNotEmpty(courseNum)){
            entityWrapper.like("courseNum",courseNum);
        }
        if(ToolUtil.isNotEmpty(equipmentNeedNum)){
            entityWrapper.like("equipmentNeedNum",equipmentNeedNum);
        }
        if(ToolUtil.isNotEmpty(teacherId)){
            entityWrapper.like("teacherId",teacherId);
        }
        return experimentService.selectList(entityWrapper);
    }

    /**
     * 新增实验管理
     */
    @Permission
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(@Valid Experiment experiment, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        experimentService.insert(experiment);
        return SUCCESS_TIP;
    }

    /**
     * 删除实验管理
     */
    @Permission
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String experimentId) {
        Long idByInt= Long.parseLong(experimentId);
        experimentService.deleteById(idByInt);
        return SUCCESS_TIP;
    }

    /**
     * 修改实验管理
     */
    @Permission
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(@Valid Experiment experiment, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        experimentService.updateById(experiment);
        return SUCCESS_TIP;
    }

    /**
     * 实验管理详情
     */
    @RequestMapping(value = "/detail/{experimentId}")
    @ResponseBody
    public Object detail(@PathVariable("experimentId") String experimentId) {
        Long idByInt= Long.parseLong(experimentId);
        return experimentService.selectById(idByInt);
    }
}
