package com.kenny.laboratory.modular.system.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
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
@TableName("lab_apply_laboratory")
public class ApplyLaboratory extends Model<ApplyLaboratory> {

    private static final long serialVersionUID = 1L;

    @TableId(value="id", type= IdType.ID_WORKER)
    private Long id;
    /**
     * 申请的实验id
     */
    @TableField("experiment_id")
    private Long experimentId;
    /**
     * 申请的实验室id
     */
    @TableField("laboratory_id")
    private Long laboratoryId;
    /**
     * 实验名
     */
    @TableField("experiment_name")
    private String experimentName;
    /**
     * 实验室名
     */
    @TableField("laboratory_name")
    private String laboratoryName;
    /**
     * 申请使用时间
     */
    @TableField("apply_begin_time")
    private Date applyBeginTime;
    /**
     * 申请结束时间
     */
    @TableField("apply_end_time")
    private Date applyEndTime;
    /**
     * 状态 0:待审核 1:成功 2:失败
     */
    private Integer status;
    /**
     * 备注
     */
    private String comment;
    /**
     * 老师ID
     */
    @TableField("teacher_id")
    private Integer teacherId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ApplyLaboratory{" +
        "id=" + id +
        ", experimentId=" + experimentId +
        ", laboratoryId=" + laboratoryId +
        ", experimentName=" + experimentName +
        ", laboratoryName=" + laboratoryName +
        ", applyBeginTime=" + applyBeginTime +
        ", applyEndTime=" + applyEndTime +
        ", status=" + status +
        ", comment=" + comment +
        ", teacherId=" + teacherId +
        "}";
    }
}
