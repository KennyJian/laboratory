package com.kenny.laboratory.modular.laboratory.service.teacher;

import com.kenny.laboratory.modular.laboratory.dto.teacher.*;
import com.kenny.laboratory.modular.system.model.ApplyExperiment;
import com.kenny.laboratory.modular.system.model.ApplyLaboratory;
import com.kenny.laboratory.modular.system.model.Score;

import java.util.List;

public interface ITeacherService {

    List<ExperimentApplyDTO> findAllExperimentByTeacher();

    boolean applyLaboratory(ApplyLaboratoryDTO applyLaboratoryDTO);

    boolean isEquipmentEnough(ApplyLaboratoryDTO applyLaboratoryDTO);

    List<ApplyLaboratoryDetailDTO>  convertApplyLaboratoryToApplyLaboratoryDetailDTO(List<ApplyLaboratory> applyLaboratoryList);

    List<AuditingExperimentDTO>  convertApplyExperimentToAuditingExperimentDTO(List<ApplyExperiment> applyExperimentList);

    void auditingSuccess(Long applyExperimentId);

    void auditingFail(Long applyExperimentId);

    List<TeacherScoreDTO> covertScoreToTeacherScoreDTO(List<Score> scoreList);

    void teacherGrade(Score score);

    boolean isAchieveAttendNum(Score score);
}
