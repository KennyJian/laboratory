package com.kenny.laboratory.modular.laboratory.service.impl;

import com.kenny.laboratory.modular.system.model.Score;
import com.kenny.laboratory.modular.system.dao.ScoreMapper;
import com.kenny.laboratory.modular.laboratory.service.IScoreService;
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
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score> implements IScoreService {

}
