package com.kenny.laboratory.modular.laboratory.dto.teacher;

public class ExperimentApplyDTO {

    /**
     * id
     */
    private Long experimentId;
    /**
     * 实验名
     */
    private String experimentName;


    public Long getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(Long experimentId) {
        this.experimentId = experimentId;
    }

    public String getExperimentName() {
        return experimentName;
    }

    public void setExperimentName(String experimentName) {
        this.experimentName = experimentName;
    }

}
