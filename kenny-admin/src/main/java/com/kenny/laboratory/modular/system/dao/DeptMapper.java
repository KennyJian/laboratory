package com.kenny.laboratory.modular.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kenny.laboratory.core.node.ZTreeNode;
import com.kenny.laboratory.modular.system.model.Dept;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 *
 * @author kenny
 * @since 2020-03-08
 */
public interface DeptMapper extends BaseMapper<Dept> {

    /**
     * 获取ztree的节点列表
     */
    List<ZTreeNode> tree();

    /**
     * 获取所有部门列表
     */
    List<Map<String, Object>> list(@Param("condition") String condition);

}