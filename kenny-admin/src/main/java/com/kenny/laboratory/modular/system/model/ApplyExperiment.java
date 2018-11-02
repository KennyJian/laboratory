package com.kenny.laboratory.modular.system.model;

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
@TableName("lab_apply_experiment")
public class ApplyExperiment extends Model<ApplyExperiment> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value="id", type= IdType.ID_WORKER)
    private Long id;
    /**
     * 实验id
     */
    @TableField("experiment_id")
    private Long experimentId;
    /**
     * 学生名
     */
    @TableField("student_name")
    private String studentName;
    /**
     * 实验名
     */
    @TableField("experiment_name")
    private String experimentName;
    /**
     * 状态 0:待审核 1:成功 2:失败
     */
    private Integer status;
    /**
     * 学生id
     */
    @TableField("student_id")
    private Integer studentId;
    /**
     * 老师id
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

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getExperimentName() {
        return experimentName;
    }

    public void setExperimentName(String experimentName) {
        this.experimentName = experimentName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
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
        return "ApplyExperiment{" +
        "id=" + id +
        ", experimentId=" + experimentId +
        ", studentName=" + studentName +
        ", experimentName=" + experimentName +
        ", status=" + status +
        ", studentId=" + studentId +
        ", teacherId=" + teacherId +
        "}";
    }
}
