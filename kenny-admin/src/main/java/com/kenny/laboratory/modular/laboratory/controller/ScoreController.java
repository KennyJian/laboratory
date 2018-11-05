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
import com.kenny.laboratory.modular.system.model.Score;
import com.kenny.laboratory.modular.laboratory.service.IScoreService;

/**
 * 成绩管理控制器
 *
 * @author kenny
 * @Date 2018-11-02 15:03:28
 */
@Controller
@RequestMapping("/score")
public class ScoreController extends BaseController {

    private String PREFIX = "/laboratory/score/";

    @Autowired
    private IScoreService scoreService;


    /**
     * 跳转到成绩管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "score.html";
    }

    /**
     * 跳转到添加成绩管理
     */
    @RequestMapping("/score_add")
    public String scoreAdd() {
        return PREFIX + "score_add.html";
    }

    /**
     * 跳转到修改成绩管理
     */
    @RequestMapping("/score_update/{scoreId}")
    public String scoreUpdate(@PathVariable String scoreId, Model model) {
        Long idByInt= Long.parseLong(scoreId);
        Score score = scoreService.selectById(idByInt);
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
    public Object list(    @RequestParam(required = false) String id,
    @RequestParam(required = false) String studentId,
    @RequestParam(required = false) String experimentId,
    @RequestParam(required = false) String studentName,
    @RequestParam(required = false) String experimentName,
    @RequestParam(required = false) String attendanceNum,
    @RequestParam(required = false) String score,
    @RequestParam(required = false) String teacherName,
    @RequestParam(required = false) String teacherId){
        EntityWrapper<Score> entityWrapper=new EntityWrapper<>();
        if(ToolUtil.isNotEmpty(id)){
            entityWrapper.like("id",id);
        }
        if(ToolUtil.isNotEmpty(studentId)){
            entityWrapper.like("studentId",studentId);
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
        if(ToolUtil.isNotEmpty(attendanceNum)){
            entityWrapper.like("attendanceNum",attendanceNum);
        }
        if(ToolUtil.isNotEmpty(score)){
            entityWrapper.like("score",score);
        }
        if(ToolUtil.isNotEmpty(teacherName)){
            entityWrapper.like("teacherName",teacherName);
        }
        if(ToolUtil.isNotEmpty(teacherId)){
            entityWrapper.like("teacherId",teacherId);
        }
        return scoreService.selectList(entityWrapper);
    }

    /**
     * 新增成绩管理
     */
    @Permission
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(@Valid Score score, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        scoreService.insert(score);
        return SUCCESS_TIP;
    }

    /**
     * 删除成绩管理
     */
    @Permission
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String scoreId) {
        Long idByInt= Long.parseLong(scoreId);
        scoreService.deleteById(idByInt);
        return SUCCESS_TIP;
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
        scoreService.updateById(score);
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
