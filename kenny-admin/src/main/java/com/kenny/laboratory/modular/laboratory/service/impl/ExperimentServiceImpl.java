package com.kenny.laboratory.modular.laboratory.service.impl;

import com.kenny.laboratory.modular.system.model.Experiment;
import com.kenny.laboratory.modular.system.dao.ExperimentMapper;
import com.kenny.laboratory.modular.laboratory.service.IExperimentService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kenny
 * @since 2018-11-02
 */
@Service
public class ExperimentServiceImpl extends ServiceImpl<ExperimentMapper, Experiment> implements IExperimentService {

}
