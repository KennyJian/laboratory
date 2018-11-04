package com.kenny.laboratory.modular.laboratory.controller.teacher;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kenny.laboratory.core.base.controller.BaseController;
import com.kenny.laboratory.core.common.annotion.Permission;
import com.kenny.laboratory.core.common.exception.BizExceptionEnum;
import com.kenny.laboratory.core.exception.GunsException;
import com.kenny.laboratory.core.log.LogObjectHolder;
import com.kenny.laboratory.core.shiro.ShiroKit;
import com.kenny.laboratory.core.util.ToolUtil;
import com.kenny.laboratory.modular.laboratory.dto.teacher.ApplyLaboratoryDTO;
import com.kenny.laboratory.modular.laboratory.dto.teacher.ExperimentApplyDTO;
import com.kenny.laboratory.modular.laboratory.exception.ChooseDataException;
import com.kenny.laboratory.modular.laboratory.exception.EquipmentNotEnoughException;
import com.kenny.laboratory.modular.laboratory.service.IExperimentService;
import com.kenny.laboratory.modular.laboratory.service.ILaboratoryService;
import com.kenny.laboratory.modular.laboratory.service.teacher.ITeacherService;
import com.kenny.laboratory.modular.system.model.Experiment;
import com.kenny.laboratory.modular.system.model.Laboratory;
import org.springframework.beans.BeanUtils;
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
 * 实验室管理控制器
 *
 * @author fengshuonan
 * @Date 2018-11-02 15:02:29
 */
@Controller
@RequestMapping("/teacher/laboratory")
public class TeacherLaboratoryController extends BaseController {

    private String PREFIX = "/laboratory/teacher/laboratory/";

    @Autowired
    private ILaboratoryService laboratoryService;

    @Autowired
    private ITeacherService teacherService;


    /**
     * 跳转到实验室管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "laboratory.html";
    }

    /**
     * 跳转到申请实验室
     */
    @RequestMapping("/laboratory_apply/{laboratoryId}")
    public String laboratoryApply(@PathVariable("laboratoryId") String laboratoryId,Model model) {
        //查询选中实验室信息
        Long idByInt= Long.parseLong(laboratoryId);
        Laboratory laboratory = laboratoryService.selectById(idByInt);
        model.addAttribute("item",laboratory);
        LogObjectHolder.me().set(laboratory);
        //查询老师已经发布的实验
        List<ExperimentApplyDTO> experimentApplyDTOList=teacherService.findAllExperimentByTeacher();
        model.addAttribute("experimentList",experimentApplyDTOList);
        LogObjectHolder.me().set(experimentApplyDTOList);
        return PREFIX + "laboratory_apply.html";
    }


    /**
     * 获取实验室管理列表
     */
    @Permission
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(
    @RequestParam(required = false) String laboratoryName,
    @RequestParam(required = false) String laboratoryLocation,
    @RequestParam(required = false) String laboratoryEquipmentNum){
        EntityWrapper<Laboratory> entityWrapper=new EntityWrapper<>();
        if(ToolUtil.isNotEmpty(laboratoryName)){
            entityWrapper.like("laboratory_name",laboratoryName);
        }
        if(ToolUtil.isNotEmpty(laboratoryLocation)){
            entityWrapper.like("laboratory_location",laboratoryLocation);
        }
        if(ToolUtil.isNotEmpty(laboratoryEquipmentNum)){
            entityWrapper.like("laboratory_equipment_num",laboratoryEquipmentNum);
        }
        entityWrapper.eq("is_open",1);

        return laboratoryService.selectList(entityWrapper);
    }

    /**
     * 新增实验室管理
     */
    @Permission
    @RequestMapping(value = "/apply")
    @ResponseBody
    public Object apply(@Valid ApplyLaboratoryDTO applyLaboratoryDTO, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }

        //时间选择异常
        if(applyLaboratoryDTO.getApplyBeginTime().after(applyLaboratoryDTO.getApplyEndTime())){
            throw new ChooseDataException();
        }

        //所选实验室设备不够异常
        if(!teacherService.isEquipmentEnough(applyLaboratoryDTO)){
            throw new EquipmentNotEnoughException();
        }

        teacherService.applyLaboratory(applyLaboratoryDTO);

        return SUCCESS_TIP;
    }


}
