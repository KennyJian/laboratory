package com.kenny.laboratory.modular.laboratory.controller.teacher;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kenny.laboratory.core.base.controller.BaseController;
import com.kenny.laboratory.core.common.annotion.Permission;
import com.kenny.laboratory.core.common.exception.BizExceptionEnum;
import com.kenny.laboratory.core.exception.GunsException;
import com.kenny.laboratory.core.log.LogObjectHolder;
import com.kenny.laboratory.core.shiro.ShiroKit;
import com.kenny.laboratory.core.util.ToolUtil;
import com.kenny.laboratory.modular.laboratory.exception.GradeException;
import com.kenny.laboratory.modular.laboratory.service.IScoreService;
import com.kenny.laboratory.modular.laboratory.service.teacher.ITeacherService;
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
 * 成绩管理控制器
 *
 * @author kenny
 * @Date 2018-11-02 15:03:28
 */
@Controller
@RequestMapping("/teacher/score")
public class TeacherScoreController extends BaseController {

    private String PREFIX = "/laboratory/teacher/score/";

    @Autowired
    private IScoreService scoreService;

    @Autowired
    private ITeacherService teacherService;


    /**
     * 跳转到成绩管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "score.html";
    }


    /**
     * 跳转到修改成绩管理
     */
    @RequestMapping("/score_update/{scoreId}")
    public String scoreUpdate(@PathVariable String scoreId, Model model) {
        Long idByInt= Long.parseLong(scoreId);
        Score score = scoreService.selectById(idByInt);
//        //判断是否达到评分条件
//        if(!teacherService.isAchieveAttendNum(score)){
//            throw new GradeException();
//        }

        model.addAttribute("item",score);
        LogObjectHolder.me().set(score);
        return PREFIX + "score_edit.html";
    }

    /**
     * 获取成绩管理列表
     */
    @Permission
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(
    @RequestParam(required = false) String studentName,
    @RequestParam(required = false) String experimentName){
        EntityWrapper<Score> entityWrapper=new EntityWrapper<>();
        if(ToolUtil.isNotEmpty(studentName)){
            entityWrapper.like("studentName",studentName);
        }
        if(ToolUtil.isNotEmpty(experimentName)){
            entityWrapper.like("experimentName",experimentName);
        }
            entityWrapper.eq("teacher_id", ShiroKit.getUser().getId());
        return teacherService.covertScoreToTeacherScoreDTO(scoreService.selectList(entityWrapper));
    }

    /**
     * 修改成绩管理
     */
    @Permission
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(@Valid Score score, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        teacherService.teacherGrade(score);
        return SUCCESS_TIP;
    }

    /**
     * 成绩管理详情
     */
    @RequestMapping(value = "/detail/{scoreId}")
    @ResponseBody
    public Object detail(@PathVariable("scoreId") String scoreId) {
        Long idByInt= Long.parseLong(scoreId);
        return scoreService.selectById(idByInt);
    }
}
