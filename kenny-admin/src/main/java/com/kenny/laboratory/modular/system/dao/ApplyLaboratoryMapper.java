package com.kenny.laboratory.modular.system.dao;

import com.kenny.laboratory.modular.system.model.ApplyLaboratory;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kenny
 * @since 2018-11-02
 */
public interface ApplyLaboratoryMapper extends BaseMapper<ApplyLaboratory> {

    List<Long> findExperimentIdByStatusSuccess();
}
