package com.kenny.laboratory.modular.system.model;

import java.math.BigDecimal;
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
 * @since 2020-03-08
 */
@TableName("lab_score")
public class Score extends Model<Score> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value="id", type= IdType.ID_WORKER)
    private Long id;
    /**
     * 学生id
     */
    @TableField("student_id")
    private Integer studentId;
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
     * 到勤数
     */
    @TableField("attendance_num")
    private Integer attendanceNum;
    /**
     * 成绩
     */
    private BigDecimal score;
    /**
     * 老师名字
     */
    @TableField("teacher_name")
    private String teacherName;
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

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
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

    public Integer getAttendanceNum() {
        return attendanceNum;
    }

    public void setAttendanceNum(Integer attendanceNum) {
        this.attendanceNum = attendanceNum;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
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
        return "Score{" +
        "id=" + id +
        ", studentId=" + studentId +
        ", experimentId=" + experimentId +
        ", studentName=" + studentName +
        ", experimentName=" + experimentName +
        ", attendanceNum=" + attendanceNum +
        ", score=" + score +
        ", teacherName=" + teacherName +
        ", teacherId=" + teacherId +
        "}";
    }
}
