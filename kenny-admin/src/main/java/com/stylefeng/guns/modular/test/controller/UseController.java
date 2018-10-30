package com.stylefeng.guns.modular.test.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.core.common.annotion.Permission;
import com.stylefeng.guns.core.common.exception.BizExceptionEnum;
import com.stylefeng.guns.core.exception.GunsException;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.ToolUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.modular.system.model.Use;
import com.stylefeng.guns.modular.test.service.IUseService;

/**
 * 测试控制器
 *
 * @author fengshuonan
 * @Date 2018-10-30 12:32:31
 */
@Controller
@RequestMapping("/use")
public class UseController extends BaseController {

    private String PREFIX = "/test/use/";

    @Autowired
    private IUseService useService;


    /**
     * 跳转到测试首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "use.html";
    }

    /**
     * 跳转到添加测试
     */
    @RequestMapping("/use_add")
    public String useAdd() {
        return PREFIX + "use_add.html";
    }

    /**
     * 跳转到修改测试
     */
    @RequestMapping("/use_update/{useId}")
    public String useUpdate(@PathVariable String useId, Model model) {
        Long idByInt= Long.parseLong(useId);
        Use use = useService.selectById(idByInt);
        model.addAttribute("item",use);
        LogObjectHolder.me().set(use);
        return PREFIX + "use_edit.html";
    }

    /**
     * 获取测试列表
     */
    @Permission
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(    @RequestParam(required = false) String id,
    @RequestParam(required = false) String good,
    @RequestParam(required = false) String papap){
        EntityWrapper<Use> entityWrapper=new EntityWrapper<>();
        if(ToolUtil.isNotEmpty(id)){
            entityWrapper.like("id",id);
        }
        if(ToolUtil.isNotEmpty(good)){
            entityWrapper.like("good",good);
        }
        if(ToolUtil.isNotEmpty(papap)){
            entityWrapper.like("papap",papap);
        }
        if (!ShiroKit.isAdmin()){
            entityWrapper.in("deptid",ShiroKit.getDeptDataScope());
        }
        return useService.selectList(entityWrapper);
    }

    /**
     * 新增测试
     */
    @Permission
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(@Valid Use use, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        useService.insert(use);
        return SUCCESS_TIP;
    }

    /**
     * 删除测试
     */
    @Permission
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String useId) {
        Long idByInt= Long.parseLong(useId);
        useService.deleteById(idByInt);
        return SUCCESS_TIP;
    }

    /**
     * 修改测试
     */
    @Permission
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(@Valid Use use, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        useService.updateById(use);
        return SUCCESS_TIP;
    }

    /**
     * 测试详情
     */
    @RequestMapping(value = "/detail/{useId}")
    @ResponseBody
    public Object detail(@PathVariable("useId") String useId) {
        Long idByInt= Long.parseLong(useId);
        return useService.selectById(idByInt);
    }
}
