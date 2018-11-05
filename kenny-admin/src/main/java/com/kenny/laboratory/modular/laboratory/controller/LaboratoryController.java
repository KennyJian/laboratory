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
import com.kenny.laboratory.modular.system.model.Laboratory;
import com.kenny.laboratory.modular.laboratory.service.ILaboratoryService;

/**
 * 实验室管理控制器
 *
 * @author kenny
 * @Date 2018-11-02 15:02:29
 */
@Controller
@RequestMapping("/laboratory")
public class LaboratoryController extends BaseController {

    private String PREFIX = "/laboratory/laboratory/";

    @Autowired
    private ILaboratoryService laboratoryService;


    /**
     * 跳转到实验室管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "laboratory.html";
    }

    /**
     * 跳转到添加实验室管理
     */
    @RequestMapping("/laboratory_add")
    public String laboratoryAdd() {
        return PREFIX + "laboratory_add.html";
    }

    /**
     * 跳转到修改实验室管理
     */
    @RequestMapping("/laboratory_update/{laboratoryId}")
    public String laboratoryUpdate(@PathVariable String laboratoryId, Model model) {
        Long idByInt= Long.parseLong(laboratoryId);
        Laboratory laboratory = laboratoryService.selectById(idByInt);
        model.addAttribute("item",laboratory);
        LogObjectHolder.me().set(laboratory);
        return PREFIX + "laboratory_edit.html";
    }

    /**
     * 获取实验室管理列表
     */
    @Permission
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(    @RequestParam(required = false) String laboratoryId,
    @RequestParam(required = false) String laboratoryName,
    @RequestParam(required = false) String laboratoryLocation,
    @RequestParam(required = false) String laboratoryEquipmentNum,
    @RequestParam(required = false) String isOpen,
    @RequestParam(required = false) String isElectrify){
        EntityWrapper<Laboratory> entityWrapper=new EntityWrapper<>();
        if(ToolUtil.isNotEmpty(laboratoryId)){
            entityWrapper.like("laboratoryId",laboratoryId);
        }
        if(ToolUtil.isNotEmpty(laboratoryName)){
            entityWrapper.like("laboratoryName",laboratoryName);
        }
        if(ToolUtil.isNotEmpty(laboratoryLocation)){
            entityWrapper.like("laboratoryLocation",laboratoryLocation);
        }
        if(ToolUtil.isNotEmpty(laboratoryEquipmentNum)){
            entityWrapper.like("laboratoryEquipmentNum",laboratoryEquipmentNum);
        }
        if(ToolUtil.isNotEmpty(isOpen)){
            entityWrapper.like("isOpen",isOpen);
        }
        if(ToolUtil.isNotEmpty(isElectrify)){
            entityWrapper.like("isElectrify",isElectrify);
        }

        return laboratoryService.selectList(entityWrapper);
    }

    /**
     * 新增实验室管理
     */
    @Permission
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(@Valid Laboratory laboratory, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        laboratoryService.insert(laboratory);
        return SUCCESS_TIP;
    }

    /**
     * 删除实验室管理
     */
    @Permission
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String laboratoryId) {
        Long idByInt= Long.parseLong(laboratoryId);
        laboratoryService.deleteById(idByInt);
        return SUCCESS_TIP;
    }

    /**
     * 修改实验室管理
     */
    @Permission
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(@Valid Laboratory laboratory, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        laboratoryService.updateById(laboratory);
        return SUCCESS_TIP;
    }

    /**
     * 实验室管理详情
     */
    @RequestMapping(value = "/detail/{laboratoryId}")
    @ResponseBody
    public Object detail(@PathVariable("laboratoryId") String laboratoryId) {
        Long idByInt= Long.parseLong(laboratoryId);
        return laboratoryService.selectById(idByInt);
    }
}
