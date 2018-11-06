package com.kenny.laboratory.modular.laboratory.controller.student;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kenny.laboratory.core.base.controller.BaseController;
import com.kenny.laboratory.core.common.annotion.Permission;
import com.kenny.laboratory.core.common.exception.BizExceptionEnum;
import com.kenny.laboratory.core.exception.GunsException;
import com.kenny.laboratory.core.log.LogObjectHolder;
import com.kenny.laboratory.core.shiro.ShiroKit;
import com.kenny.laboratory.core.util.ToolUtil;
import com.kenny.laboratory.modular.laboratory.service.IScoreService;
import com.kenny.laboratory.modular.laboratory.service.student.IStudentService;
import com.kenny.laboratory.modular.system.model.Score;
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
 * 学生成绩查看和签到控制器
 *
 * @author kenny
 * @Date 2018-11-02 15:03:28
 */
@Controller
@RequestMapping("/student/score")
public class StudentScoreController extends BaseController {

    private String PREFIX = "/laboratory/student/score/";

    @Autowired
    private IScoreService scoreService;

    @Autowired
    private IStudentService studentService;

    /**
     * 跳转到成绩管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "score.html";
    }


    /**
     * 获取成绩管理列表
     */
    @Permission
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(
    @RequestParam(required = false) String experimentName,
    @RequestParam(required = false) String teacherName){
        EntityWrapper<Score> entityWrapper=new EntityWrapper<>();
        if(ToolUtil.isNotEmpty(experimentName)){
            entityWrapper.like("experimentName",experimentName);
        }
        if(ToolUtil.isNotEmpty(teacherName)){
            entityWrapper.like("teacherName",teacherName);
        }
        entityWrapper.eq("student_id", ShiroKit.getUser().getId());

        return studentService.convertScoreToStudentScoreDTO(scoreService.selectList(entityWrapper));
    }

    /**
     * 打卡
     */
    @Permission
    @RequestMapping(value = "/attend")
    @ResponseBody
    public Object delete(@RequestParam String scoreId) {
        Long idByInt= Long.parseLong(scoreId);
        studentService.studentAttend(idByInt);
        return SUCCESS_TIP;
    }

}
