package com.kenny.laboratory.modular.laboratory.dto.teacher;

import java.util.Date;

public class TacherSignInDTO {

    private Long id;
    private String experimentName;
    private String laboratoryName;
    private Date applyBeginTime;
    private Date applyEndTime;
    private String isElectrify;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExperimentName() {
        return experimentName;
    }

    public void setExperimentName(String experimentName) {
        this.experimentName = experimentName;
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

    public String getIsElectrify() {
        return isElectrify;
    }

    public void setIsElectrify(String isElectrify) {
        this.isElectrify = isElectrify;
    }
}
