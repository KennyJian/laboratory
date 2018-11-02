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
 * @since 2018-11-02
 */
@TableName("lab_experiment")
public class Experiment extends Model<Experiment> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value="experiment_id", type= IdType.ID_WORKER)
    private Long experimentId;
    /**
     * 实验名
     */
    @TableField("experiment_name")
    private String experimentName;
    /**
     * 课程号
     */
    @TableField("course_id")
    private String courseId;
    /**
     * 实验课时
     */
    @TableField("course_num")
    private Integer courseNum;
    /**
     * 实验所需设备
     */
    @TableField("equipment_need_num")
    private Integer equipmentNeedNum;
    /**
     * 老师用户ID
     */
    @TableField("teacher_id")
    private Integer teacherId;


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

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Integer getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(Integer courseNum) {
        this.courseNum = courseNum;
    }

    public Integer getEquipmentNeedNum() {
        return equipmentNeedNum;
    }

    public void setEquipmentNeedNum(Integer equipmentNeedNum) {
        this.equipmentNeedNum = equipmentNeedNum;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    protected Serializable pkVal() {
        return this.experimentId;
    }

    @Override
    public String toString() {
        return "Experiment{" +
        "experimentId=" + experimentId +
        ", experimentName=" + experimentName +
        ", courseId=" + courseId +
        ", courseNum=" + courseNum +
        ", equipmentNeedNum=" + equipmentNeedNum +
        ", teacherId=" + teacherId +
        "}";
    }
}
