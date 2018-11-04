package com.kenny.laboratory.modular.laboratory.dto.teacher;

import java.util.Date;

public class ApplyLaboratoryDTO {

    private Long experimentId;

    private Long laboratoryId;

    private String laboratoryName;

    private Date applyBeginTime;

    private Date applyEndTime;

    private Integer laboratoryEquipmentNum;

    public Long getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(Long experimentId) {
        this.experimentId = experimentId;
    }

    public Long getLaboratoryId() {
        return laboratoryId;
    }

    public void setLaboratoryId(Long laboratoryId) {
        this.laboratoryId = laboratoryId;
    }

    public String getLaboratoryName() {
        return laboratoryName;
    }

    public void setLaboratoryName(String laboratoryName) {
        this.laboratoryName = laboratoryName;
    }

    public Date getApplyBeginTime() {
        return applyBeginTime;
    }

    public void setApplyBeginTime(Date applyBeginTime) {
        this.applyBeginTime = applyBeginTime;
    }

    public Date getApplyEndTime() {
        return applyEndTime;
    }

    public void setApplyEndTime(Date applyEndTime) {
        this.applyEndTime = applyEndTime;
    }

    public Integer getLaboratoryEquipmentNum() {
        return laboratoryEquipmentNum;
    }

    public void setLaboratoryEquipmentNum(Integer laboratoryEquipmentNum) {
        this.laboratoryEquipmentNum = laboratoryEquipmentNum;
    }
}
