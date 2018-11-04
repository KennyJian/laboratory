package com.kenny.laboratory.modular.laboratory.service.admin;

import com.kenny.laboratory.modular.laboratory.dto.admin.AuditingLaboratoryShowDTO;
import com.kenny.laboratory.modular.system.model.ApplyLaboratory;

import java.util.List;

public interface IAdminService {

    List<AuditingLaboratoryShowDTO> convertAuditingLaboratoryShow(List<ApplyLaboratory> applyLaboratoryList);

    void auditingSuccess(Long id);

    void auditingFail(Long id);
}
