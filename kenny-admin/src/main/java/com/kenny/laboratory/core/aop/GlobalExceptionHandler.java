package com.kenny.laboratory.core.aop;

import com.kenny.laboratory.core.common.exception.BizExceptionEnum;
import com.kenny.laboratory.core.common.exception.InvalidKaptchaException;
import com.kenny.laboratory.core.exception.GunsException;
import com.kenny.laboratory.core.log.factory.LogTaskFactory;
import com.kenny.laboratory.core.support.HttpKit;
import com.kenny.laboratory.core.base.tips.ErrorTip;
import com.kenny.laboratory.core.log.LogManager;
import com.kenny.laboratory.core.shiro.ShiroKit;
import com.kenny.laboratory.modular.laboratory.exception.*;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.DisabledAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.lang.reflect.UndeclaredThrowableException;

/**
 * 全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 *
 * @author fengshuonan
 * @date 2016年11月12日 下午3:19:56
 */
@ControllerAdvice
@Order(-1)
public class GlobalExceptionHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 拦截业务异常
     */
    @ExceptionHandler(GunsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorTip notFount(GunsException e) {
        LogManager.me().executeLog(LogTaskFactory.exceptionLog(ShiroKit.getUser().getId(), e));
        HttpKit.getRequest().setAttribute("tip", e.getMessage());
        log.error("业务异常:", e);
        return new ErrorTip(e.getCode(), e.getMessage());
    }

    /**
     * 用户未登录异常
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String unAuth(AuthenticationException e) {
        log.error("用户未登陆：", e);
        return "/login.html";
    }

    /**
     * 账号被冻结异常
     */
    @ExceptionHandler(DisabledAccountException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String accountLocked(DisabledAccountException e, Model model) {
        String username = HttpKit.getRequest().getParameter("username");
        LogManager.me().executeLog(LogTaskFactory.loginLog(username, "账号被冻结", HttpKit.getIp()));
        model.addAttribute("tips", "账号被冻结");
        return "/login.html";
    }

    /**
     * 账号密码错误异常
     */
    @ExceptionHandler(CredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String credentials(CredentialsException e, Model model) {
        String username = HttpKit.getRequest().getParameter("username");
        LogManager.me().executeLog(LogTaskFactory.loginLog(username, "账号密码错误", HttpKit.getIp()));
        model.addAttribute("tips", "账号密码错误");
        return "/login.html";
    }

    /**
     * 验证码错误异常
     */
    @ExceptionHandler(InvalidKaptchaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String credentials(InvalidKaptchaException e, Model model) {
        String username = HttpKit.getRequest().getParameter("username");
        LogManager.me().executeLog(LogTaskFactory.loginLog(username, "验证码错误", HttpKit.getIp()));
        model.addAttribute("tips", "验证码错误");
        return "/login.html";
    }

    /**
     * 无权访问该资源异常
     */
    @ExceptionHandler(UndeclaredThrowableException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorTip credentials(UndeclaredThrowableException e) {
        HttpKit.getRequest().setAttribute("tip", "权限异常");
        log.error("权限异常!", e);
        return new ErrorTip(BizExceptionEnum.NO_PERMITION.getCode(), BizExceptionEnum.NO_PERMITION.getMessage());
    }

    @ExceptionHandler(EquipmentNotEnoughException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorTip applyError(EquipmentNotEnoughException e){
        HttpKit.getRequest().setAttribute("tip", "实验室设备不足");
        log.error("实验室设备不足!", e);
        return new ErrorTip(BizExceptionEnum.EQUIPMENT_NOT_ENOUGH.getCode(), BizExceptionEnum.EQUIPMENT_NOT_ENOUGH.getMessage());
    }


    @ExceptionHandler(ChooseDataException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorTip chooseDataError(ChooseDataException e){
        HttpKit.getRequest().setAttribute("tip", "申请结束时间不能小于开始时间");
        log.error("申请结束时间不能小于开始时间!", e);
        return new ErrorTip(BizExceptionEnum.CHOOSE_DARA_ERROR.getCode(), BizExceptionEnum.CHOOSE_DARA_ERROR.getMessage());
    }


    @ExceptionHandler(LaboratoryOccupiedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorTip laboratoryOccupied(LaboratoryOccupiedException e){
        HttpKit.getRequest().setAttribute("tip", "该时间段已被占用");
        log.error("该时间段已被占用!", e);
        return new ErrorTip(BizExceptionEnum.CHOOSE_DARA_OCCUPIED.getCode(), BizExceptionEnum.CHOOSE_DARA_OCCUPIED.getMessage());
    }

    @ExceptionHandler(StudentRepeatApplyException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorTip studentRepeatApply(StudentRepeatApplyException e){
        HttpKit.getRequest().setAttribute("tip", "学生重复申请选课");
        log.error("学生重复申请选课!", e);
        return new ErrorTip(BizExceptionEnum.STUDENT_REPEAT_APPLY.getCode(), BizExceptionEnum.STUDENT_REPEAT_APPLY.getMessage());
    }


    @ExceptionHandler(AttendMaxException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorTip attendMax(AttendMaxException e){
        HttpKit.getRequest().setAttribute("tip", "学生签到次数已达课程数");
        log.error("学生签到次数已达课程数!", e);
        return new ErrorTip(BizExceptionEnum.ATTEDN_MAX.getCode(), BizExceptionEnum.ATTEDN_MAX.getMessage());
    }
    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorTip notFount(RuntimeException e) {
        LogManager.me().executeLog(LogTaskFactory.exceptionLog(ShiroKit.getUser().getId(), e));
        HttpKit.getRequest().setAttribute("tip", "服务器未知运行时异常");
        log.error("运行时异常:", e);
        return new ErrorTip(BizExceptionEnum.SERVER_ERROR.getCode(), BizExceptionEnum.SERVER_ERROR.getMessage());
    }
}
