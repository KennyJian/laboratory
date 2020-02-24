package com.kenny.laboratory.modular.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.kenny.laboratory.modular.system.model.Notice;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 通知表 服务类
 * </p>
 *
 * @author kenny
 * @since 2020-03-08
 */
public interface INoticeService extends IService<Notice> {

    /**
     * 获取通知列表
     */
    List<Map<String, Object>> list(String condition);
}
