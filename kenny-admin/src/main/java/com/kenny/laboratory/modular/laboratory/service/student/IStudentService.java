package com.kenny.laboratory.modular.laboratory.service.student;

import com.kenny.laboratory.modular.laboratory.dto.student.StudentApplyDetailDTO;
import com.kenny.laboratory.modular.laboratory.dto.student.StudentScoreDTO;
import com.kenny.laboratory.modular.laboratory.dto.student.StundentApplyShowDTO;
import com.kenny.laboratory.modular.system.model.ApplyExperiment;
import com.kenny.laboratory.modular.system.model.Experiment;
import com.kenny.laboratory.modular.system.model.Score;

import java.util.List;

public interface IStudentService {

    List<StundentApplyShowDTO> convertExperientToStundentApplyShowDTO(List<Experiment> experimentList);

    void insertToApplyExperiment(String experimentId,String experimentName,String teacherId);

    List<StudentApplyDetailDTO> convertApplyeExperimentToStudentApplyDetailDTO(List<ApplyExperiment> applyExperimentList);

    List<StudentScoreDTO> convertScoreToStudentScoreDTO(List<Score> scoreList);

    void studentAttend(Long scoreId);
}
