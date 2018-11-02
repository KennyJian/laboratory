package com.kenny.laboratory.modular.laboratory.controller.admin.dto;


/**
 * <p>
 * 
 * </p>
 *
 * @author kenny
 * @since 2018-11-02
 */
public class LaboratoryDTO{


    private Long laboratoryId;
    /**
     * 实验室名字
     */
    private String laboratoryName;
    /**
     * 实验室地点
     */
    private String laboratoryLocation;
    /**
     * 实验室设备数
     */
    private Integer laboratoryEquipmentNum;
    /**
     * 实验室是否开放  1:开放 2:不开放
     */
    private String isOpen;
    /**
     * 实验室是否通电 1:通电 2:不通电
     */
    private String isElectrify;

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

    public String getLaboratoryLocation() {
        return laboratoryLocation;
    }

    public void setLaboratoryLocation(String laboratoryLocation) {
        this.laboratoryLocation = laboratoryLocation;
    }

    public Integer getLaboratoryEquipmentNum() {
        return laboratoryEquipmentNum;
    }

    public void setLaboratoryEquipmentNum(Integer laboratoryEquipmentNum) {
        this.laboratoryEquipmentNum = laboratoryEquipmentNum;
    }

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    public String getIsElectrify() {
        return isElectrify;
    }

    public void setIsElectrify(String isElectrify) {
        this.isElectrify = isElectrify;
    }
}
