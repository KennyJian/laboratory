package com.kenny.laboratory.modular.system.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author kenny
 * @since 2020-03-08
 */
@TableName("lab_laboratory")
public class Laboratory extends Model<Laboratory> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value="laboratory_id", type= IdType.ID_WORKER)
    private Long laboratoryId;
    /**
     * 实验室名字
     */
    @TableField("laboratory_name")
    private String laboratoryName;
    /**
     * 实验室地点
     */
    @TableField("laboratory_location")
    private String laboratoryLocation;
    /**
     * 实验室设备数
     */
    @TableField("laboratory_equipment_num")
    private Integer laboratoryEquipmentNum;
    /**
     * 实验室是否开放  1:开放 2:不开放
     */
    @TableField("is_open")
    private Integer isOpen;
    /**
     * 实验室是否通电 1:通电 2:不通电
     */
    @TableField("is_electrify")
    private Integer isElectrify;


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

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public Integer getIsElectrify() {
        return isElectrify;
    }

    public void setIsElectrify(Integer isElectrify) {
        this.isElectrify = isElectrify;
    }

    @Override
    protected Serializable pkVal() {
        return this.laboratoryId;
    }

    @Override
    public String toString() {
        return "Laboratory{" +
        "laboratoryId=" + laboratoryId +
        ", laboratoryName=" + laboratoryName +
        ", laboratoryLocation=" + laboratoryLocation +
        ", laboratoryEquipmentNum=" + laboratoryEquipmentNum +
        ", isOpen=" + isOpen +
        ", isElectrify=" + isElectrify +
        "}";
    }
}
