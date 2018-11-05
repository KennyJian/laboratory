package com.kenny.laboratory.modular.laboratory.service.student;

import com.kenny.laboratory.modular.laboratory.dto.student.StundentApplyShowDTO;
import com.kenny.laboratory.modular.system.model.Experiment;

import java.util.List;

public interface IStudentService {

    List<StundentApplyShowDTO> convertExperientToStundentApplyShowDTO(List<Experiment> experimentList);

    void insertToApplyExperiment(String experimentId,String experimentName,String teacherId);
}
