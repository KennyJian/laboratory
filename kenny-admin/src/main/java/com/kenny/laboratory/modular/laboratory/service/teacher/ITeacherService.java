package com.kenny.laboratory.modular.laboratory.service.teacher;

import com.kenny.laboratory.modular.laboratory.dto.teacher.ExperimentApplyDTO;

import java.util.List;

public interface ITeacherService {

    List<ExperimentApplyDTO> findAllExperimentByTeacher();
}
