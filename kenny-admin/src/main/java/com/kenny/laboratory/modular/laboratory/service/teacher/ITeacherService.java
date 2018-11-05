package com.kenny.laboratory.modular.laboratory.service.teacher;

import com.kenny.laboratory.modular.laboratory.dto.teacher.ApplyLaboratoryDTO;
import com.kenny.laboratory.modular.laboratory.dto.teacher.ApplyLaboratoryDetailDTO;
import com.kenny.laboratory.modular.laboratory.dto.teacher.ExperimentApplyDTO;
import com.kenny.laboratory.modular.system.model.ApplyLaboratory;

import java.util.List;

public interface ITeacherService {

    List<ExperimentApplyDTO> findAllExperimentByTeacher();

    boolean applyLaboratory(ApplyLaboratoryDTO applyLaboratoryDTO);

    boolean isEquipmentEnough(ApplyLaboratoryDTO applyLaboratoryDTO);

    List<ApplyLaboratoryDetailDTO>  convertApplyLaboratoryToApplyLaboratoryDetailDTO(List<ApplyLaboratory> applyLaboratoryList);
}
