package com.kenny.laboratory.modular.laboratory.controller.student;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kenny.laboratory.core.base.controller.BaseController;
import com.kenny.laboratory.core.common.annotion.Permission;
import com.kenny.laboratory.core.common.exception.BizExceptionEnum;
import com.kenny.laboratory.core.exception.GunsException;
import com.kenny.laboratory.core.log.LogObjectHolder;
import com.kenny.laboratory.core.util.ToolUtil;
import com.kenny.laboratory.modular.laboratory.dto.student.StundentApplyShowDTO;
import com.kenny.laboratory.modular.laboratory.labenum.AuditingEnum;
import com.kenny.laboratory.modular.laboratory.service.IApplyExperimentService;
import com.kenny.laboratory.modular.laboratory.service.IApplyLaboratoryService;
import com.kenny.laboratory.modular.laboratory.service.IExperimentService;
import com.kenny.laboratory.modular.laboratory.service.student.IStudentService;
import com.kenny.laboratory.modular.system.dao.ApplyLaboratoryMapper;
import com.kenny.laboratory.modular.system.model.ApplyExperiment;
import com.kenny.laboratory.modular.system.model.ApplyLaboratory;
import com.kenny.laboratory.modular.system.model.Experiment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

/**
 * 实验选课管理控制器
 *
 * @author kenny
 * @Date 2018-11-02 15:03:14
 */
@Controller
@RequestMapping("/student/apply")
public class StudentApplyExperimentController extends BaseController {

    private String PREFIX = "/laboratory/student/applyExperiment/";

    @Autowired
    private IApplyExperimentService applyExperimentService;

    @Autowired
    private IExperimentService experimentService;

    @Autowired
    private ApplyLaboratoryMapper applyLaboratoryMapper;

    @Autowired
    private IStudentService studentService;


    /**
     * 跳转到实验选课管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "applyExperiment.html";
    }

    /**
     * 获取实验选课管理列表
     */
    /**
     * 获取实验室申请管理列表
     */
    @Permission
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String experimentName){
        EntityWrapper<Experiment> entityWrapper=new EntityWrapper<>();
        if(ToolUtil.isNotEmpty(experimentName)) {
            entityWrapper.like("experiment_name", experimentName);
        }
            entityWrapper.in("experiment_id",applyLaboratoryMapper.findExperimentIdByStatusSuccess());

        return studentService.convertExperientToStundentApplyShowDTO(experimentService.selectList(entityWrapper));
    }


    /**
     * 申请实验选课管理
     */
    @Permission
    @RequestMapping(value = "/apply")
    @ResponseBody
    public Object apply(@RequestParam String experimentId,
                        @RequestParam String experimentName,
                        @RequestParam String teacherId) {

        studentService.insertToApplyExperiment(experimentId,experimentName,teacherId);
        return SUCCESS_TIP;
    }

}
